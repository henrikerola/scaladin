package vaadin.scala

import vaadin.scala.mixins.TabSheetMixin
import vaadin.scala.internal.WrapperUtil
import vaadin.scala.internal.SelectedTabChangeListener
import vaadin.scala.internal.ListenersTrait
import collection.mutable

package mixins {
  trait TabSheetMixin extends AbstractComponentContainerMixin { self: com.vaadin.ui.TabSheet => }
}

object TabSheet {

  class Tab(override val p: com.vaadin.ui.TabSheet.Tab) extends Wrapper {

    def visible: Boolean = p.isVisible
    def visible_=(visible: Boolean): Unit = p.setVisible(visible)

    def closable: Boolean = p.isClosable
    def closable_=(closable: Boolean): Unit = p.setClosable(closable)

    def enabled: Boolean = p.isEnabled
    def enabled_=(enabled: Boolean): Unit = p.setEnabled(enabled)

    def caption: Option[String] = Option(p.getCaption)
    def caption_=(caption: Option[String]): Unit = p.setCaption(caption.orNull)
    def caption_=(caption: String): Unit = p.setCaption(caption)

    def icon: Option[Resource] = WrapperUtil.wrapperFor[Resource](p.getIcon)
    def icon_=(icon: Option[Resource]): Unit = if (icon.isDefined) p.setIcon(icon.get.p) else p.setIcon(null)
    def icon_=(icon: Resource): Unit = if (icon == null) p.setIcon(null) else p.setIcon(icon.p)

    def description: Option[String] = Option(p.getDescription)
    def description_=(description: Option[String]): Unit = p.setDescription(description.orNull)
    def description_=(description: String): Unit = p.setDescription(description)

    // TODO: component error

    def component: Component = WrapperUtil.wrapperFor[Component](p.getComponent).get

    def styleName: Option[String] = Option(p.getStyleName)
    def styleName_=(styleName: Option[String]): Unit = p.setStyleName(styleName.orNull)
    def styleName_=(styleName: String): Unit = p.setStyleName(styleName)
  }

  case class TabCloseEvent(tabSheet: TabSheet, component: Component, tab: TabSheet.Tab) extends Event
  case class SelectedTabChangeEvent(tabSheet: TabSheet) extends Event

}

class TabSheet(override val p: com.vaadin.ui.TabSheet with TabSheetMixin = new com.vaadin.ui.TabSheet with TabSheetMixin)
    extends AbstractComponentContainer(p) with Focusable with FocusNotifier with BlurNotifier with SelectiveRenderer {

  // TODO: change to protected/private
  val tabs = mutable.Map.empty[com.vaadin.ui.TabSheet.Tab, TabSheet.Tab]
  protected def register(vaadinTab: com.vaadin.ui.TabSheet.Tab): TabSheet.Tab = {
    val tab = new TabSheet.Tab(vaadinTab)
    tabs += vaadinTab -> tab
    tab
  }

  def removeTab(tab: TabSheet.Tab): Unit = {
    tabs -= tab.p
    p.removeTab(tab.p)
  }

  override def removeComponent(component: Component): Unit = {
    tab(component) match {
      case Some(tab) => tabs -= tab.p
      case None =>
    }
    super.removeComponent(component)
  }

  def addTab(component: Component): TabSheet.Tab = register(p.addTab(component.p))
  def addTab(component: Component, caption: String): TabSheet.Tab = register(p.addTab(component.p, caption))
  def addTab(component: Component, caption: String, icon: Resource): TabSheet.Tab = register(p.addTab(component.p, caption, icon.p))
  def addTab(component: Component, caption: String, icon: Resource, position: Int): TabSheet.Tab = register(p.addTab(component.p, caption, icon.p, position))
  def addTab(component: Component, position: Int): TabSheet.Tab = register(p.addTab(component.p, position))

  override def add[C <: Component](component: C): C = addTab(component).component.asInstanceOf[C]

  def tabsVisible: Boolean = !p.areTabsHidden
  def tabsVisible_=(tabsVisible: Boolean): Unit = p.hideTabs(!tabsVisible)

  def tab(component: Component): Option[TabSheet.Tab] = tabs.get(p.getTab(component.p))
  def tab(position: Int): Option[TabSheet.Tab] = tabs.get(p.getTab(position))

  def selectedComponent: Component = wrapperFor[Component](p.getSelectedTab).get
  def selectedComponent_=(component: Component): Unit = p.setSelectedTab(component.p)

  def selectedTab: TabSheet.Tab = tab(selectedComponent).get
  def selectedTab_=(tab: TabSheet.Tab) = { selectedComponent = tab.component }

  def tabPosition(tab: TabSheet.Tab): Int = p.getTabPosition(tab.p)
  def tabPosition(tab: TabSheet.Tab, position: Int): Unit = p.setTabPosition(tab.p, position)

  private class TabCloseHandler(val action: TabSheet.TabCloseEvent => Unit) extends com.vaadin.ui.TabSheet.CloseHandler {
    def onTabClose(tabsheet: com.vaadin.ui.TabSheet, tabContent: com.vaadin.ui.Component): Unit = {
      val component = wrapperFor[Component](tabContent).get
      action(TabSheet.TabCloseEvent(wrapperFor[TabSheet](tabsheet).get, component, tab(component).get))
    }
  }

  var _closeHandler: TabSheet.TabCloseEvent => Unit = (e: TabSheet.TabCloseEvent) => { removeComponent(e.component) }
  p.setCloseHandler(new TabCloseHandler(_closeHandler))

  def closeHandler: TabSheet.TabCloseEvent => Unit = _closeHandler

  def closeHandler_=(handler: TabSheet.TabCloseEvent => Unit): Unit = {
    _closeHandler = handler
    p.setCloseHandler(new TabCloseHandler(handler))
  }

  lazy val selectedTabChangeListeners = new ListenersTrait[TabSheet.SelectedTabChangeEvent, SelectedTabChangeListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.TabSheet.SelectedTabChangeEvent])
    override def addListener(elem: TabSheet.SelectedTabChangeEvent => Unit) = p.addSelectedTabChangeListener(new SelectedTabChangeListener(elem))
    override def removeListener(elem: SelectedTabChangeListener) = p.removeSelectedTabChangeListener(elem)
  }
}