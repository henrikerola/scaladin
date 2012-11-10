package vaadin.scala

import scala.collection.mutable
import vaadin.scala.mixins.WindowMixin

package mixins {
  trait WindowMixin extends PanelMixin { self: com.vaadin.ui.Window => }
}

class WindowCloseListener(action: com.vaadin.ui.Window.CloseEvent => Unit) extends com.vaadin.ui.Window.CloseListener {
  def windowClose(event: com.vaadin.ui.Window.CloseEvent) { action(event) }
}

object Window {
  val BORDER_NONE: Int = 0

  val BORDER_MINIMAL: Int = 1

  val BORDER_DEFAULT: Int = 2
}

class Window(override val p: com.vaadin.ui.Window with WindowMixin = new com.vaadin.ui.Window with WindowMixin)
    extends Panel(p) with BlurNotifier with FocusNotifier {

  def positionX_=(positionX: Int) = p.setPositionX(positionX)
  def positionX: Int = p.getPositionX

  def positionY_=(positionY: Int) = p.setPositionY(positionY)
  def positionY: Int = p.getPositionY

  def resizable_=(resizable: Boolean) = p.setResizable(resizable)
  def resizable: Boolean = p.isResizable

  private var _closeShortcut: Option[KeyShortcut] = None

  def closeShortcut: Option[KeyShortcut] = _closeShortcut
  def closeShortcut_=(cs: Option[KeyShortcut]): Unit = {
    _closeShortcut = cs
    closeShortcut match {
      case None => p.removeCloseShortcut()
      case Some(closeShortcut) => p.setCloseShortcut(closeShortcut.keyCode.value, closeShortcut.modifiers.map(_.value): _*)
    }
  }
  def closeShortcut_=(cs: KeyShortcut): Unit = this.closeShortcut = Option(cs)

  override def parent: Option[Window] = wrapperFor[Window](p.getParent)

  def center() = p.center()

  def modal_=(modal: Boolean) = p.setModal(modal)
  def modal: Boolean = p.isModal

  def closable_=(closable: Boolean) = p.setClosable(closable)
  def closable: Boolean = p.isClosable

  def draggable_=(draggable: Boolean) = p.setDraggable(draggable)
  def draggable: Boolean = p.isDraggable
}