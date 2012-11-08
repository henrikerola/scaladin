package vaadin.scala.internal

trait Listener {
  def action: Any

  protected def wrapperFor[T](wrapped: Any): Option[T] = WrapperUtil.wrapperFor[T](wrapped)
}