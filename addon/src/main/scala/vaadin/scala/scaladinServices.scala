package vaadin.scala

import vaadin.scala.mixins.ScaladinServletServiceMixin
import com.vaadin.server.VaadinRequest

package mixins {
  trait ScaladinServletServiceMixin extends ScaladinMixin {self: com.vaadin.server.VaadinServletService =>
    override def createVaadinSession(request: VaadinRequest) =
      new ScaladinSession(new com.vaadin.server.VaadinSession(this)).p
  }
}

/**
 * @author Henri Kerola / Vaadin
 */
trait ScaladinService extends Wrapper {

  val p: com.vaadin.server.VaadinService

}

class ScaladinServletService(override val p: com.vaadin.server.VaadinServletService with ScaladinServletServiceMixin) extends ScaladinService {
  p.wrapper = this
}
