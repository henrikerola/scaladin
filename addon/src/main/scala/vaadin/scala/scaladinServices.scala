package vaadin.scala

import event.Event
import internal.{ SessionInitListener, ListenersTrait }
import vaadin.scala.mixins.ScaladinServletServiceMixin
import com.vaadin.server.VaadinRequest
import vaadin.scala.server.{ ScaladinRequest, ScaladinSession }
import vaadin.scala.server.mixins.VaadinSessionMixin

package mixins {

  trait ScaladinServletServiceMixin extends ScaladinMixin { self: com.vaadin.server.VaadinServletService =>
    override def createVaadinSession(request: VaadinRequest) =
      new ScaladinSession(new com.vaadin.server.VaadinSession(this) with VaadinSessionMixin).p
  }
}

object ScaladinService {
  case class SessionInitEvent(
    service: ScaladinService,
    session: ScaladinSession,
    request: ScaladinRequest) extends Event
}

/**
 * @author Henri Kerola / Vaadin
 */
trait ScaladinService extends Wrapper {

  val p: com.vaadin.server.VaadinService

  lazy val sessionInitListeners: ListenersSet[ScaladinService.SessionInitEvent => Unit] =
    new ListenersTrait[ScaladinService.SessionInitEvent, SessionInitListener] {
      override def listeners = null // TODO
      override def addListener(elem: ScaladinService.SessionInitEvent => Unit) =
        p.addSessionInitListener(new SessionInitListener(elem))
      override def removeListener(elem: SessionInitListener) = p.removeSessionInitListener(elem)
    }

}

class ScaladinServletService(override val p: com.vaadin.server.VaadinServletService with ScaladinServletServiceMixin)
    extends ScaladinService {
  p.wrapper = this

  def init(): Unit = p.init()
}
