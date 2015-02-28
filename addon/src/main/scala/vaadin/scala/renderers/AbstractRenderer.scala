package vaadin.scala.renderers

import com.vaadin.ui.Grid.{ AbstractRenderer => VaadinAbstractRenderer }
import vaadin.scala.renderers.mixins.AbstractRendererMixin

package mixins {
  trait AbstractRendererMixin extends RendererMixin
}

/**
 * @see com.vaadin.ui.Grid.AbstractRenderer
 * @author Henri Kerola / Vaadin
 */
class AbstractRenderer[T](val p: VaadinAbstractRenderer[T] with AbstractRendererMixin)
    extends Renderer[T] {

  p.wrapper = this
}
