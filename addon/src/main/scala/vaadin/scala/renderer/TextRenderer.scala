package vaadin.scala.renderer

import com.vaadin.ui.renderers.{ TextRenderer => VaadinTextRenderer }
import vaadin.scala.renderer.mixins.TextRendererMixin

package mixins {
  trait TextRendererMixin extends AbstractRendererMixin
}

/**
 * @see com.vaadin.ui.renderer.TextRenderer
 * @author Henri Kerola / Vaadin
 */
class TextRenderer extends AbstractRenderer[String](new VaadinTextRenderer with TextRendererMixin)