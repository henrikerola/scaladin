package vaadin.scala.internal

import com.vaadin.server.{ BootstrapFragmentResponse, BootstrapPageResponse }
import collection.JavaConverters._
import collection.mutable
import org.jsoup.nodes.Node
import vaadin.scala.ScaladinSession

/**
 * @author Henri Kerola / Vaadin
 */
class BootstrapFragmentListener(val action: ScaladinSession.BootstrapFragmentResponse => Unit)
    extends com.vaadin.server.BootstrapListener with Listener {

  def modifyBootstrapFragment(response: BootstrapFragmentResponse) {
    val nodes: mutable.Buffer[Node] = response.getFragmentNodes.asScala
    action(ScaladinSession.BootstrapFragmentResponse(nodes))
  }

  def modifyBootstrapPage(response: BootstrapPageResponse) {
  }
}
