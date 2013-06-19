package vaadin.scala.event

import vaadin.scala.Component

case class ItemDescriptionEvent(component: Component, itemId: Any, propertyId: Any) extends ComponentEvent(component)