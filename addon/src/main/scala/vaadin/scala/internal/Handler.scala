package vaadin.scala.internal

trait Handler[T, R] {
  def action: T => R

  protected def wrapperFor[T](maybeWrapper: Any): Option[T] = WrapperUtil.wrapperFor[T](maybeWrapper)
}