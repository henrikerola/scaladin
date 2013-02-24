package vaadin.scala.internal

trait Listener {
  def action: Any

  protected def wrapperFor[T](maybeWrapper: Any): Option[T] = WrapperUtil.wrapperFor[T](maybeWrapper)
}