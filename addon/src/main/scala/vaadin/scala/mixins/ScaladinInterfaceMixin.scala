package vaadin.scala.mixins

import vaadin.scala.InterfaceWrapper

trait ScaladinInterfaceMixin {
  private[this] var _wrapper: InterfaceWrapper = _
  def wrapper: InterfaceWrapper = _wrapper
  def wrapper_=(wrapper: InterfaceWrapper) = {
    if (_wrapper != null)
      throw new RuntimeException("wrapper cannot be reset once set")
    else
      _wrapper = wrapper
  }
}
