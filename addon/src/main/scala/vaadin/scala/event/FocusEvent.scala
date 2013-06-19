package vaadin.scala.event

import vaadin.scala.Component

case class FocusEvent(component: Component) extends ComponentEvent(component)
