package vaadin.scala.event

import vaadin.scala.ClientConnector

case class DetachEvent(detachSource: ClientConnector) extends ConnectorEvent(detachSource)