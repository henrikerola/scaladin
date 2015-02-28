package vaadin.scala.renderers

import vaadin.scala.Wrapper
import vaadin.scala.mixins.ScaladinMixin
import vaadin.scala.renderers.mixins.RendererMixin

package mixins {
  trait RendererMixin extends ScaladinMixin
}

/**
 * @see com.vaadin.ui.renderer.Renderer
 * @author Henri Kerola / Vaadin
 */
trait Renderer[T] extends Wrapper {

  def p: com.vaadin.ui.renderers.Renderer[T] with RendererMixin

  def presentationType: Class[T] = p.getPresentationType
}