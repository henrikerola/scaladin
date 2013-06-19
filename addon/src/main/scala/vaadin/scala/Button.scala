package vaadin.scala

import event.{ FocusNotifier, BlurNotifier, ComponentEvent, AbstractClickEvent }
import vaadin.scala.mixins.ButtonMixin
import vaadin.scala.internal.WrapperUtil
import vaadin.scala.internal.ButtonClickListener
import vaadin.scala.internal.ListenersTrait

package mixins {
  trait ButtonMixin extends AbstractComponentMixin { self: com.vaadin.ui.Button => }
}

object Button {

  case class ClickEvent(
    button: Button,
    clientX: Int,
    clientY: Int,
    relativeX: Int,
    relativeY: Int,
    altKey: Boolean,
    ctrlKey: Boolean,
    metaKey: Boolean,
    shiftKey: Boolean) extends ComponentEvent(button)

  def apply(caption: String): Button = {
    val button = new Button
    button.caption = caption
    button
  }

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
}

class Button(override val p: com.vaadin.ui.Button with ButtonMixin = new com.vaadin.ui.Button with ButtonMixin)
    extends AbstractComponent(p) with BlurNotifier with FocusNotifier with Component.Focusable {

  private var _clickShortcut: Option[KeyShortcut] = None

  def clickShortcut: Option[KeyShortcut] = _clickShortcut
  def clickShortcut_=(clickShortcut: Option[KeyShortcut]) {
    _clickShortcut = clickShortcut
    clickShortcut match {
      case None => p.removeClickShortcut
      case Some(shortcut) => p.setClickShortcut(shortcut.keyCode.value, shortcut.modifiers.map(_.value): _*)
    }
  }
  def clickShortcut_=(clickShortcut: KeyShortcut) { this.clickShortcut = Option(clickShortcut) }

  def disableOnClick: Boolean = p.isDisableOnClick
  def disableOnClick_=(disableOnClick: Boolean) { p.setDisableOnClick(disableOnClick) }

  def htmlContentAllowed: Boolean = p.isHtmlContentAllowed
  def htmlContentAllowed_=(htmlContentAllowed: Boolean) { p.setHtmlContentAllowed(htmlContentAllowed) }

  lazy val clickListeners: ListenersSet[Button.ClickEvent => Unit] =
    new ListenersTrait[Button.ClickEvent, ButtonClickListener] {
      override def listeners = p.getListeners(classOf[com.vaadin.ui.Button.ClickEvent])
      override def addListener(elem: Button.ClickEvent => Unit) = p.addClickListener(new ButtonClickListener(elem))
      override def removeListener(elem: ButtonClickListener) = p.removeClickListener(elem)
    }
}

class LinkButton(override val p: com.vaadin.ui.Button with ButtonMixin = new com.vaadin.ui.Button with ButtonMixin)
    extends Button(p) {
  p.setStyleName(BaseTheme.ButtonLink)
}