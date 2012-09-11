package vaadin.scala

import vaadin.scala.internal.ItemClickListener
import vaadin.scala.internal.ListenersTrait

case class ItemClickEvent(component: Component, item: Item, itemId: Any, propertyId: Option[Any], button: Int, clientX: Int, clientY: Int, relativeX: Int, relativeY: Int, doubleClick: Boolean, altKey: Boolean, ctrlKey: Boolean, metaKey: Boolean, shiftKey: Boolean) extends AbstractClickEvent(component, button, clientX, clientY, relativeX, relativeY, doubleClick, altKey, ctrlKey, metaKey, shiftKey)

trait ItemClickNotifier { self: { def p: { def getListeners(eventType: Class[_]): java.util.Collection[_]; def addListener(listener: com.vaadin.event.ItemClickEvent.ItemClickListener); def removeListener(listener: com.vaadin.event.ItemClickEvent.ItemClickListener); } } =>
  lazy val itemClickListeners = new ListenersTrait[ItemClickEvent, ItemClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.ItemClickEvent.ItemClickListener])
    override def addListener(elem: ItemClickEvent => Unit) = p.addListener(new ItemClickListener(elem))
    override def removeListener(elem: ItemClickListener) = p.removeListener(elem)
  }
}

package internal {
  class ItemClickListener(val action: ItemClickEvent => Unit) extends com.vaadin.event.ItemClickEvent.ItemClickListener with Listener {
    def itemClick(e: com.vaadin.event.ItemClickEvent): Unit = action(ItemClickEvent(wrapperFor[Table](e.getComponent).get, new BasicItem(e.getItem), e.getItemId, Option(e.getPropertyId), e.getButton, e.getClientX, e.getClientY, e.getRelativeX, e.getRelativeY, e.isDoubleClick, e.isAltKey, e.isCtrlKey, e.isMetaKey, e.isShiftKey))
  }
}
