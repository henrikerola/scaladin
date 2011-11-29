package vaadin.scala

import com.vaadin.terminal.Resource
import com.vaadin.ui.ComponentContainer
import com.vaadin.ui.Component
import scala.collection.JavaConverters._

trait FilterableComponentContainer extends ComponentContainer {

  def filter(filterFunction: Component => Boolean): List[Component] = getComponentIterator.asScala.filter(filterFunction).toList

  def filterRecursive(filterFunction: Component => Boolean): List[Component] = {
    var newList: List[Component] = Nil
    for (component <- getComponentIterator.asScala) {
      if (filterFunction(component))
        newList = component :: newList
      if (component.isInstanceOf[ComponentContainer]) {
        newList = component.asInstanceOf[FilterableComponentContainer].filterRecursive(filterFunction) ::: newList
      }
    }
    newList
  }
}

class WindowCloseListener(action: com.vaadin.ui.Window#CloseEvent => Unit) extends com.vaadin.ui.Window.CloseListener {
  def windowClose(event: com.vaadin.ui.Window#CloseEvent) { action(event) }
}

class Window(caption: String = null, width: String = null, height: String = null, content: ComponentContainer = null, modal: Boolean = false, icon: Resource = null, style: String = null, resizable: Boolean = true, draggable: Boolean = true, closable: Boolean = true)
  extends com.vaadin.ui.Window(caption, content) {
  setWidth(width)
  setHeight(height)
  setModal(modal)
  setIcon(icon)
  setStyleName(style)
  setResizable(resizable)
  setClosable(closable)
  setDraggable(draggable)

  def add[C <: com.vaadin.ui.Component](component: C = null): C = {
    addComponent(component)
    component
  }

  def getComponents(): TraversableOnce[Component] = getComponentIterator.asScala.toSeq
}

class SelectedTabChangeListener(action: com.vaadin.ui.TabSheet#SelectedTabChangeEvent => Unit) extends com.vaadin.ui.TabSheet.SelectedTabChangeListener {
  def selectedTabChange(event: com.vaadin.ui.TabSheet#SelectedTabChangeEvent) = action(event)
}

class TabSheet(width: String = 100 percent, height: String = null, caption: String = null, style: String = null)
  extends com.vaadin.ui.TabSheet() {
  setCaption(caption)
  setWidth(width)
  setHeight(height)
  setStyleName(style)

  def addListener(action: com.vaadin.ui.TabSheet#SelectedTabChangeEvent => Unit): Unit = addListener(new SelectedTabChangeListener(action))

  def getComponents(): TraversableOnce[Component] = getComponentIterator.asScala.toSeq
}

class Panel(caption: String = null, width: String = 100 percent, height: String = null, style: String = null)
  extends com.vaadin.ui.Panel() {
  setCaption(caption)
  setWidth(width)
  setHeight(height)
  setStyleName(style)

  def getComponents(): TraversableOnce[Component] = getComponentIterator.asScala.toSeq

  def add[C <: com.vaadin.ui.Component](component: C = null): C = {
    addComponent(component)
    component
  }
}

class HorizontalSplitPanel(width: String = 100 percent, height: String = 100 percent, caption: String = null, style: String = null)
  extends com.vaadin.ui.HorizontalSplitPanel() {
  setWidth(width)
  setHeight(height)
  setCaption(caption)
  setStyleName(style)

  def add[C <: com.vaadin.ui.Component](component: C = null): C = {
    addComponent(component)
    component
  }

  def getComponents(): TraversableOnce[Component] = getComponentIterator.asScala.toSeq
}

class VerticalSplitPanel(width: String = 100 percent, height: String = 100 percent, caption: String = null, style: String = null)
  extends com.vaadin.ui.VerticalSplitPanel() {
  setWidth(width)
  setHeight(height)
  setCaption(caption)
  setStyleName(style)

  def add[C <: com.vaadin.ui.Component](component: C = null): C = {
    addComponent(component)
    component
  }

  def getComponents(): TraversableOnce[Component] = getComponentIterator.asScala.toSeq
}