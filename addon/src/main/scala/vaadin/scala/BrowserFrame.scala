package vaadin.scala

import com.vaadin.ui.{ BrowserFrame => VaadinBrowserFrame }
import vaadin.scala.mixins.BrowserFrameMixin

package mixins {
  trait BrowserFrameMixin extends AbstractEmbeddedMixin {
    self: com.vaadin.ui.BrowserFrame =>
  }
}

/**
 * @see com.vaadin.ui.BrowserFrame
 * @author Henri Kerola / Vaadin
 */
class BrowserFrame(
  override val p: VaadinBrowserFrame with BrowserFrameMixin = new VaadinBrowserFrame with BrowserFrameMixin)
    extends AbstractEmbedded(p)
