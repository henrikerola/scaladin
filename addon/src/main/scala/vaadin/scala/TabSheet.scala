package vaadin.scala

object TabSheet {

  class Tab(val p: com.vaadin.ui.TabSheet.Tab) extends Wrapper {

    WrapperRegistry.put(this)

    def visible = p.isVisible
    def visible_=(visible: Boolean) = p.setVisible(visible)

    def closable = p.isClosable
    def closable_=(closable: Boolean) = p.setClosable(closable)

    def enabled = p.isEnabled
    def enabled_=(enabled: Boolean) = p.setEnabled(enabled)

    def caption = Option(p.getCaption)
    def caption_=(caption: Option[String]) = p.setCaption(caption.getOrElse(null))
    def caption_=(caption: String) = p.setCaption(caption)

    def icon: Option[Resource] = WrapperRegistry.get[Resource](p.getIcon)
    def icon_=(icon: Option[Resource]) = if (icon.isDefined) p.setIcon(icon.get.p) else p.setIcon(null)
    def icon_=(icon: Resource) = if (icon == null) p.setIcon(null) else p.setIcon(icon.p)

    def description = Option(p.getDescription)
    def description_=(description: Option[String]) = p.setDescription(description.getOrElse(null))
    def description_=(description: String) = p.setDescription(description)

    // TODO: component error
    
    def component = WrapperRegistry.get[Component](p.getComponent).get

    def styleName = Option(p.getStyleName)
    def styleName_=(styleName: Option[String]) = p.setStyleName(styleName.getOrElse(null))
    def styleName_=(styleName: String) = p.setStyleName(styleName)
  }

  case class TabCloseEvent(tabSheet: TabSheet, component: Component, tab: TabSheet.Tab) extends Event

}

class TabSheet(override val p: com.vaadin.ui.TabSheet = new com.vaadin.ui.TabSheet) extends AbstractComponentContainer(p) {
  
  WrapperRegistry.put(this)

  def removeTab(tab: TabSheet.Tab) = p.removeTab(tab.p)

  def addTab(component: Component) = new TabSheet.Tab(p.addTab(component.p))
  def addTab(component: Component, caption: String) = new TabSheet.Tab(p.addTab(component.p, caption))
  def addTab(component: Component, caption: String, icon: Resource) = new TabSheet.Tab(p.addTab(component.p, caption, icon.p))
  def addTab(component: Component, caption: String, icon: Resource, position: Int) = new TabSheet.Tab(p.addTab(component.p, caption, icon.p, position))
  def addTab(component: Component, position: Int) = new TabSheet.Tab(p.addTab(component.p, position))

  def tabsVisible = !p.areTabsHidden
  def tabsVisible_=(tabsVisible: Boolean) = p.hideTabs(!tabsVisible)

  def tab(component: Component): Option[TabSheet.Tab] = WrapperRegistry.get[TabSheet.Tab](p.getTab(component.p))
  def tab(position: Int): Option[TabSheet.Tab] = WrapperRegistry.get[TabSheet.Tab](p.getTab(position))

  def selectedComponent = WrapperRegistry.get[Component](p.getSelectedTab).get
  def selectedComponent_=(component: Component) = p.setSelectedTab(component.p)
  
  def selectedTab = tab(selectedComponent).get
  def selectedTab_=(tab: TabSheet.Tab) = { selectedComponent = tab.component }

  def tabPosition(tab: TabSheet.Tab) = p.getTabPosition(tab.p)
  def tabPosition(tab: TabSheet.Tab, position: Int) = p.setTabPosition(tab.p, position)

  private class TabCloseHandler(val action: TabSheet.TabCloseEvent => Unit) extends com.vaadin.ui.TabSheet.CloseHandler {
    def onTabClose(tabsheet: com.vaadin.ui.TabSheet, tabContent: com.vaadin.ui.Component) = {
      val component = WrapperRegistry.get[Component](tabContent).get
      action(TabSheet.TabCloseEvent(WrapperRegistry.get[TabSheet](tabsheet).get, component, tab(component).get))
    }
  }

  var _closeHandler: TabSheet.TabCloseEvent => Unit = (e: TabSheet.TabCloseEvent) => { removeComponent(e.component) }
  p.setCloseHandler(new TabCloseHandler(_closeHandler))

  def closeHandler: TabSheet.TabCloseEvent => Unit = _closeHandler

  def closeHandler_=(handler: TabSheet.TabCloseEvent => Unit): Unit = {
    _closeHandler = handler
    p.setCloseHandler(new TabCloseHandler(handler))
  }
  
  // TODO: SelectedTabChangeListener
}