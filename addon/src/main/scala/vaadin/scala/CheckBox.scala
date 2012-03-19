package vaadin.scala

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
