package vaadin.scala.internal

trait Listener {
  def action: Any

  protected def wrapperFor[T](vaadinComponent: com.vaadin.ui.Component): Option[T] = WrapperUtil.wrapperFor[T](vaadinComponent)
}