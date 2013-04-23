package vaadin.scala

import vaadin.scala.event.{ ClickEvent, FocusNotifier, BlurNotifier, Event }
import scala.collection.mutable
import vaadin.scala.mixins.WindowMixin
import vaadin.scala.internal.ListenersTrait
import vaadin.scala.internal.WindowCloseListener
import vaadin.scala.internal.WindowResizeListener

package mixins {
  trait WindowMixin extends PanelMixin { self: com.vaadin.ui.Window => }
}

object Window {
  val BORDER_NONE: Int = 0

  val BORDER_MINIMAL: Int = 1

  val BORDER_DEFAULT: Int = 2

  case class CloseEvent(window: Window) extends Event
  case class ResizeEvent(window: Window) extends Event
}

class Window(override val p: com.vaadin.ui.Window with WindowMixin = new com.vaadin.ui.Window with WindowMixin)
    extends Panel(p) with BlurNotifier with FocusNotifier {

  def positionX_=(positionX: Int): Unit = p.setPositionX(positionX)
  def positionX: Int = p.getPositionX

  def positionY_=(positionY: Int): Unit = p.setPositionY(positionY)
  def positionY: Int = p.getPositionY

  def resizable_=(resizable: Boolean): Unit = p.setResizable(resizable)
  def resizable: Boolean = p.isResizable

  private var _closeShortcut: Option[KeyShortcut] = None

  def closeShortcut: Option[KeyShortcut] = _closeShortcut
  def closeShortcut_=(cs: Option[KeyShortcut]): Unit = {
    _closeShortcut = cs
    closeShortcut match {
      case None => p.removeCloseShortcut()
      case Some(closeShortcut) =>
        p.setCloseShortcut(closeShortcut.keyCode.value, closeShortcut.modifiers.map(_.value): _*)
    }
  }
  def closeShortcut_=(cs: KeyShortcut): Unit = this.closeShortcut = Option(cs)

  override def parent: Option[Window] = wrapperFor[Window](p.getParent)

  def center(): Unit = p.center()

  def modal_=(modal: Boolean): Unit = p.setModal(modal)
  def modal: Boolean = p.isModal

  def closable_=(closable: Boolean): Unit = p.setClosable(closable)
  def closable: Boolean = p.isClosable

  def draggable_=(draggable: Boolean) = p.setDraggable(draggable)
  def draggable: Boolean = p.isDraggable

  lazy val closeListeners: ListenersSet[Window.CloseEvent => Unit] =
    new ListenersTrait[Window.CloseEvent, WindowCloseListener] {
      override def listeners = p.getListeners(classOf[com.vaadin.ui.Window.CloseListener])
      override def addListener(elem: Window.CloseEvent => Unit) = p.addCloseListener(new WindowCloseListener(elem))
      override def removeListener(elem: WindowCloseListener) = p.removeCloseListener(elem)
    }

  lazy val resizeListeners: ListenersSet[Window.ResizeEvent => Unit] =
    new ListenersTrait[Window.ResizeEvent, WindowResizeListener] {
      override def listeners = p.getListeners(classOf[com.vaadin.ui.Window.CloseListener])
      override def addListener(elem: Window.ResizeEvent => Unit) = p.addResizeListener(new WindowResizeListener(elem))
      override def removeListener(elem: WindowResizeListener) = p.removeResizeListener(elem)
    }
}