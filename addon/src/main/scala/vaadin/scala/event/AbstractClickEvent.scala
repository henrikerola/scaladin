package vaadin.scala.event

import vaadin.scala.{ MouseButton, Component }

abstract class AbstractClickEvent(
  component: Component,
  button: MouseButton.Value,
  clientX: Int,
  clientY: Int,
  relativeX: Int,
  relativeY: Int,
  doubleClick: Boolean,
  altKey: Boolean,
  ctrlKey: Boolean,
  metaKey: Boolean,
  shiftKey: Boolean) extends ComponentEvent(component)
