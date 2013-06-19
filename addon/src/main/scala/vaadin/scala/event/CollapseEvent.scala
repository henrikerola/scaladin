package vaadin.scala.event

import vaadin.scala.Component

case class CollapseEvent(component: Component, itemId: Any) extends ComponentEvent(component)