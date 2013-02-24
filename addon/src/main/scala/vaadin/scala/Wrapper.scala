package vaadin.scala

import vaadin.scala.internal.WrapperUtil

trait Wrapper extends Serializable {
  def p: Any

  protected def wrapperFor[T](maybeWrapper: Any): Option[T] = WrapperUtil.wrapperFor[T](maybeWrapper)

  protected def peerFor[T](wrapper: Option[Wrapper]): T = WrapperUtil.peerFor[T](wrapper)
}