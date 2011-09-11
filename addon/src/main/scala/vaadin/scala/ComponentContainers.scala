package vaadin.scala

import com.vaadin.terminal.Resource
import com.vaadin.ui.ComponentContainer
import com.vaadin.ui.Component
import scala.collection.JavaConverters._

class WindowCloseListener(action: com.vaadin.ui.Window#CloseEvent => Unit) extends com.vaadin.ui.Window.CloseListener {
  def windowClose(event: com.vaadin.ui.Window#CloseEvent) { action(event) }
}

class Window(caption: String = null, width: String = null, height: String = null, content: ComponentContainer = null, modal: Boolean = false, icon: Resource = null, style: String = null) extends com.vaadin.ui.Window(caption, content) {
  setWidth(width)
  setHeight(height)
  setModal(modal)
  setIcon(icon)
  setStyleName(style)

  def getComponents(): TraversableOnce[Component] = getComponentIterator.asScala.toSeq
}

class Panel(caption: String = null, width: String = null, height: String = null, content: ComponentContainer = null, icon: Resource = null, style: String = null) extends com.vaadin.ui.Panel(caption, content) {
  setWidth(width)
  setHeight(height)
  setIcon(icon)
  setStyleName(style)

  def getComponents(): TraversableOnce[Component] = getComponentIterator.asScala.toSeq
}

trait FilterableComponentContainer extends ComponentContainer {
  def \(filter: Component => Boolean): List[Component] = {
    var newList = List[Component]()
    val iterator = getComponentIterator
    while (iterator.hasNext) {
      val component = iterator.next()
      if (filter(component))
        newList = component :: newList
    }

    newList
  }

  def \\(filter: Component => Boolean): List[Component] = {
    var newList = List[Component]()
    val iterator = getComponentIterator
    while (iterator.hasNext) {
      val component = iterator.next()
      if (filter(component))
        newList = component :: newList

      if (component.isInstanceOf[ComponentContainer]) {
        newList = component.asInstanceOf[FilterableComponentContainer] \\ (filter) ::: newList
      }
    }

    newList
  }
}

class SelectedTabChangeListener(action: com.vaadin.ui.TabSheet#SelectedTabChangeEvent => Unit) extends com.vaadin.ui.TabSheet.SelectedTabChangeListener {
  def selectedTabChange(event: com.vaadin.ui.TabSheet#SelectedTabChangeEvent) = action(event)
}

class TabSheet(caption: String = null, width: String = 100 percent, height: String = null, style: String = null)
    extends com.vaadin.ui.TabSheet() {
  setCaption(caption)
  setWidth(width);
  setHeight(height);
  setStyleName(style)

  def addListener(action: com.vaadin.ui.TabSheet#SelectedTabChangeEvent => Unit): Unit = addListener(new SelectedTabChangeListener(action))

  def getComponents(): TraversableOnce[Component] = getComponentIterator.asScala.toSeq
}