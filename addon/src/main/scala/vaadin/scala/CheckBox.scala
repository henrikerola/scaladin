package vaadin.scala

import com.vaadin.ui.{ CheckBox => VaadinCheckBox }
import vaadin.scala.mixins.CheckBoxMixin

package mixins {
  trait CheckBoxMixin extends AbstractFieldMixin[Boolean, java.lang.Boolean] { self: com.vaadin.ui.CheckBox => }
}
/**
 * A two-state component, which value is `true` or `false`. Possible
 * value `null` coming from Vaadin Java APIs is considered as `false`.
 *
 * @see com.vaadin.ui.CheckBox
 * @author Henri Kerola / Vaadin
 */
class CheckBox(override val p: VaadinCheckBox with CheckBoxMixin = new VaadinCheckBox with CheckBoxMixin)
    extends AbstractField[Boolean, java.lang.Boolean](p) {

  def value_=(value: Boolean) { p.setValue(value) }

  def booleanValue: Boolean = if (p.getValue == null) false else p.getValue

  override def value: Option[Boolean] = if (p.getValue == null) Some(false) else Some(p.getValue)
}
