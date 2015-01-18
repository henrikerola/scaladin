package vaadin.scala

import com.vaadin.ui.{ NativeSelect => VaadinNativeSelect }
import vaadin.scala.event.{ FocusNotifier, BlurNotifier }
import vaadin.scala.mixins.NativeSelectMixin

package mixins {
  trait NativeSelectMixin extends AbstractSelectMixin { self: com.vaadin.ui.NativeSelect => }
}

class NativeSelect(
  override val p: VaadinNativeSelect with NativeSelectMixin = new VaadinNativeSelect with NativeSelectMixin)
    extends AbstractSelect(p) with BlurNotifier with FocusNotifier