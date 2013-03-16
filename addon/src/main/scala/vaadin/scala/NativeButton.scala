package vaadin.scala

import com.vaadin.ui.{ NativeButton => VaadinNativeButton }
import vaadin.scala.mixins.NativeButtonMixin

package mixins {
  trait NativeButtonMixin extends ButtonMixin { self: com.vaadin.ui.NativeButton => }
}

/**
 * @see com.vaadin.ui.NativeButton
 * @author Henri Kerola / Vaadin
 */
class NativeButton(
  override val p: VaadinNativeButton with NativeButtonMixin = new VaadinNativeButton with NativeButtonMixin)
    extends Button(p)