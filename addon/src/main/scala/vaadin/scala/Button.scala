package vaadin.scala

import com.vaadin.ui.themes.BaseTheme
import vaadin.scala.listeners.ButtonClickListener
import vaadin.scala.listeners.BlurListeners
import vaadin.scala.listeners.FocusListeners

object Button {
  case class ClickEvent(button: Button, clientX: Int, clientY: Int, relativeX: Int, relativeY: Int, altKey: Boolean, ctrlKey: Boolean, metaKey: Boolean, shiftKey: Boolean) extends Event
}

package listeners {
  class ButtonClickListener(val action: Button.ClickEvent => Unit) extends com.vaadin.ui.Button.ClickListener with Listener {
    def buttonClick(e: com.vaadin.ui.Button#ClickEvent) = action(Button.ClickEvent(WrapperRegistry.get[Button](e.getButton()).get, e.getClientX(), e.getClientY(), e.getRelativeX(), e.getRelativeY(), e.isAltKey(), e.isCtrlKey(), e.isMetaKey(), e.isShiftKey()))
  }
}

class Button extends AbstractField {

  override val p = new com.vaadin.ui.Button()
  WrapperRegistry.put(this)

  def this(caption: String = null, action: Button.ClickEvent => Unit = null, icon: Resource = null, style: String = null, enabled: Boolean = true) = {
    this()

    this.caption = caption
    this.icon = icon
    p.setStyleName(style)
    this.enabled = enabled

    if (action != null) clickListeners += action
  }

  def disableOnClick = p.isDisableOnClick()
  def disableOnClick_=(disableOnClick: Boolean) = p.setDisableOnClick(disableOnClick)

  //def addListener(action: Button.ClickEvent => Unit): Unit = p.addListener(new ButtonClickListener(action))

  lazy val clickListeners = new ListenersTrait[Button.ClickEvent => Unit, ButtonClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Button#ClickEvent])
    override def addListener(elem: Button.ClickEvent => Unit) = p.addListener(new ButtonClickListener(elem))
    override def removeListener(elem: ButtonClickListener) = p.removeListener(elem)
  }

  lazy val blurListeners = new BlurListeners(p)
  lazy val focusListeners = new FocusListeners(p)
}

class LinkButton(caption: String = null, action: Button.ClickEvent => Unit = null, icon: Resource = null, style: String = null)
  extends Button(caption, action, icon) {
  p.setStyleName(BaseTheme.BUTTON_LINK)
  p.addStyleName(style)
}