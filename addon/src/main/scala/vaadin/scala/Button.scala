package vaadin.scala

import com.vaadin.ui.themes.BaseTheme
import vaadin.scala.mixins.ButtonMixin
import vaadin.scala.internal.WrapperUtil
import vaadin.scala.listeners.ButtonClickListener
import vaadin.scala.listeners.BlurListeners
import vaadin.scala.listeners.FocusListeners

package mixins {
  trait ButtonMixin extends AbstractFieldMixin
}

object Button {

  def apply(caption: String, clickListener: => Unit): Button = {
    val button = new Button
    button.caption = caption
    button.clickListeners += clickListener
    button
  }

  def apply(caption: String, clickListener: Button.ClickEvent => Unit): Button = {
    val button = new Button
    button.caption = caption
    button.clickListeners += clickListener
    button
  }

  case class ClickEvent(button: Button, clientX: Int, clientY: Int, relativeX: Int, relativeY: Int, altKey: Boolean, ctrlKey: Boolean, metaKey: Boolean, shiftKey: Boolean) extends Event
}

package listeners {
  class ButtonClickListener(val action: Button.ClickEvent => Unit) extends com.vaadin.ui.Button.ClickListener with Listener {
    def buttonClick(e: com.vaadin.ui.Button#ClickEvent) = action(Button.ClickEvent(wrapperFor[Button](e.getButton()).get, e.getClientX(), e.getClientY(), e.getRelativeX(), e.getRelativeY(), e.isAltKey(), e.isCtrlKey(), e.isMetaKey(), e.isShiftKey()))
  }
}

class Button(override val p: com.vaadin.ui.Button with ButtonMixin = new com.vaadin.ui.Button with ButtonMixin) extends AbstractField(p) {

  /*-
  def this(caption: String = null, action: ButtonClickEvent => Unit = null, icon: Resource = null, style: String = null, enabled: Boolean = true) = {
    this(new com.vaadin.ui.Button)

    this.caption = caption
    this.icon = icon
    p.setStyleName(style)
    this.enabled = enabled

    if (action != null) clickListeners += action
  }*/

  def disableOnClick = p.isDisableOnClick
  def disableOnClick_=(disableOnClick: Boolean) = p.setDisableOnClick(disableOnClick)

  //def addListener(action: Button.ClickEvent => Unit): Unit = p.addListener(new ButtonClickListener(action))

  lazy val clickListeners = new ListenersTrait[Button.ClickEvent, ButtonClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Button#ClickEvent])
    override def addListener(elem: Button.ClickEvent => Unit) = p.addListener(new ButtonClickListener(elem))
    override def removeListener(elem: ButtonClickListener) = p.removeListener(elem)
  }

  lazy val blurListeners = new BlurListeners(p)
  lazy val focusListeners = new FocusListeners(p)
}

class LinkButton(override val p: com.vaadin.ui.Button with ButtonMixin = new com.vaadin.ui.Button with ButtonMixin) extends Button(p) {
  p.setStyleName(BaseTheme.BUTTON_LINK)
}