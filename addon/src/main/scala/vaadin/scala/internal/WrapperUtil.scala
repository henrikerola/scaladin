package vaadin.scala.internal

import vaadin.scala.mixins.{ ScaladinInterfaceMixin, ScaladinMixin }
import vaadin.scala.Wrapper

object WrapperUtil {

  def wrapperFor[T](maybeWrapper: Any): Option[T] = {
    if (maybeWrapper == null)
      None
    else
      maybeWrapper match {
        case mixin: ScaladinMixin => Option(mixin.wrapper.asInstanceOf[T])
        case interfaceMixin: ScaladinInterfaceMixin => Option(interfaceMixin.wrapper.asInstanceOf[T])
      }
  }

  def peerFor[T](wrapper: Option[Wrapper]): T =
    wrapper.fold(null.asInstanceOf[T])(_.p.asInstanceOf[T])

  def peerFor[T](wrapper: Wrapper): T =
    if (wrapper != null) wrapper.p.asInstanceOf[T] else null.asInstanceOf[T]
}