package vaadin.scala

import com.vaadin.ui.{ PasswordField => VaadinPasswordField }
import vaadin.scala.mixins.PasswordFieldMixin

package mixins {
  trait PasswordFieldMixin extends AbstractTextFieldMixin { self: com.vaadin.ui.PasswordField => }
}

class PasswordField(
  override val p: VaadinPasswordField with PasswordFieldMixin = new VaadinPasswordField with PasswordFieldMixin)
    extends AbstractTextField(p)