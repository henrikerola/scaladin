package vaadin.scala

import vaadin.scala.mixins.CheckBoxMixin

package mixins {
  trait CheckBoxMixin extends AbstractFieldMixin
}

class CheckBox(override val p: com.vaadin.ui.CheckBox with CheckBoxMixin = new com.vaadin.ui.CheckBox with CheckBoxMixin)
  extends AbstractField[Boolean](p) {

  // Have to be Option[Boolean] because we override Option[Any]...
  override def value: Option[Boolean] = Option(p.getValue);
  def value_=(value: Boolean) = p.setValue(value)
  
  def booleanValue: Boolean = p.getValue
}
