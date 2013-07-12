package vaadin.scala.mixins

import vaadin.scala.Wrapper

trait TypedScaladinMixin[C <: Wrapper] extends ScaladinMixin {
  override def wrapper: C = super.wrapper.asInstanceOf[C]
}
