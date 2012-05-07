package vaadin.scala

import vaadin.scala.mixins.PasswordFieldMixin

package mixins {
  trait PasswordFieldMixin extends AbstractTextFieldMixin
}

class PasswordField(override val p: com.vaadin.ui.PasswordField with PasswordFieldMixin = new com.vaadin.ui.PasswordField with PasswordFieldMixin) extends AbstractTextField(p) {

}