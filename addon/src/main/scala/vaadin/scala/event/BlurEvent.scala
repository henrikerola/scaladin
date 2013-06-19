package vaadin.scala.event

import vaadin.scala.Component

case class BlurEvent(component: Component) extends ComponentEvent(component)