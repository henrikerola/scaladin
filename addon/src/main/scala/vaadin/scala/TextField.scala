package vaadin.scala

import vaadin.scala.mixins.TextFieldMixin

package mixins {
  trait TextFieldMixin extends AbstractTextFieldMixin { self: com.vaadin.ui.TextField => }
}

class TextField(
  override val p: com.vaadin.ui.TextField with TextFieldMixin = new com.vaadin.ui.TextField with TextFieldMixin)
    extends AbstractTextField(p)