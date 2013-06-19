package vaadin.scala.event

import vaadin.scala.ClientConnector

abstract class ConnectorEvent(val source: ClientConnector) extends Event