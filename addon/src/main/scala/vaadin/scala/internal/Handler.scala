package vaadin.scala.internal

import scala.util.Try

trait Handler[T] {
  def action: T => Try[Unit]

  protected def wrapperFor[T](wrapped: Any): Option[T] = WrapperUtil.wrapperFor[T](wrapped)
}