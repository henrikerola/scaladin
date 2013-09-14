package vaadin.scala

import com.vaadin.shared.communication.{ PushMode => VaadinPushMode }
import com.vaadin.shared.ui.ui.{ Transport => VaadinTransport }
import vaadin.scala.PushConfiguration.Transport

object PushConfiguration {

  object Transport extends Enumeration {
    import com.vaadin.shared.ui.ui.Transport._

    val WebSocket = Value(WEBSOCKET.ordinal)
    val Streaming = Value(STREAMING.ordinal)
  }
}

/**
 * @author Henri Kerola / Vaadin
 */
trait PushConfiguration extends Wrapper {

  val p: com.vaadin.ui.PushConfiguration

  def pushMode: PushMode.Value = PushMode(p.getPushMode.ordinal)
  def pushMode_=(pushMode: PushMode.Value): Unit = p.setPushMode(VaadinPushMode.values.apply(pushMode.id))

  def transport: Option[Transport.Value] = Option(p.getTransport) map { t => Transport(t.ordinal) }
  def transport_=(transport: Transport.Value): Unit = p.setTransport(VaadinTransport.values.apply(transport.id))

  def fallbackTransport: Option[Transport.Value] = Option(p.getFallbackTransport) map { t => Transport(t.ordinal) }
  def fallbackTransport_=(fallbackTransport: Transport.Value): Unit =
    p.setFallbackTransport(VaadinTransport.values.apply(fallbackTransport.id))

  // TODO: getParameter, setParameter, getParameterNames

}
