package vaadin.scala

import vaadin.scala.mixins.ButtonMixin
import vaadin.scala.internal.WrapperUtil
import vaadin.scala.internal.ButtonClickListener
import vaadin.scala.internal.ListenersTrait

package mixins {
  trait ButtonMixin extends AbstractComponentMixin { self: com.vaadin.ui.Button => }
}

object Button {

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

  case class ClickEvent(button: Button, clientX: Int, clientY: Int, relativeX: Int, relativeY: Int, altKey: Boolean, ctrlKey: Boolean, metaKey: Boolean, shiftKey: Boolean) extends Event
}

class Button(override val p: com.vaadin.ui.Button with ButtonMixin = new com.vaadin.ui.Button with ButtonMixin)
    extends AbstractComponent(p) with BlurNotifier with FocusNotifier with Focusable {

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
  def disableOnClick_=(disableOnClick: Boolean): Unit = p.setDisableOnClick(disableOnClick)

  def htmlContentAllowed: Boolean = p.isHtmlContentAllowed
  def htmlContentAllowed_=(htmlContentAllowed: Boolean): Unit = p.setHtmlContentAllowed(htmlContentAllowed)

  lazy val clickListeners = new ListenersTrait[Button.ClickEvent, ButtonClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Button.ClickEvent])
    override def addListener(elem: Button.ClickEvent => Unit) = p.addClickListener(new ButtonClickListener(elem))
    override def removeListener(elem: ButtonClickListener) = p.removeClickListener(elem)
  }
}

class LinkButton(override val p: com.vaadin.ui.Button with ButtonMixin = new com.vaadin.ui.Button with ButtonMixin) extends Button(p) {
  p.setStyleName(BaseTheme.ButtonLink)
}