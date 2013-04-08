package vaadin.scala.internal

import vaadin.scala.{ UI, ScaladinRequest, ScaladinSession }
import com.vaadin.server.{ BootstrapFragmentResponse, BootstrapPageResponse }
import collection.mutable

/**
 * @author Henri Kerola / Vaadin
 */
class BootstrapPageListener(val action: ScaladinSession.BootstrapPageResponse => Unit)
    extends com.vaadin.server.BootstrapListener with Listener {

  def modifyBootstrapPage(response: BootstrapPageResponse) {
    val request = new ScaladinRequest { val p = response.getRequest }
    val session = wrapperFor[ScaladinSession](response.getSession).get
    val uiClass: Class[_ <: UI] = null // TODO
    val uiProvider = response.getUIProvider
    val document = response.getDocument
    val headers = mutable.Map.empty[String, String]
    action(ScaladinSession.BootstrapPageResponse(request, session, uiClass, uiProvider, document, headers))
    headers foreach { h => response.setHeader(h._1, h._2) }
  }

  def modifyBootstrapFragment(response: BootstrapFragmentResponse) {

  }

}
