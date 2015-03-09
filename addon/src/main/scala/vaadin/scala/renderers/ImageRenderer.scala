package vaadin.scala.renderers

import com.vaadin.ui.renderers.{ ImageRenderer => VaadinImageRenderer }
import com.vaadin.server.{ Resource => VaadinResource }
import vaadin.scala.renderers.mixins.ImageRendererMixin

package mixins {
  trait ImageRendererMixin extends ClickableRendererMixin
}

object ImageRenderer {

  def apply(): ImageRenderer = new ImageRenderer

  def apply(clickListener: ClickableRenderer.RendererClickEvent => Unit): ImageRenderer =
    new ImageRenderer {
      clickListeners += clickListener
    }
}

/**
 * @see com.vaadin.ui.renderer.ImageRenderer
 * @author Henri Kerola / Vaadin
 */
class ImageRenderer(
  override val p: VaadinImageRenderer with ImageRendererMixin = new VaadinImageRenderer with ImageRendererMixin)
    extends ClickableRenderer[VaadinResource](p)

