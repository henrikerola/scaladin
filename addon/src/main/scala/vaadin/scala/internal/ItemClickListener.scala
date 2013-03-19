package vaadin.scala.internal

import com.vaadin.event.ItemClickEvent.{ ItemClickListener => VaadinItemClickListener }
import vaadin.scala._
import event.ItemClickEvent

class ItemClickListener(val action: ItemClickEvent => Unit) extends VaadinItemClickListener with Listener {

  def itemClick(e: com.vaadin.event.ItemClickEvent) {
    val event = ItemClickEvent(
      wrapperFor[Table](e.getComponent).get,
      new BasicItem(e.getItem),
      e.getItemId,
      Option(e.getPropertyId),
      MouseButton(e.getButton.ordinal),
      e.getClientX,
      e.getClientY,
      e.getRelativeX,
      e.getRelativeY,
      e.isDoubleClick,
      e.isAltKey,
      e.isCtrlKey,
      e.isMetaKey,
      e.isShiftKey)

    action(event)
  }
}
