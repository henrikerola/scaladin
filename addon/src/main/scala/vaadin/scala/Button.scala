package vaadin.scala

import com.vaadin.ui.themes.BaseTheme
import scala.collection.mutable
import vaadin.scala.event._

package object event {
  // TODO: better name for this
  type ButtonClickEventListener = (ButtonClickEvent => Unit)
}

case class ButtonClickEvent(button: Button, clientX: Int, clientY: Int, relativeX: Int, relativeY: Int, altKey: Boolean, ctrlKey: Boolean, metaKey: Boolean, shiftKey: Boolean) extends Event

class ButtonClickListener(val action: ButtonClickEventListener) extends com.vaadin.ui.Button.ClickListener {
  def buttonClick(e: com.vaadin.ui.Button#ClickEvent) = action(ButtonClickEvent(WrapperRegistry.get[Button](e.getButton()).get, e.getClientX(), e.getClientY(), e.getRelativeX(), e.getRelativeY(), e.isAltKey(), e.isCtrlKey(), e.isMetaKey(), e.isShiftKey()))
}

class Button(caption: String = null, action: ButtonClickEventListener = null, icon: Resource = null, style: String = null, enabled: Boolean = true)
  extends AbstractField {
  
  override val p = new com.vaadin.ui.Button(caption)
  WrapperRegistry.put(this)
  
  if (icon == null) p.setIcon(null) else p.setIcon(icon.p)
  p.setStyleName(style)
  p.setEnabled(enabled)

  if (action != null) addListener(action)

  def disableOnClick = p.isDisableOnClick()
  def disableOnClick_=(disableOnClick: Boolean) = p.setDisableOnClick(disableOnClick)

  def addListener(action: ButtonClickEventListener): Unit = p.addListener(new ButtonClickListener(action))

  val clickListeners: mutable.Set[ButtonClickEventListener] = new mutable.Set[ButtonClickEventListener] {
    import scala.collection.JavaConversions._
    def contains(key: ButtonClickEventListener) = {
      iterator.contains(key)
    }
    def iterator: Iterator[ButtonClickEventListener] = {
      val list = p.getListeners(classOf[com.vaadin.ui.Button#ClickEvent]).map(_.asInstanceOf[ButtonClickListener].action)
      list.iterator
    }
    def +=(elem: ButtonClickEventListener) = { p.addListener(new ButtonClickListener(elem)); this }
    def -=(elem: ButtonClickEventListener) = {
      val list = p.getListeners(classOf[com.vaadin.ui.Button#ClickEvent]).foreach { e =>
        if (e.asInstanceOf[ButtonClickListener].action == elem) {
          p.removeListener(e.asInstanceOf[ButtonClickListener])
          this
        }
      }
      this
    }
  }
}

class LinkButton(caption: String = null, action: ButtonClickEventListener = null, icon: Resource = null, style: String = null)
  extends Button(caption, action, icon) {
  p.setStyleName(BaseTheme.BUTTON_LINK)
  p.addStyleName(style)
}