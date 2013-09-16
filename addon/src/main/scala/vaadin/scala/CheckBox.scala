package vaadin.scala

import com.vaadin.ui.{ CheckBox => VaadinCheckBox }
import vaadin.scala.mixins.CheckBoxMixin

package mixins {
  trait CheckBoxMixin extends AbstractFieldMixin[java.lang.Boolean] { self: com.vaadin.ui.CheckBox => }
}

class CheckBox(override val p: VaadinCheckBox with CheckBoxMixin = new VaadinCheckBox with CheckBoxMixin)
    extends AbstractField[java.lang.Boolean](p) {

  def value_=(value: Boolean) { p.setValue(value) }

  def booleanValue: Boolean = p.getValue
}
