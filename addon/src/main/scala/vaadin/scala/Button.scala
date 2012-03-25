package vaadin.scala

import com.vaadin.ui.themes.BaseTheme

case class ButtonClickEvent(button: Button, clientX: Int, clientY: Int, relativeX: Int, relativeY: Int, altKey: Boolean, ctrlKey: Boolean, metaKey: Boolean, shiftKey: Boolean) extends Event

class ButtonClickListener(val action: ButtonClickEvent => Unit)(implicit wrapper: WrapperRegistry) extends com.vaadin.ui.Button.ClickListener with Listener {
  def buttonClick(e: com.vaadin.ui.Button#ClickEvent) = action(ButtonClickEvent(wrapper.get[Button](e.getButton()).get, e.getClientX(), e.getClientY(), e.getRelativeX(), e.getRelativeY(), e.isAltKey(), e.isCtrlKey(), e.isMetaKey(), e.isShiftKey()))
}

case class BlurEvent(component: Component) extends Event

class BlurListener(val action: BlurEvent => Unit)(implicit wrapper: WrapperRegistry) extends com.vaadin.event.FieldEvents.BlurListener with Listener {
  def blur(e: com.vaadin.event.FieldEvents.BlurEvent) = action(BlurEvent(wrapper.get[Button](e.getComponent()).get))
}

case class FocusEvent(component: Component) extends Event

class FocusListener(val action: FocusEvent => Unit)(implicit wrapper: WrapperRegistry) extends com.vaadin.event.FieldEvents.FocusListener with Listener {
  def focus(e: com.vaadin.event.FieldEvents.FocusEvent) = action(FocusEvent(wrapper.get[Button](e.getComponent()).get))
}

class Button(implicit wrapper: WrapperRegistry) extends AbstractField {

  override val p = new com.vaadin.ui.Button()
  wr.put(this)

  def this(caption: String = null, action: ButtonClickEvent => Unit = null, icon: Resource = null, style: String = null, enabled: Boolean = true)(implicit wrapper: WrapperRegistry) = {
    this()

    this.caption = caption
    this.icon = icon
    p.setStyleName(style)
    this.enabled = enabled

    if (action != null) clickListeners += action
  }

  def disableOnClick = p.isDisableOnClick()
  def disableOnClick_=(disableOnClick: Boolean) = p.setDisableOnClick(disableOnClick)

  def addListener(action: ButtonClickEvent => Unit): Unit = p.addListener(new ButtonClickListener(action))

  lazy val clickListeners = new ListenersTrait[ButtonClickEvent => Unit, ButtonClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Button#ClickEvent])
    override def addListener(elem: ButtonClickEvent => Unit) = p.addListener(new ButtonClickListener(elem))
    override def removeListener(elem: ButtonClickListener) = p.removeListener(elem)
  }

  lazy val blurListeners = new ListenersTrait[BlurEvent => Unit, BlurListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.FieldEvents.BlurEvent])
    override def addListener(elem: BlurEvent => Unit) = p.addListener(new BlurListener(elem))
    override def removeListener(elem: BlurListener) = p.removeListener(elem)
  }

  lazy val focusListeners = new ListenersTrait[FocusEvent => Unit, FocusListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.FieldEvents.FocusEvent])
    override def addListener(elem: FocusEvent => Unit) = p.addListener(new FocusListener(elem))
    override def removeListener(elem: FocusListener) = p.removeListener(elem)
  }
}

class LinkButton(caption: String = null, action: ButtonClickEvent => Unit = null, icon: Resource = null, style: String = null)(implicit wrapper: WrapperRegistry)
  extends Button(caption, action, icon) {
  p.setStyleName(BaseTheme.BUTTON_LINK)
  p.addStyleName(style)
}