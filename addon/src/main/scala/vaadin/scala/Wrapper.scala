package vaadin.scala

import vaadin.scala.internal.WrapperUtil

trait Wrapper extends Serializable {
  def p: Any

  protected def wrapperFor[T](vaadinComponent: Any): Option[T] = WrapperUtil.wrapperFor[T](vaadinComponent)

  protected def peerFor[T](wrapper: Option[Wrapper]): T = WrapperUtil.peerFor[T](wrapper)
}