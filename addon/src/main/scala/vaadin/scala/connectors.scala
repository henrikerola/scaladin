package vaadin.scala

import vaadin.scala.mixins._
import vaadin.scala.event.ConnectorEvent

package mixins {

  trait ConnectorMixin extends ScaladinMixin { self: com.vaadin.shared.Connector =>
  }

  trait ClientConnectorMixin extends ConnectorMixin { self: com.vaadin.server.ClientConnector =>
  }
}

object ClientConnector {
  case class DetachEvent(detachSource: ClientConnector) extends ConnectorEvent(detachSource)
}
trait Connector extends Wrapper {
  def p: com.vaadin.shared.Connector with ConnectorMixin

  def connectorId: String = p.getConnectorId()
  def parent: Option[Connector] = wrapperFor(p.getParent())
}

trait ClientConnector extends Connector {
  def p: com.vaadin.server.ClientConnector with ClientConnectorMixin
}