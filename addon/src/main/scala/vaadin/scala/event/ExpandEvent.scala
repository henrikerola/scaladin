package vaadin.scala.event

import vaadin.scala.Component

case class ExpandEvent(component: Component, itemId: Any) extends ComponentEvent(component)
