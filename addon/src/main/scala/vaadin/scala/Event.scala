package vaadin.scala

trait Event

// TODO: button should be enumeration or something instead of Int
abstract class AbstractClickEvent(component: Component, button: Int, clientX: Int, clientY: Int, relativeX: Int, relativeY: Int, doubleClick: Boolean, altKey: Boolean, ctrlKey: Boolean, metaKey: Boolean, shiftKey: Boolean) extends Event

case class ClickEvent(component: Component, button: Int, clientX: Int, clientY: Int, relativeX: Int, relativeY: Int, doubleClick: Boolean, altKey: Boolean, ctrlKey: Boolean, metaKey: Boolean, shiftKey: Boolean) extends AbstractClickEvent(component, button, clientX, clientY, relativeX, relativeY, doubleClick, altKey, ctrlKey, metaKey, shiftKey)

class ClickListener(val action: ClickEvent => Unit) extends com.vaadin.event.MouseEvents.ClickListener with Listener {
  def click(e: com.vaadin.event.MouseEvents.ClickEvent) = action(ClickEvent(WrapperRegistry.get[Table](e.getComponent).get, e.getButton, e.getClientX, e.getClientY, e.getRelativeX, e.getRelativeY, e.isDoubleClick, e.isAltKey, e.isCtrlKey, e.isMetaKey, e.isShiftKey))
}