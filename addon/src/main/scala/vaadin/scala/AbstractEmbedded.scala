package vaadin.scala

import vaadin.scala.mixins.AbstractEmbeddedMixin
import vaadin.scala.server.Resource

package mixins {
  trait AbstractEmbeddedMixin extends AbstractComponentMixin {
    self: com.vaadin.ui.AbstractEmbedded =>
  }
}

/**
 * @see com.vaadin.ui.AbstractEmbedded
 * @author Henri Kerola / Vaadin
 */
class AbstractEmbedded(override val p: com.vaadin.ui.AbstractEmbedded with AbstractEmbeddedMixin)
    extends AbstractComponent(p) {

  def source: Option[Resource] = wrapperFor(p.getSource)
  def source_=(source: Option[Resource]) { p.setSource(peerFor(source)) }
  def source_=(source: Resource) { p.setSource(source.pResource) }

  def alternateText: Option[String] = Option(p.getAlternateText)
  def alternateText_=(alternateText: String) { p.setAlternateText(alternateText) }
  def alternateText_=(alternateText: Option[String]) { p.setAlternateText(alternateText.orNull) }

}
