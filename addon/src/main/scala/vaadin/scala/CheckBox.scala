package vaadin.scala

import vaadin.scala.listeners.BlurListeners
import vaadin.scala.listeners.FocusListeners

// In Vaadin 6 CheckBox extends Button, but here we do similarly than Vaadin 7 does and extend AbstractField
class CheckBox extends AbstractField {

  override val p = new com.vaadin.ui.CheckBox

  def this(caption: String = null, checked: Boolean = false, immediate: Boolean = false, action: com.vaadin.ui.Button#ClickEvent => Unit = null, icon: Resource = null, style: String = null, enabled: Boolean = true, description: String = null) = {
    this()

    this.caption = caption;
    this.value = checked;
    p.setImmediate(immediate)
    this.icon = icon;
    p.setStyleName(style)
    this.enabled = enabled;
    this.description = description
    
    // FIXME
  //if (action != null) addListener(action)

  // FIXME
  //def addListener(action: com.vaadin.ui.Button#ClickEvent => Unit): Unit = addListener(new ButtonClickListener(action))
  }
  
  // Have to be Option[Boolean] because we override Option[Any]...
  override def value: Option[Boolean] = Option(p.booleanValue());
  def value_=(value: Boolean) = p.setValue(value)
  
  lazy val blurListeners = new BlurListeners(p)
  lazy val focusListeners = new FocusListeners(p)
}
