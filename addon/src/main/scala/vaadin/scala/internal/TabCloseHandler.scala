package vaadin.scala.internal

import vaadin.scala._

class TabCloseHandler(val action: TabSheet.TabCloseEvent => Unit) extends com.vaadin.ui.TabSheet.CloseHandler {
  def onTabClose(tabsheet: com.vaadin.ui.TabSheet, tabContent: com.vaadin.ui.Component): Unit = {
    val ts = WrapperUtil.wrapperFor[TabSheet](tabsheet).get
    val component = WrapperUtil.wrapperFor[Component](tabContent).get
    action(TabSheet.TabCloseEvent(ts, component, ts.getTab(component).get))
  }
}
