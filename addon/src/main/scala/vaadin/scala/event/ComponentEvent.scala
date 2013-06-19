package vaadin.scala.event

import vaadin.scala.Component

class ComponentEvent(source: Component) extends ConnectorEvent(source) {

  def getComponent: Component = source.asInstanceOf[Component]
}