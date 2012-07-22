package vaadin.scala

import com.vaadin.ui.themes.BaseTheme
import vaadin.scala.mixins.ButtonMixin
import vaadin.scala.internal.WrapperUtil
import vaadin.scala.listeners.ButtonClickListener

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

class Button(override val p: com.vaadin.ui.Button with ButtonMixin = new com.vaadin.ui.Button with ButtonMixin)
    extends AbstractField(p) with BlurNotifier with FocusNotifier {

  private var _clickShortcut: Option[KeyShortcut] = None

  def clickShortcut: Option[KeyShortcut] = _clickShortcut
  def clickShortcut_=(clickShortcut: Option[KeyShortcut]): Unit = {
    _clickShortcut = clickShortcut
    clickShortcut match {
      case None => p.removeClickShortcut
      case Some(clickShortcut) => p.setClickShortcut(clickShortcut.keyCode.value, clickShortcut.modifiers.map(_.value): _*)
    }
  }
  def clickShortcut_=(clickShortcut: KeyShortcut): Unit = this.clickShortcut = Option(clickShortcut)

  def disableOnClick: Boolean = p.isDisableOnClick
  def disableOnClick_=(disableOnClick: Boolean) = p.setDisableOnClick(disableOnClick)

  def htmlContentAllowed: Boolean = p.isHtmlContentAllowed
  def htmlContentAllowed_=(htmlContentAllowed: Boolean) = p.setHtmlContentAllowed(htmlContentAllowed)

  lazy val clickListeners = new ListenersTrait[Button.ClickEvent, ButtonClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Button#ClickEvent])
    override def addListener(elem: Button.ClickEvent => Unit) = p.addListener(new ButtonClickListener(elem))
    override def removeListener(elem: ButtonClickListener) = p.removeListener(elem)
  }
}

class LinkButton(override val p: com.vaadin.ui.Button with ButtonMixin = new com.vaadin.ui.Button with ButtonMixin) extends Button(p) {
  p.setStyleName(BaseTheme.BUTTON_LINK)
}