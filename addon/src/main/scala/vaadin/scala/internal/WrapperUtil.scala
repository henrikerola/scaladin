package vaadin.scala.internal

import vaadin.scala.mixins.ScaladinMixin

object WrapperUtil {

  def wrapperFor[T](vaadinComponent: Any): Option[T] = {
    if (vaadinComponent == null)
      None
    else
      vaadinComponent.asInstanceOf[ScaladinMixin].wrapper.map { _.asInstanceOf[T] }
  }
}