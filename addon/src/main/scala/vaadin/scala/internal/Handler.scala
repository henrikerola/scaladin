package vaadin.scala.internal

import scala.util.Try

trait Handler[T, R] {
  def action: T => R

  protected def wrapperFor[T](wrapped: Any): Option[T] = WrapperUtil.wrapperFor[T](wrapped)
}