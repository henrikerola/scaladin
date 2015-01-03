package vaadin.scala.renderer

import vaadin.scala.Wrapper
import vaadin.scala.mixins.ScaladinMixin
import vaadin.scala.renderer.mixins.RendererMixin

package mixins {
  trait RendererMixin extends ScaladinMixin
}

/**
 * @see com.vaadin.ui.renderer.Renderer
 * @author Henri Kerola / Vaadin
 */
trait Renderer[T] extends Wrapper {

  def p: com.vaadin.ui.renderer.Renderer[T] with RendererMixin

  def presentationType: Class[T] = p.getPresentationType
}