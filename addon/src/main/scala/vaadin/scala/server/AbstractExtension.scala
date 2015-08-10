package vaadin.scala.server

import vaadin.scala.{ ClientConnector, AbstractComponent, Wrapper }
import vaadin.scala.mixins.ScaladinMixin
import vaadin.scala.server.mixins.AbstractExtensionMixin

package mixins {

  import vaadin.scala.mixins.ClientConnectorMixin

  trait AbstractExtensionMixin extends ClientConnectorMixin {
    self: com.vaadin.server.AbstractExtension =>
  }
}

/**
 * @see com.vaadin.server.AbstractExtension
 * @author Henri Kerola / Vaadin
 */
abstract class AbstractExtension(override val p: com.vaadin.server.AbstractExtension with AbstractExtensionMixin)
    extends ClientConnector {

  p.wrapper = this

  def remove(): Unit = p.remove()

}
