package vaadin.scala.renderers

import com.vaadin.ui.renderers.{ TextRenderer => VaadinTextRenderer }
import vaadin.scala.renderers.mixins.TextRendererMixin

package mixins {
  trait TextRendererMixin extends AbstractRendererMixin
}

/**
 * @see com.vaadin.ui.renderer.TextRenderer
 * @author Henri Kerola / Vaadin
 */
class TextRenderer extends AbstractRenderer[String](new VaadinTextRenderer with TextRendererMixin)