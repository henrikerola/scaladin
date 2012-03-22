package vaadin.scala

trait Event

// TODO: button should be enumeration or something instead of Int
class ClickEvent(component: Component, button: Int, clientX: Int, clientY: Int, relativeX: Int, relativeY: Int, doubleClick: Boolean, altKey: Boolean, ctrlKey: Boolean, metaKey: Boolean, shiftKey: Boolean) extends Event