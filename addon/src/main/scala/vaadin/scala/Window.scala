package vaadin.scala

import scala.collection.mutable
import vaadin.scala.mixins.WindowMixin

package mixins {
  trait WindowMixin extends PanelMixin
}

class WindowCloseListener(action: com.vaadin.ui.Window#CloseEvent => Unit) extends com.vaadin.ui.Window.CloseListener {
  def windowClose(event: com.vaadin.ui.Window#CloseEvent) { action(event) }
}

class Window(override val p: com.vaadin.ui.Window with WindowMixin = new com.vaadin.ui.Window with WindowMixin)
  extends Panel(p) with BlurNotifier with FocusNotifier {

  def positionX_=(positionX: Int) = p.setPositionX(positionX)
  def positionX: Int = p.getPositionX

  def positionY_=(positionY: Int) = p.setPositionY(positionY)
  def positionY: Int = p.getPositionY

  def resizable_=(resizable: Boolean) = p.setResizable(resizable)
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

  //not a property
  def setCloseShortcut(keyCode: Int, modifiers: Int*): Unit = { p.setCloseShortcut(keyCode, modifiers.map(_.asInstanceOf[Int]): _*) }
  
  // TODO: return wrapped Terminal
  def terminal = Option(p.getTerminal)
  
  override def parent: Option[Window] = wrapperFor[Window](p.getParent)
  
  def theme: Option[String] = Option(p.getTheme)
  def theme_=(theme: Option[String]) = p.setTheme(theme.getOrElse(null))
  def theme_=(theme: String) = p.setTheme(theme)

  def center() = p.center()

  def modal_=(modal: Boolean) = p.setModal(modal)
  def modal: Boolean = p.isModal

  def closable_=(closable: Boolean) = p.setClosable(modal)
  def closable: Boolean = p.isClosable

  def draggable_=(draggable: Boolean) = p.setDraggable(draggable)
  def draggable: Boolean = p.isDraggable
  
  def name: Option[String] = Option(p.getName)
  def name_=(name: Option[String]) = p.setName(name.getOrElse(null))
  def name_=(name: String) = p.setName(name)
  
  def scrollIntoView(component: Component) = p.scrollIntoView(component.p)
  
  def browserWindowHeight: Int = p.getBrowserWindowHeight
  def browserWindowWidth: Int = p.getBrowserWindowWidth
  
  def executeJavaScript(javaScript: String) = p.executeJavaScript(javaScript)

  def showNotification(caption: String) = p.showNotification(caption)
  def showNotification(caption: String, notificationType: Notification.Type.Value) = p.showNotification(caption, notificationType.id)
  def showNotification(caption: String, description: String) = p.showNotification(caption, description)
  def showNotification(caption: String, description: String, notificationType: Notification.Type.Value) = p.showNotification(caption, description, notificationType.id)
  def showNotification(caption: String, description: String, notificationType: Notification.Type.Value, htmlContentAllowed: Boolean) = p.showNotification(caption, description, notificationType.id, htmlContentAllowed)
  def showNotification(notification: Notification) = p.showNotification(notification.p)
}