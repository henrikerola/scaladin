package vaadin.scala.internal

import com.vaadin.server.SessionInitEvent
import vaadin.scala.{ ScaladinRequest, ScaladinService, ScaladinSession }

/**
 * @author Henri Kerola / Vaadin
 */
class SessionInitListener(val action: ScaladinService.SessionInitEvent => Unit)
    extends com.vaadin.server.SessionInitListener with Listener {

  def sessionInit(event: SessionInitEvent) {
    val service = wrapperFor[ScaladinService](event.getService).get
    val session = wrapperFor[ScaladinSession](event.getSession).get
    val request = new ScaladinRequest { val p = event.getRequest }
    action(ScaladinService.SessionInitEvent(service, session, request))
  }
}
