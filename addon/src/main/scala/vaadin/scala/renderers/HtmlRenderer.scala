package vaadin.scala.renderers

import com.vaadin.ui.renderers.{ HtmlRenderer => VaadinHtmlRenderer }
import vaadin.scala.renderers.mixins.HtmlRendererMixin

package mixins {
  trait HtmlRendererMixin extends AbstractRendererMixin
}

object HtmlRenderer {
  def apply(): HtmlRenderer = new HtmlRenderer
}

/**
 * @see com.vaadin.ui.renderer.HtmlRenderer
 * @author Henri Kerola / Vaadin
 */
class HtmlRenderer extends AbstractRenderer[String](new VaadinHtmlRenderer with HtmlRendererMixin)