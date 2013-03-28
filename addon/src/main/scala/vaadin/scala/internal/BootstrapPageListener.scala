package vaadin.scala.internal

import vaadin.scala.ScaladinSession
import com.vaadin.server.{ BootstrapFragmentResponse, BootstrapPageResponse }

/**
 * @author Henri Kerola / Vaadin
 */
class BootstrapPageListener(val action: ScaladinSession.BootstrapPageResponse => Unit)
    extends com.vaadin.server.BootstrapListener with Listener {

  def modifyBootstrapPage(response: BootstrapPageResponse) {
    val document = response.getDocument
    ScaladinSession.BootstrapPageResponse(document)

  }

  def modifyBootstrapFragment(response: BootstrapFragmentResponse) {

  }

}
