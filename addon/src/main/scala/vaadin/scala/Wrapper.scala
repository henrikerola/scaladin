package vaadin.scala

import vaadin.scala.internal.WrapperUtil
import vaadin.scala.server.Resource

trait Wrapper extends Serializable {
  def p: Any

  protected def wrapperFor[T](maybeWrapper: Any): Option[T] = WrapperUtil.wrapperFor[T](maybeWrapper)

  protected def peerFor[T](wrapper: Option[Wrapper]): T = WrapperUtil.peerFor[T](wrapper)

  protected def peerFor[T](wrapper: => Option[Resource]): T = WrapperUtil.peerFor[T](wrapper)
}