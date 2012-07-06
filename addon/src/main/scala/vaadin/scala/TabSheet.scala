package vaadin.scala

import vaadin.scala.mixins.TabSheetMixin
import vaadin.scala.internal.WrapperUtil
import vaadin.scala.internal.SelectedTabChangeListener

package mixins {
  trait TabSheetMixin extends AbstractComponentContainerMixin
}

object TabSheet {

  class Tab(val p: com.vaadin.ui.TabSheet.Tab) {

    def visible = p.isVisible
    def visible_=(visible: Boolean) = p.setVisible(visible)

    def closable = p.isClosable
    def closable_=(closable: Boolean) = p.setClosable(closable)

    def enabled = p.isEnabled
    def enabled_=(enabled: Boolean) = p.setEnabled(enabled)

    def caption = Option(p.getCaption)
    def caption_=(caption: Option[String]) = p.setCaption(caption.getOrElse(null))
    def caption_=(caption: String) = p.setCaption(caption)

    def icon: Option[Resource] = WrapperUtil.wrapperFor[Resource](p.getIcon)
    def icon_=(icon: Option[Resource]) = if (icon.isDefined) p.setIcon(icon.get.p) else p.setIcon(null)
    def icon_=(icon: Resource) = if (icon == null) p.setIcon(null) else p.setIcon(icon.p)

    def description = Option(p.getDescription)
    def description_=(description: Option[String]) = p.setDescription(description.getOrElse(null))
    def description_=(description: String) = p.setDescription(description)

    // TODO: component error

    def component = WrapperUtil.wrapperFor[Component](p.getComponent).get

    def styleName = Option(p.getStyleName)
    def styleName_=(styleName: Option[String]) = p.setStyleName(styleName.getOrElse(null))
    def styleName_=(styleName: String) = p.setStyleName(styleName)
  }

  case class TabCloseEvent(tabSheet: TabSheet, component: Component, tab: TabSheet.Tab) extends Event
  case class SelectedTabChangeEvent(tabSheet: TabSheet) extends Event

}

class TabSheet(override val p: com.vaadin.ui.TabSheet with TabSheetMixin = new com.vaadin.ui.TabSheet with TabSheetMixin) extends AbstractComponentContainer(p) {

  // TODO: change to protected/private
  val tabs = scala.collection.mutable.Map.empty[com.vaadin.ui.TabSheet.Tab, TabSheet.Tab]
  protected def register(vaadinTab: com.vaadin.ui.TabSheet.Tab): TabSheet.Tab = {
    val tab = new TabSheet.Tab(vaadinTab)
    tabs += vaadinTab -> tab
    tab
  }
  
  def removeTab(tab: TabSheet.Tab) = {
    tabs -= tab.p
    p.removeTab(tab.p)
  } 
  
  override def removeComponent(component: Component) = {
    tab(component) match {
      case Some(tab) => tabs -= tab.p
      case None =>
    }
    super.removeComponent(component)
  }

  def addTab(component: Component) = register(p.addTab(component.p))
  def addTab(component: Component, caption: String) = register(p.addTab(component.p, caption))
  def addTab(component: Component, caption: String, icon: Resource) = register(p.addTab(component.p, caption, icon.p))
  def addTab(component: Component, caption: String, icon: Resource, position: Int) = register(p.addTab(component.p, caption, icon.p, position))
  def addTab(component: Component, position: Int) = register(p.addTab(component.p, position))

  def tabsVisible = !p.areTabsHidden
  def tabsVisible_=(tabsVisible: Boolean) = p.hideTabs(!tabsVisible)

  def tab(component: Component): Option[TabSheet.Tab] = tabs.get(p.getTab(component.p))
  def tab(position: Int): Option[TabSheet.Tab] = tabs.get(p.getTab(position))

  def selectedComponent = wrapperFor[Component](p.getSelectedTab).get
  def selectedComponent_=(component: Component) = p.setSelectedTab(component.p)

  def selectedTab: TabSheet.Tab = tab(selectedComponent).get
  def selectedTab_=(tab: TabSheet.Tab) = { selectedComponent = tab.component }

  def tabPosition(tab: TabSheet.Tab) = p.getTabPosition(tab.p)
  def tabPosition(tab: TabSheet.Tab, position: Int) = p.setTabPosition(tab.p, position)

  private class TabCloseHandler(val action: TabSheet.TabCloseEvent => Unit) extends com.vaadin.ui.TabSheet.CloseHandler {
    def onTabClose(tabsheet: com.vaadin.ui.TabSheet, tabContent: com.vaadin.ui.Component) = {
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
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Button#ClickEvent])
    override def addListener(elem: TabSheet.SelectedTabChangeEvent => Unit) = p.addListener(new SelectedTabChangeListener(elem))
    override def removeListener(elem: SelectedTabChangeListener) = p.removeListener(elem)
  }
}