package vaadin.scala

import com.vaadin.ui.{ Image => VaadinImage }
import vaadin.scala.mixins.ImageMixin
import vaadin.scala.event.ClickNotifier

package mixins {
  trait ImageMixin extends AbstractEmbeddedMixin {
    self: com.vaadin.ui.Image =>
  }
}

/**
 * @see com.vaadin.ui.Image
 * @author Henri Kerola / Vaadin
 */
class Image(override val p: VaadinImage with ImageMixin = new VaadinImage with ImageMixin)
    extends AbstractEmbedded(p) with ClickNotifier {

}
