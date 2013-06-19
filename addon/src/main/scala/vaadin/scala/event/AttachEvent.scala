package vaadin.scala.event

import vaadin.scala.ClientConnector

case class AttachEvent(attachSource: ClientConnector) extends ConnectorEvent(attachSource)