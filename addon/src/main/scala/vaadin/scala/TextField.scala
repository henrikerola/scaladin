package vaadin.scala

import vaadin.scala.mixins.TextFieldMixin

package mixins {
  trait TextFieldMixin extends AbstractTextFieldMixin
}

class TextField(override val p: com.vaadin.ui.TextField with TextFieldMixin = new com.vaadin.ui.TextField with TextFieldMixin) extends AbstractTextField(p) {

}