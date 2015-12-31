package vaadin.scala

import com.vaadin.shared.ui.window.WindowMode
import vaadin.scala.event.{ FocusNotifier, BlurNotifier, Event }
import vaadin.scala.mixins.WindowMixin
import vaadin.scala.internal.{ WindowModeChangeListener, ListenersTrait, WindowCloseListener, WindowResizeListener }

package mixins {
  trait WindowMixin extends PanelMixin { self: com.vaadin.ui.Window => }
}

object Window {
  val BORDER_NONE: Int = 0

  val BORDER_MINIMAL: Int = 1

  val BORDER_DEFAULT: Int = 2

  object WindowMode extends Enumeration {
    import com.vaadin.shared.ui.window.WindowMode._
    val Normal = Value(NORMAL.ordinal())
    val Maximized = Value(MAXIMIZED.ordinal())
  }

  case class CloseEvent(window: Window) extends Event
  case class ResizeEvent(window: Window) extends Event
  case class WindowModeChangeEvent(window: Window, windowMode: WindowMode.Value) extends Event
}

class Window(override val p: com.vaadin.ui.Window with WindowMixin = new com.vaadin.ui.Window with WindowMixin)
    extends Panel(p) with BlurNotifier with FocusNotifier {

  def positionX_=(positionX: Int): Unit = p.setPositionX(positionX)
  def positionX: Int = p.getPositionX

  def positionY_=(positionY: Int): Unit = p.setPositionY(positionY)
  def positionY: Int = p.getPositionY

  def position: (Int, Int) = (positionX, positionY)
  def position_=(position: (Int, Int)): Unit = p.setPosition(position._1, position._2)

  def resizable_=(resizable: Boolean): Unit = p.setResizable(resizable)
  def resizable: Boolean = p.isResizable

  def resizeLazy: Boolean = p.isResizeLazy
  def resizeLazy_=(resizeLazy: Boolean): Unit = p.setResizeLazy(resizeLazy)

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

  def bringToFront(): Unit = p.bringToFront()

  def modal_=(modal: Boolean): Unit = p.setModal(modal)
  def modal: Boolean = p.isModal

  def closable_=(closable: Boolean): Unit = p.setClosable(closable)
  def closable: Boolean = p.isClosable

  def draggable_=(draggable: Boolean) = p.setDraggable(draggable)
  def draggable: Boolean = p.isDraggable

  def windowMode: Window.WindowMode.Value = Window.WindowMode(p.getWindowMode.ordinal)
  def windowMode_=(mode: Window.WindowMode.Value): Unit =
    p.setWindowMode(WindowMode.values.apply(mode.id))

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

  lazy val windowModeChangeListeners: ListenersSet[Window.WindowModeChangeEvent => Unit] =
    new ListenersTrait[Window.WindowModeChangeEvent, WindowModeChangeListener] {
      override def listeners = p.getListeners(classOf[com.vaadin.ui.Window.WindowModeChangeListener])
      override def addListener(elem: Window.WindowModeChangeEvent => Unit) = p.addWindowModeChangeListener(new WindowModeChangeListener(elem))
      override def removeListener(elem: WindowModeChangeListener) = p.removeWindowModeChangeListener(elem)
    }
}