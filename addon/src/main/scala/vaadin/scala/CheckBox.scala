package vaadin.scala

import vaadin.scala.mixins.CheckBoxMixin
import vaadin.scala.listeners.BlurListeners
import vaadin.scala.listeners.FocusListeners

package mixins {
  trait CheckBoxMixin extends AbstractFieldMixin
}

// In Vaadin 6 CheckBox extends Button, but here we do similarly than Vaadin 7 does and extend AbstractField
class CheckBox(override val p: com.vaadin.ui.CheckBox with CheckBoxMixin = new com.vaadin.ui.CheckBox with CheckBoxMixin) extends AbstractField(p) {
  
  // Have to be Option[Boolean] because we override Option[Any]...
  override def value: Option[Boolean] = Option(p.booleanValue());
  def value_=(value: Boolean) = p.setValue(value)
  
  lazy val blurListeners = new BlurListeners(p)
  lazy val focusListeners = new FocusListeners(p)
}
