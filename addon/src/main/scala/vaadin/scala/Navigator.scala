package vaadin.scala

import scala.collection.mutable
import scala.collection.JavaConversions._

import internal._
import vaadin.scala.mixins._
import scala.Some

package mixins {

  /**
   * @see com.vaadin.navigator.NavigationStateManager
   */
  trait NavigationStateManagerMixin extends ScaladinInterfaceMixin {
    self: com.vaadin.navigator.NavigationStateManager =>
    override def wrapper = super.wrapper.asInstanceOf[Navigator.NavigationStateManager]

    def getState: String = {
      wrapper.state.getOrElse("")
    }

    def setState(state: String) {
      wrapper.state = Some(state)
    }

    def setNavigator(navigator: com.vaadin.navigator.Navigator) {
      wrapper.navigator = WrapperUtil.wrapperFor(navigator)
    }

  }

  trait ViewMixin extends ScaladinInterfaceMixin {
    self: com.vaadin.navigator.View =>
    override def wrapper = super.wrapper.asInstanceOf[Navigator.View]

    def enter(event: com.vaadin.navigator.ViewChangeListener.ViewChangeEvent) {
      wrapper.enter(Navigator.ViewChangeEvent(
        WrapperUtil.wrapperFor(event.getNavigator).get,
        WrapperUtil.wrapperFor(event.getOldView),
        WrapperUtil.wrapperFor(event.getNewView).get,
        event.getViewName,
        event.getParameters))
    }
  }

  trait ViewDisplayMixin extends ScaladinInterfaceMixin {
    self: com.vaadin.navigator.ViewDisplay =>
    override def wrapper = super.wrapper.asInstanceOf[Navigator.ViewDisplay]

    def showView(view: com.vaadin.navigator.View) {
      wrapper.showView(WrapperUtil.wrapperFor(view))
    }
  }

  trait ViewProviderMixin extends ScaladinInterfaceMixin {
    self: com.vaadin.navigator.ViewProvider =>
    override def wrapper = super.wrapper.asInstanceOf[Navigator.ViewProvider]

    override def getViewName(s: String): String = {
      wrapper.viewName(s).getOrElse(null)
    }

    override def getView(s: String): com.vaadin.navigator.View = {
      wrapper.view(s).map(_.pView).getOrElse(null)
    }
  }

  trait NavigatorMixin extends ScaladinMixin {
    self: com.vaadin.navigator.Navigator =>
    override def fireBeforeViewChange(event: com.vaadin.navigator.ViewChangeListener.ViewChangeEvent): Boolean = {
      !wrapper.asInstanceOf[Navigator].beforeViewChangeListeners.listenerSet.exists(listener => !listener.beforeViewChange(event))
    }

    override def fireAfterViewChange(event: com.vaadin.navigator.ViewChangeListener.ViewChangeEvent) {
      wrapper.asInstanceOf[Navigator].afterViewChangeListeners.listenerSet.foreach(listener => listener.afterViewChange(event))
    }
  }

}

object Navigator {

  trait NavigationStateManager extends InterfaceWrapper {
    val pNavigationStateManager = new com.vaadin.navigator.NavigationStateManager with NavigationStateManagerMixin
    pNavigationStateManager.wrapper = this

    def navigator: Option[Navigator]

    def navigator_=(n: Navigator) {
      navigator_=(Some(n))
    }

    def navigator_=(n: Option[Navigator])

    def state: Option[String]

    def state_=(s: String) {
      state_=(Some(s))
    }

    def state_=(s: Option[String])

  }

  trait View extends InterfaceWrapper {
    val pView = new com.vaadin.navigator.View with ViewMixin
    pView.wrapper = this

    def enter(e: Navigator.ViewChangeEvent)
  }

  case class ViewChangeEvent(navigator: Navigator, oldView: Option[Navigator.View], newView: Navigator.View, viewName: String, parameters: String) extends Event

  trait ViewDisplay extends InterfaceWrapper {
    val pViewDisplay = new com.vaadin.navigator.ViewDisplay with ViewDisplayMixin
    pViewDisplay.wrapper = this

    def showView(v: Navigator.View) {
      showView(Option(v))
    }

    def showView(v: Option[Navigator.View])
  }

  trait ViewProvider extends InterfaceWrapper {
    val pViewProvider = new com.vaadin.navigator.ViewProvider with ViewProviderMixin
    pViewProvider.wrapper = this

    def viewName(viewAndParameters: String): Option[String]

    def view(name: String): Option[Navigator.View]

  }

  class UriFragmentManager(val page: Page, var navigator: Option[Navigator] = None) extends NavigationStateManager {

    // Added through p since page does not have listeners implemented yet.
    //    page.uriFragmentChangedListeners.add((event: Page.UriFragmentChangedEvent) => navigator.map(_.navigateTo(state)))
    page.p.addUriFragmentChangedListener(new UriFragmentChangedListener((event: Page.UriFragmentChangedEvent) => navigator.map(_.navigateTo(state))))

    def state: Option[String] = page.uriFragment.map(fragment => {
      if (fragment == null || !fragment.startsWith("!")) {
        None
      } else {
        Some(fragment.substring(1))
      }
    }).getOrElse(None)

    def state_=(s: Option[String]) {
      page.setUriFragment(fragment = "!" + s.getOrElse(""), false)
    }

  }

  // TODO: extends CustomComponent with ViewDisplay
  class ComponentContainerViewDisplay(container: ComponentContainer) extends Navigator.ViewDisplay {
    def showView(view: Option[Navigator.View]) {
      view.map(v => v match {
        case c: Component => {
          container.removeAllComponents()
          container.addComponent(c)
        }
        case _ => throw new IllegalArgumentException("View is not a component: " + view)
      })
    }
  }

  // TODO: extends CustomComponent with ViewDisplay
  class SingleComponentContainerViewDisplay(container: SingleComponentContainer) extends Navigator.ViewDisplay {
    def showView(view: Option[Navigator.View]) {
      view.map(v => v match {
        case c: Component => container.content_=(c)
        case _ => throw new IllegalArgumentException("View is not a component: " + view)
      })
    }
  }

  class StaticViewProvider(val viewName: String, val viewInstance: Navigator.View) extends Navigator.ViewProvider {
    def viewName(viewAndParameters: String): Option[String] = {
      if (viewAndParameters != null && (viewAndParameters == viewName || viewAndParameters.startsWith(viewName + "/"))) {
        Some(viewName)
      } else {
        None
      }
    }

    def view(name: String): Option[Navigator.View] = if (viewName == name) {
      Some(viewInstance)
    } else {
      None
    }
  }

  class ClassBasedViewProvider(val viewName: String, viewClass: Class[_ <: Navigator.View]) extends Navigator.ViewProvider {
    def viewName(viewAndParameters: String): Option[String] = {
      if (viewAndParameters != null && (viewAndParameters == viewName || viewAndParameters.startsWith(viewName + "/"))) {
        Some(viewName)
      } else {
        None
      }
    }

    def view(n: String): Option[Navigator.View] = {
      if (n == viewName) {
        try {
          Some(viewClass.newInstance())
        } catch {
          case e: InstantiationException => throw new RuntimeException(e)
          case e: IllegalAccessException => throw new RuntimeException(e)
        }
      } else {
        None
      }
    }
  }

  def apply(ui: UI, container: ComponentContainer): Navigator = {
    new Navigator(ui, new ComponentContainerViewDisplay(container))
  }

  def apply(ui: UI, container: SingleComponentContainer): Navigator = {
    new Navigator(ui, new SingleComponentContainerViewDisplay(container))
  }

}

/**
 * @see com.vaadin.navigator.Navigator
 * @author Matti Heinola / Viklo
 */
class Navigator(val ui: UI, val display: Navigator.ViewDisplay) extends Wrapper {
  navigator =>

  val stateManager: Navigator.NavigationStateManager = new Navigator.UriFragmentManager(ui.page)
  val p: com.vaadin.navigator.Navigator with NavigatorMixin = new com.vaadin.navigator.Navigator(ui.p, stateManager.pNavigationStateManager, display.pViewDisplay) with NavigatorMixin
  p.wrapper = this
  stateManager.navigator = this

  def navigateTo(navigationState: String) {
    navigateTo(Option(navigationState))
  }

  def navigateTo(navigationState: Option[String]) {
    p.navigateTo(navigationState.getOrElse(""))
  }

  def state: Option[String] = stateManager.state

  def addView(viewName: String, view: Navigator.View) {
    addViewProvider(viewName, new Navigator.StaticViewProvider(viewName, view))
  }

  def addView(viewName: String, viewClass: Class[_ <: Navigator.View]) {
    addViewProvider(viewName, new Navigator.ClassBasedViewProvider(viewName, viewClass))
  }

  private def addViewProvider(viewName: String, provider: Navigator.ViewProvider) {
    removeView(viewName)
    addProvider(provider)
  }

  def removeView(viewName: String) {
    p.removeView(viewName)
  }

  def addProvider(provider: Navigator.ViewProvider) {
    p.addProvider(provider.pViewProvider)
  }

  def removeProvider(provider: Navigator.ViewProvider) {
    p.removeProvider(provider.pViewProvider)
  }

  def errorView(viewClass: Class[_ <: Navigator.View]) {
    p.setErrorProvider(new Navigator.ViewProvider {
      def viewName(viewAndParameters: String): Option[String] = {
        Option(viewAndParameters)
      }

      def view(n: String): Option[Navigator.View] = {
        try {
          Some(viewClass.newInstance())
        } catch {
          case e: Exception => throw new RuntimeException(e)
        }
      }
    }.pViewProvider)
  }

  def errorView(view: Navigator.View) {
    p.setErrorView(view.pView)
  }

  lazy val beforeViewChangeListeners = new DecisionListenersTrait[Navigator.ViewChangeEvent, ViewChangeListener] {
    val listenerSet: mutable.Set[ViewChangeListener] = mutable.HashSet()

    override def listeners: java.util.Collection[_] = listenerSet

    override def addListener(elem: Navigator.ViewChangeEvent => Boolean) {
      listenerSet.add(new BeforeViewChangeListener(elem))
    }

    override def removeListener(elem: ViewChangeListener) {
      listenerSet.remove(elem)
    }
  }

  lazy val afterViewChangeListeners = new ListenersTrait[Navigator.ViewChangeEvent, ViewChangeListener] {
    val listenerSet: mutable.Set[ViewChangeListener] = mutable.HashSet()

    override def listeners: java.util.Collection[_] = listenerSet

    override def addListener(elem: Navigator.ViewChangeEvent => Unit) {
      listenerSet.add(new AfterViewChangeListener(elem))
    }

    override def removeListener(elem: ViewChangeListener) {
      listenerSet.remove(elem)
    }
  }

}
