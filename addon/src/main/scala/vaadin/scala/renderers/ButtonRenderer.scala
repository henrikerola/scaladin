package vaadin.scala.renderers

import com.vaadin.ui.renderers.{ ButtonRenderer => VaadinButtonRenderer }
import vaadin.scala.renderers.mixins.ButtonRendererMixin

package mixins {
  trait ButtonRendererMixin extends ClickableRendererMixin
}

object ButtonRenderer {

  def apply(): ButtonRenderer = new ButtonRenderer

  def apply(clickListener: ClickableRenderer.RendererClickEvent => Unit): ButtonRenderer =
    new ButtonRenderer {
      clickListeners += clickListener
    }
}

/**
 * @see com.vaadin.ui.renderer.ButtonRenderer
 * @author Henri Kerola / Vaadin
 */
class ButtonRenderer(override val p: VaadinButtonRenderer with ButtonRendererMixin = new VaadinButtonRenderer with ButtonRendererMixin)
  extends ClickableRenderer[String](p)
