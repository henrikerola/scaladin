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
      wrapper.state = Option(state)
    }

    def setNavigator(navigator: com.vaadin.navigator.Navigator) {
      wrapper.setNavigator(WrapperUtil.wrapperFor(navigator))
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
        Option(event.getViewName),
        Option(event.getParameters)))
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
      wrapper.getViewName(Option(s)).getOrElse(null)
    }

    override def getView(s: String): com.vaadin.navigator.View = {
      wrapper.getView(Option(s)).map(_.pView).getOrElse(null)
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

    /**
     * This method should only be called by a Navigator.
     * @see com.vaadin.navigator.NavigationStateManager
     */
    def setNavigator(n: Option[Navigator])

    def state: Option[String]

    /**
     * This method should only be called by a Navigator.
     * @see com.vaadin.navigator.NavigationStateManager
     */
    def state_=(s: Option[String])

  }

  trait View extends InterfaceWrapper {
    val pView = new com.vaadin.navigator.View with ViewMixin
    pView.wrapper = this

    def enter(e: Navigator.ViewChangeEvent)
  }

  case class ViewChangeEvent(navigator: Navigator, oldView: Option[Navigator.View], newView: Navigator.View, viewName: Option[String], parameters: Option[String]) extends Event

  trait ViewDisplay extends InterfaceWrapper {
    val pViewDisplay = new com.vaadin.navigator.ViewDisplay with ViewDisplayMixin
    pViewDisplay.wrapper = this

    def showView(view: Navigator.View) {
      showView(Option(view))
    }

    def showView(view: Option[Navigator.View])
  }

  trait ViewProvider extends InterfaceWrapper {
    val pViewProvider = new com.vaadin.navigator.ViewProvider with ViewProviderMixin
    pViewProvider.wrapper = this

    def getViewName(viewAndParameters: String): Option[String] = getViewName(Option(viewAndParameters))
    def getViewName(viewAndParameters: Option[String]): Option[String]

    def getView(name: String): Option[Navigator.View] = getView(Option(name))
    def getView(name: Option[String]): Option[Navigator.View]

  }

  class UriFragmentManager(val page: Page, var navigator: Option[Navigator] = None) extends NavigationStateManager {

    // TODO: Added through p since page does not have listeners implemented yet.
    // page.uriFragmentChangedListeners.add((event: Page.UriFragmentChangedEvent) => navigator.map(_.navigateTo(state)))
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

    /**
     * This method should only be called by a Navigator.
     * @see com.vaadin.navigator.NavigationStateManager
     */
    def setNavigator(n: Option[Navigator]) {
      navigator = n
    }

  }

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

  class SingleComponentContainerViewDisplay(container: SingleComponentContainer) extends Navigator.ViewDisplay {
    def showView(view: Option[Navigator.View]) {
      view.map(v => v match {
        case c: Component => container.content_=(c)
        case _ => throw new IllegalArgumentException("View is not a component: " + view)
      })
    }
  }

  /**
   * Helper for removing duplicate code in StaticViewProvider and ClassBasedViewProvider
   */
  trait AbstractViewProvider extends Navigator.ViewProvider {
    def viewName: Option[String]

    def getViewName(viewAndParameters: Option[String]): Option[String] = {
      viewAndParameters.map(vap => {
        if (viewName.map(vn => vn == vap || vap.startsWith(vn + "/")).getOrElse(false)) {
          viewName
        } else {
          None
        }
      }).getOrElse(None)
    }
  }

  class StaticViewProvider(val viewName: Option[String], val viewInstance: Option[Navigator.View]) extends AbstractViewProvider {
    def getView(name: Option[String]): Option[Navigator.View] = if (viewName == name) {
      viewInstance
    } else {
      None
    }
  }

  class ClassBasedViewProvider(val viewName: Option[String], viewClass: Class[_ <: Navigator.View]) extends AbstractViewProvider {
    def getView(name: Option[String]): Option[Navigator.View] = {
      if (name == viewName) {
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
    apply(ui, new Navigator.ComponentContainerViewDisplay(container))
  }

  def apply(ui: UI, container: SingleComponentContainer): Navigator = {
    apply(ui, new Navigator.SingleComponentContainerViewDisplay(container))
  }

  def apply(ui: UI, display: Navigator.ViewDisplay): Navigator = {
    new Navigator(ui, new Navigator.UriFragmentManager(ui.page), display)
  }
}

/**
 * @see com.vaadin.navigator.Navigator
 * @author Matti Heinola / Viklo
 */
class Navigator(val ui: UI, val stateManager: Navigator.NavigationStateManager, val display: Navigator.ViewDisplay) extends Wrapper {
  navigator =>

  val p: com.vaadin.navigator.Navigator with NavigatorMixin = new com.vaadin.navigator.Navigator(ui.p, stateManager.pNavigationStateManager, display.pViewDisplay) with NavigatorMixin
  p.wrapper = this
  stateManager.setNavigator(Option(this))

  def navigateTo(navigationState: String) {
    navigateTo(Option(navigationState))
  }

  def navigateTo(navigationState: Option[String]) {
    p.navigateTo(navigationState.getOrElse(""))
  }

  def getState: Option[String] = stateManager.state

  def addView(viewName: String, view: Navigator.View) {
    addView(Option(viewName), Option(view))
  }

  def addView(viewName: Option[String], view: Option[Navigator.View]) {
    addViewProvider(viewName, new Navigator.StaticViewProvider(viewName, view))
  }

  def addView(viewName: String, viewClass: Class[_ <: Navigator.View]) {
    addView(Option(viewName), viewClass)
  }

  def addView(viewName: Option[String], viewClass: Class[_ <: Navigator.View]) {
    addViewProvider(viewName, new Navigator.ClassBasedViewProvider(viewName, viewClass))
  }

  private def addViewProvider(viewName: Option[String], provider: Navigator.ViewProvider) {
    removeView(viewName)
    addProvider(provider)
  }

  def removeView(viewName: String) {
    removeView(Option(viewName))
  }

  def removeView(viewName: Option[String]) {
    viewName.map(n => p.removeView(n))
  }

  def addProvider(provider: Navigator.ViewProvider) {
    p.addProvider(provider.pViewProvider)
  }

  def removeProvider(provider: Navigator.ViewProvider) {
    p.removeProvider(provider.pViewProvider)
  }

  def setErrorView(viewClass: Class[_ <: Navigator.View]) {
    setErrorProvider(new Navigator.ViewProvider {
      def getViewName(viewAndParameters: Option[String]): Option[String] = {
        viewAndParameters
      }

      def getView(name: Option[String]): Option[Navigator.View] = {
        try {
          Some(viewClass.newInstance())
        } catch {
          case e: Exception => throw new RuntimeException(e)
        }
      }
    })
  }

  def setErrorView(view: Navigator.View) {
    p.setErrorView(view.pView)
  }

  def setErrorProvider(provider: Navigator.ViewProvider) {
    p.setErrorProvider(provider.pViewProvider)
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
