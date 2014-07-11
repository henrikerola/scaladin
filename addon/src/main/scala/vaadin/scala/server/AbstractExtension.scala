package vaadin.scala.server

import vaadin.scala.{ AbstractComponent, Wrapper }
import vaadin.scala.mixins.ScaladinMixin
import vaadin.scala.server.mixins.AbstractExtensionMixin

package mixins {
  trait AbstractExtensionMixin extends ScaladinMixin
}

/**
 * @see com.vaadin.server.AbstractExtension
 * @author Henri Kerola / Vaadin
 */
abstract class AbstractExtension(override val p: com.vaadin.server.AbstractExtension with AbstractExtensionMixin)
    extends Wrapper {

  def remove(): Unit = p.remove()

}
