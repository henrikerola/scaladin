package vaadin.scala.event

import vaadin.scala.{ MouseButton, Item, Component }

case class ItemClickEvent(
  component: Component,
  item: Item,
  itemId: Any,
  propertyId: Option[Any],
  button: MouseButton.Value,
  clientX: Int,
  clientY: Int,
  relativeX: Int,
  relativeY: Int,
  doubleClick: Boolean,
  altKey: Boolean,
  ctrlKey: Boolean,
  metaKey: Boolean,
  shiftKey: Boolean)
    extends AbstractClickEvent(
      component,
      button,
      clientX,
      clientY,
      relativeX, relativeY,
      doubleClick,
      altKey,
      ctrlKey,
      metaKey,
      shiftKey)