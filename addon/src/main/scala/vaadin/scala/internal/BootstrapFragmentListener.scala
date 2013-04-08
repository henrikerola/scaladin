package vaadin.scala.internal

import com.vaadin.server.{ BootstrapFragmentResponse, BootstrapPageResponse }
import collection.JavaConverters._
import collection.mutable
import org.jsoup.nodes.Node
import vaadin.scala.{ UI, ScaladinRequest, ScaladinSession }

/**
 * @author Henri Kerola / Vaadin
 */
class BootstrapFragmentListener(val action: ScaladinSession.BootstrapFragmentResponse => Unit)
    extends com.vaadin.server.BootstrapListener with Listener {

  def modifyBootstrapFragment(response: BootstrapFragmentResponse) {
    val request = new ScaladinRequest { val p = response.getRequest }
    val session = wrapperFor[ScaladinSession](response.getSession).get
    val uiClass: Class[_ <: UI] = null // TODO
    val uiProvider = response.getUIProvider
    val nodes: mutable.Buffer[Node] = response.getFragmentNodes.asScala
    action(ScaladinSession.BootstrapFragmentResponse(request, session, uiClass, uiProvider, nodes))
  }

  def modifyBootstrapPage(response: BootstrapPageResponse) {
  }
}
