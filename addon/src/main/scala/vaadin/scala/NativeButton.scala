package vaadin.scala

import vaadin.scala.mixins.NativeButtonMixin

package mixins {
  trait NativeButtonMixin extends ButtonMixin { self: com.vaadin.ui.NativeButton => }
}

/**
 * @see com.vaadin.ui.NativeButton
 * @author Henri Kerola / Vaadin
 */
class NativeButton(override val p: com.vaadin.ui.NativeButton with NativeButtonMixin = new com.vaadin.ui.NativeButton with NativeButtonMixin) extends Button(p)