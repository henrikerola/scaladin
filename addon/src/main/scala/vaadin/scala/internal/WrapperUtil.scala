package vaadin.scala.internal

import vaadin.scala.mixins.ScaladinMixin
import vaadin.scala.Wrapper

object WrapperUtil {

  private[this] val NullWrapper = new Wrapper {
    val p = null
  }

  def wrapperFor[T](vaadinComponent: Any): Option[T] = {
    if (vaadinComponent == null)
      None
    else
      vaadinComponent.asInstanceOf[ScaladinMixin].wrapper match {
        case null => None
        case wrapper => Some(wrapper.asInstanceOf[T])
      }
  }

  def peerFor[T](wrapper: Option[Wrapper]): T =
    wrapper.getOrElse(NullWrapper).p.asInstanceOf[T]
}