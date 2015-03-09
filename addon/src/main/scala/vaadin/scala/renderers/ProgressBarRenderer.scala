package vaadin.scala.renderers

import com.vaadin.ui.renderers.{ ProgressBarRenderer => VProgressBarRenderer }
import vaadin.scala.renderers.mixins.{ ProgressBarRendererMixin => Mixin }

package mixins {
  trait ProgressBarRendererMixin extends AbstractRendererMixin
}

object ProgressBarRenderer {

  def apply(): ProgressBarRenderer = new ProgressBarRenderer

}

/**
 * @see com.vaadin.ui.renderer.ProgressBarRenderer
 * @author Henri Kerola / Vaadin
 */
class ProgressBarRenderer(override val p: VProgressBarRenderer with Mixin = new VProgressBarRenderer with Mixin)
  extends AbstractRenderer[java.lang.Double](p)