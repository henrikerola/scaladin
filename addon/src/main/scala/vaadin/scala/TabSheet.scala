package vaadin.scala

import com.vaadin.ui.{ TabSheet => VaadinTabSheet }
import event.{ FocusNotifier, BlurNotifier, Event }
import vaadin.scala.mixins.TabSheetMixin
import internal.{ TabCloseHandler, SelectedTabChangeListener, ListenersTrait }
import collection.mutable

package mixins {
  trait TabSheetMixin extends AbstractComponentContainerMixin { self: com.vaadin.ui.TabSheet => }
}

object TabSheet {

  class Tab(override val p: com.vaadin.ui.TabSheet.Tab) extends Wrapper {

    def visible: Boolean = p.isVisible
    def visible_=(visible: Boolean) { p.setVisible(visible) }

    def closable: Boolean = p.isClosable
    def closable_=(closable: Boolean) { p.setClosable(closable) }

    def enabled: Boolean = p.isEnabled
    def enabled_=(enabled: Boolean) { p.setEnabled(enabled) }

    def caption: Option[String] = Option(p.getCaption)
    def caption_=(caption: Option[String]) { p.setCaption(caption.orNull) }
    def caption_=(caption: String) { p.setCaption(caption) }

    def icon: Option[Resource] = wrapperFor(p.getIcon)
    def icon_=(icon: Option[Resource]) { p.setIcon(peerFor(icon)) }
    def icon_=(icon: Resource) { p.setIcon(icon.p) }

    def description: Option[String] = Option(p.getDescription)
    def description_=(description: Option[String]) { p.setDescription(description.orNull) }
    def description_=(description: String) { p.setDescription(description) }

    // TODO: component error

    def component: Component = wrapperFor(p.getComponent).get

    def styleName: Option[String] = Option(p.getStyleName)
    def styleName_=(styleName: Option[String]) { p.setStyleName(styleName.orNull) }
    def styleName_=(styleName: String) { p.setStyleName(styleName) }
  }

  case class TabCloseEvent(tabSheet: TabSheet, component: Component, tab: TabSheet.Tab) extends Event
  case class SelectedTabChangeEvent(tabSheet: TabSheet) extends Event

}

class TabSheet(override val p: VaadinTabSheet with TabSheetMixin = new VaadinTabSheet with TabSheetMixin)
    extends AbstractComponentContainer(p) with Focusable with FocusNotifier with BlurNotifier with SelectiveRenderer {

  // TODO: change to protected/private
  val tabs = mutable.Map.empty[com.vaadin.ui.TabSheet.Tab, TabSheet.Tab]

  protected def register(vaadinTab: com.vaadin.ui.TabSheet.Tab): TabSheet.Tab = {
    val tab = new TabSheet.Tab(vaadinTab)
    tabs += vaadinTab -> tab
    tab
  }

  def removeTab(tab: TabSheet.Tab) {
    tabs -= tab.p
    p.removeTab(tab.p)
  }

  override def removeComponent(component: Component) {
    getTab(component) match {
      case Some(tab) => tabs -= tab.p
      case None =>
    }
    super.removeComponent(component)
  }

  def addTab(component: Component): TabSheet.Tab = register(p.addTab(component.p))
  def addTab(component: Component, caption: String): TabSheet.Tab = register(p.addTab(component.p, caption))
  def addTab(component: Component, caption: String, icon: Resource): TabSheet.Tab =
    register(p.addTab(component.p, caption, icon.p))
  def addTab(component: Component, caption: String, icon: Resource, position: Int): TabSheet.Tab =
    register(p.addTab(component.p, caption, icon.p, position))
  def addTab(component: Component, position: Int): TabSheet.Tab = register(p.addTab(component.p, position))

  override def add[C <: Component](component: C): C = addTab(component).component.asInstanceOf[C]

  def tabsVisible: Boolean = !p.areTabsHidden
  def tabsVisible_=(tabsVisible: Boolean) { p.hideTabs(!tabsVisible) }

  def getTab(component: Component): Option[TabSheet.Tab] = tabs.get(p.getTab(component.p))
  def getTab(position: Int): Option[TabSheet.Tab] = tabs.get(p.getTab(position))

  def selectedComponent: Component = wrapperFor(p.getSelectedTab).get
  def selectedComponent_=(component: Component) { p.setSelectedTab(component.p) }

  def selectedTab: TabSheet.Tab = getTab(selectedComponent).get
  def selectedTab_=(tab: TabSheet.Tab) { selectedComponent = tab.component }

  def getTabPosition(tab: TabSheet.Tab): Int = p.getTabPosition(tab.p)
  def setTabPosition(tab: TabSheet.Tab, position: Int) { p.setTabPosition(tab.p, position) }

  var _closeHandler: TabSheet.TabCloseEvent => Unit = (e: TabSheet.TabCloseEvent) => { removeComponent(e.component) }
  p.setCloseHandler(new TabCloseHandler(_closeHandler))

  def closeHandler: TabSheet.TabCloseEvent => Unit = _closeHandler

  def closeHandler_=(handler: TabSheet.TabCloseEvent => Unit): Unit = {
    _closeHandler = handler
    p.setCloseHandler(new TabCloseHandler(handler))
  }

  lazy val selectedTabChangeListeners = new ListenersTrait[TabSheet.SelectedTabChangeEvent, SelectedTabChangeListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.TabSheet.SelectedTabChangeEvent])
    override def addListener(elem: TabSheet.SelectedTabChangeEvent => Unit) =
      p.addSelectedTabChangeListener(new SelectedTabChangeListener(elem))
    override def removeListener(elem: SelectedTabChangeListener) = p.removeSelectedTabChangeListener(elem)
  }
}