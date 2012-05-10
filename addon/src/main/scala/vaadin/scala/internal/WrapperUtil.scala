package vaadin.scala.internal

import vaadin.scala.mixins.ScaladinMixin

object WrapperUtil {
  
  def wrapperFor[T](vaadinComponent: Any): Option[T] = {
    if (vaadinComponent == null)
      None
    else
      Option(vaadinComponent.asInstanceOf[ScaladinMixin].wrapper.asInstanceOf[T])
  }
}