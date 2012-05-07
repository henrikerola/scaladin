package vaadin.scala

import vaadin.scala.mixins.CheckBoxMixin

package mixins {
  trait CheckBoxMixin extends AbstractFieldMixin
}

// In Vaadin 6 CheckBox extends Button, but here we do similarly than Vaadin 7 does and extend AbstractField
class CheckBox(override val p: com.vaadin.ui.CheckBox with CheckBoxMixin = new com.vaadin.ui.CheckBox with CheckBoxMixin) extends AbstractField(p) {
  
  // Have to be Option[Boolean] because we override Option[Any]...
  override def value: Option[Boolean] = Option(p.booleanValue());
  def value_=(value: Boolean) = p.setValue(value)
  
  def blurListeners = new ListenersTrait[BlurEvent => Unit, BlurListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.FieldEvents.BlurEvent])
    override def addListener(elem: BlurEvent => Unit) = p.addListener(new BlurListener(elem))
    override def removeListener(elem: BlurListener) = p.removeListener(elem)
  }

  def focusListeners = new ListenersTrait[FocusEvent => Unit, FocusListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.FieldEvents.FocusEvent])
    override def addListener(elem: FocusEvent => Unit) = p.addListener(new FocusListener(elem))
    override def removeListener(elem: FocusListener) = p.removeListener(elem)
  }
}
