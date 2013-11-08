package vaadin.scala.server

import vaadin.scala.event.Event
import vaadin.scala.internal.{ SessionInitListener, ListenersTrait }
import vaadin.scala.server.mixins.ScaladinServletServiceMixin
import com.vaadin.server.VaadinRequest
import vaadin.scala.{ ListenersSet, Wrapper }

package mixins {

  import vaadin.scala.mixins.ScaladinMixin

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

  def classLoader: ClassLoader = p.getClassLoader()

  def deploymentConfiguration = p.getDeploymentConfiguration()
}

class ScaladinServletService(override val p: com.vaadin.server.VaadinServletService with ScaladinServletServiceMixin)
    extends ScaladinService {
  p.wrapper = this

  def init(): ScaladinServletService = { p.init(); this }
}
