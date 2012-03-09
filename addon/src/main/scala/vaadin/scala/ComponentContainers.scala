package vaadin.scala

import com.vaadin.ui.{ ComponentContainer => VaadinComponentContainer }
import com.vaadin.ui.{ Component => VaadinComponent }
import scala.collection.JavaConverters._

trait FilterableComponentContainer[C <: ComponentContainer] extends ComponentContainer {

  def filter(filterFunction: VaadinComponent => Boolean): List[VaadinComponent] = p.getComponentIterator.asScala.filter(filterFunction).toList

  def filterRecursive(filterFunction: VaadinComponent => Boolean): List[VaadinComponent] = {
    var newList: List[VaadinComponent] = Nil
    for (component <- p.getComponentIterator.asScala) {
      if (filterFunction(component))
        newList = component :: newList
      if (component.isInstanceOf[VaadinComponentContainer]) {
        newList = component.asInstanceOf[FilterableComponentContainer[_]].filterRecursive(filterFunction) ::: newList
      }
    }
    newList
  }
}

class WindowCloseListener(action: com.vaadin.ui.Window#CloseEvent => Unit) extends com.vaadin.ui.Window.CloseListener {
  def windowClose(event: com.vaadin.ui.Window#CloseEvent) { action(event) }
}

class Window(caption: String = null, width: String = null, height: String = null, content: VaadinComponentContainer = null, modal: Boolean = false, icon: Resource = null, style: String = null, resizable: Boolean = true, draggable: Boolean = true, closable: Boolean = true)
  extends com.vaadin.ui.Window(caption, content) {
  setWidth(width)
  setHeight(height)
  setModal(modal)
  if (icon == null) setIcon(null) else setIcon(icon.p)
  setStyleName(style)
  setResizable(resizable)
  setClosable(closable)
  setDraggable(draggable)

  def add[C <: VaadinComponent](component: C = null): C = {
    addComponent(component)
    component
  }

  def getComponents(): TraversableOnce[VaadinComponent] = getComponentIterator.asScala.toSeq
}

class SelectedTabChangeListener(action: com.vaadin.ui.TabSheet#SelectedTabChangeEvent => Unit) extends com.vaadin.ui.TabSheet.SelectedTabChangeListener {
  def selectedTabChange(event: com.vaadin.ui.TabSheet#SelectedTabChangeEvent) = action(event)
}

class TabSheet(width: String = 100 percent, height: String = null, caption: String = null, style: String = null, size: Tuple2[String, String] = null)
  extends com.vaadin.ui.TabSheet() {
  setCaption(caption)
  if (size != null) {
    setWidth(size._1)
    setHeight(size._2)
  } else {
    setWidth(width)
    setHeight(height)
  }
  setStyleName(style)

  def addListener(action: com.vaadin.ui.TabSheet#SelectedTabChangeEvent => Unit): Unit = addListener(new SelectedTabChangeListener(action))

  def getComponents(): TraversableOnce[VaadinComponent] = getComponentIterator.asScala.toSeq
}

class Accordion(width: String = 100 percent, height: String = null, caption: String = null, style: String = null)
  extends com.vaadin.ui.Accordion() {
  setCaption(caption)
  setWidth(width)
  setHeight(height)
  setStyleName(style)

  def addListener(action: com.vaadin.ui.TabSheet#SelectedTabChangeEvent => Unit): Unit = addListener(new SelectedTabChangeListener(action))

  def getComponents(): TraversableOnce[VaadinComponent] = getComponentIterator.asScala.toSeq

}

class Panel(caption: String = null, width: String = 100 percent, height: String = null, style: String = null)
  extends com.vaadin.ui.Panel() {
  setCaption(caption)
  setWidth(width)
  setHeight(height)
  setStyleName(style)

  def getComponents(): TraversableOnce[VaadinComponent] = getComponentIterator.asScala.toSeq

  def add[C <: VaadinComponent](component: C = null): C = {
    addComponent(component)
    component
  }
}