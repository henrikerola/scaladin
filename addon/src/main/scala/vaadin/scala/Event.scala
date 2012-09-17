package vaadin.scala

import vaadin.scala.internal.WrapperUtil

trait Event

abstract class AbstractClickEvent(component: Component, button: MouseButton.Value, clientX: Int, clientY: Int, relativeX: Int, relativeY: Int, doubleClick: Boolean, altKey: Boolean, ctrlKey: Boolean, metaKey: Boolean, shiftKey: Boolean) extends Event

case class ClickEvent(component: Component, button: MouseButton.Value, clientX: Int, clientY: Int, relativeX: Int, relativeY: Int, doubleClick: Boolean, altKey: Boolean, ctrlKey: Boolean, metaKey: Boolean, shiftKey: Boolean) extends AbstractClickEvent(component, button, clientX, clientY, relativeX, relativeY, doubleClick, altKey, ctrlKey, metaKey, shiftKey)

class ClickListener(val action: ClickEvent => Unit) extends com.vaadin.event.MouseEvents.ClickListener with Listener {
  def click(e: com.vaadin.event.MouseEvents.ClickEvent) = action(ClickEvent(wrapperFor[Table](e.getComponent).get, MouseButton(e.getButton.ordinal), e.getClientX, e.getClientY, e.getRelativeX, e.getRelativeY, e.isDoubleClick, e.isAltKey, e.isCtrlKey, e.isMetaKey, e.isShiftKey))
}