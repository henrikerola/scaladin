package vaadin.scala.internal

import vaadin.scala.Table
import vaadin.scala.Listener

class FooterClickListener(val action: Table.FooterClickEvent => Unit) extends com.vaadin.ui.Table.FooterClickListener with Listener {
  def footerClick(e: com.vaadin.ui.Table.FooterClickEvent) = action(Table.FooterClickEvent(wrapperFor[Table](e.getComponent).get, e.getPropertyId, e.getButton, e.getClientX, e.getClientY, e.getRelativeX, e.getRelativeY, e.isDoubleClick, e.isAltKey, e.isCtrlKey, e.isMetaKey, e.isShiftKey))
}

class HeaderClickListener(val action: Table.HeaderClickEvent => Unit) extends com.vaadin.ui.Table.HeaderClickListener with Listener {
  def headerClick(e: com.vaadin.ui.Table.HeaderClickEvent) = action(Table.HeaderClickEvent(wrapperFor[Table](e.getComponent).get, e.getPropertyId, e.getButton, e.getClientX, e.getClientY, e.getRelativeX, e.getRelativeY, e.isDoubleClick, e.isAltKey, e.isCtrlKey, e.isMetaKey, e.isShiftKey))
}

class ColumnResizeListener(val action: Table.ColumnResizeEvent => Unit) extends com.vaadin.ui.Table.ColumnResizeListener with Listener {
  def columnResize(e: com.vaadin.ui.Table.ColumnResizeEvent) = action(Table.ColumnResizeEvent(wrapperFor[Table](e.getComponent).get, e.getPropertyId, e.getPreviousWidth, e.getCurrentWidth))
}