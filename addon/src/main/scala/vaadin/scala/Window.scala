package vaadin.scala

import scala.collection.mutable
import vaadin.scala.mixins.WindowMixin
import vaadin.scala.internal.ListenersTrait
import vaadin.scala.internal.WindowCloseListener
import vaadin.scala.internal.WindowResizeListener

package mixins {
  trait WindowMixin extends PanelMixin
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

  def childWindows: mutable.Set[Window] = new mutable.Set[Window] {
    import scala.collection.JavaConversions.asScalaIterator

    def contains(key: Window) = {
      p.getChildWindows.contains(key.p)
    }
    def iterator: Iterator[Window] = {
      p.getChildWindows.iterator.map(wrapperFor[Window](_).get)
    }
    def +=(elem: Window) = { p.addWindow(elem.p); this }
    def -=(elem: Window) = { p.removeWindow(elem.p); this }
  }

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

  // TODO: return wrapped Terminal
  def terminal = Option(p.getTerminal)

  override def parent: Option[Window] = wrapperFor[Window](p.getParent)

  def theme: Option[String] = Option(p.getTheme)
  def theme_=(theme: Option[String]): Unit = p.setTheme(theme.orNull)
  def theme_=(theme: String): Unit = p.setTheme(theme)

  def center(): Unit = p.center()

  def modal_=(modal: Boolean): Unit = p.setModal(modal)
  def modal: Boolean = p.isModal

  def closable_=(closable: Boolean): Unit = p.setClosable(closable)
  def closable: Boolean = p.isClosable

  def draggable_=(draggable: Boolean) = p.setDraggable(draggable)
  def draggable: Boolean = p.isDraggable

  def name: Option[String] = Option(p.getName)
  def name_=(name: Option[String]): Unit = p.setName(name.orNull)
  def name_=(name: String): Unit = p.setName(name)

  def scrollIntoView(component: Component): Unit = p.scrollIntoView(component.p)

  def browserWindowHeight: Int = p.getBrowserWindowHeight
  def browserWindowWidth: Int = p.getBrowserWindowWidth

  def executeJavaScript(javaScript: String): Unit = p.executeJavaScript(javaScript)

  def showNotification(caption: String): Unit = p.showNotification(caption)
  def showNotification(caption: String, notificationType: Notification.Type.Value): Unit = p.showNotification(caption, notificationType.id)
  def showNotification(caption: String, description: String): Unit = p.showNotification(caption, description)
  def showNotification(caption: String, description: String, notificationType: Notification.Type.Value): Unit = p.showNotification(caption, description, notificationType.id)
  def showNotification(caption: String, description: String, notificationType: Notification.Type.Value, htmlContentAllowed: Boolean): Unit = p.showNotification(caption, description, notificationType.id, htmlContentAllowed)
  def showNotification(notification: Notification): Unit = p.showNotification(notification.p)

  def open(resource: Resource, windowName: String = null, width: Int = -1, height: Int = -1, border: Int = Window.BORDER_DEFAULT): Unit = p.open(resource.p, windowName, width, height, border)
  
  lazy val closeListeners = new ListenersTrait[Window.CloseEvent, WindowCloseListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Window.CloseListener])
    override def addListener(elem: Window.CloseEvent => Unit) = p.addListener(new WindowCloseListener(elem))
    override def removeListener(elem: WindowCloseListener) = p.removeListener(elem)
  }
  
  lazy val resizeListeners = new ListenersTrait[Window.ResizeEvent, WindowResizeListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Window.CloseListener])
    override def addListener(elem: Window.ResizeEvent => Unit) = p.addListener(new WindowResizeListener(elem))
    override def removeListener(elem: WindowResizeListener) = p.removeListener(elem)
  }
}