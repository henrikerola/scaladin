package vaadin.scala.mixins

import vaadin.scala.Wrapper

trait ScaladinMixin {
  private[this] var _wrapper: Wrapper = _
  def wrapper: Wrapper = _wrapper
  def wrapper_=(wrapper: Wrapper) = {
    if (_wrapper != null)
      throw new RuntimeException("wrapper cannot be reset once set")
    else
      _wrapper = wrapper
  }
}
