package vaadin.scala

import scala.collection.mutable

class WindowCloseListener(action: com.vaadin.ui.Window#CloseEvent => Unit) extends com.vaadin.ui.Window.CloseListener {
  def windowClose(event: com.vaadin.ui.Window#CloseEvent) { action(event) }
}

class Window extends AbstractComponentContainer {
  override val p = new com.vaadin.ui.Window

  def positionX_=(positionX: Int) = p.setPositionX(positionX)
  def positionX = p.getPositionX

  def positionY_=(positionY: Int) = p.setPositionY(positionY)
  def positionY = p.getPositionY

  def resizable_=(resizable: Boolean) = p.setResizable(resizable)
  def resizable = p.isResizable

  def windows: mutable.Set[Window] = new mutable.Set[Window] {
    import scala.collection.JavaConversions.asScalaIterator

    def contains(key: Window) = {
      p.getChildWindows.contains(key)
    }
    def iterator: Iterator[Window] = {
      p.getChildWindows.iterator.map(WrapperRegistry.get[Window](_).get)
    }
    def +=(elem: Window) = { p.addWindow(elem.p); this }
    def -=(elem: Window) = { p.removeWindow(elem.p); this }
  }
}