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

class SelectedTabChangeListener(action: com.vaadin.ui.TabSheet#SelectedTabChangeEvent => Unit) extends com.vaadin.ui.TabSheet.SelectedTabChangeListener {
  def selectedTabChange(event: com.vaadin.ui.TabSheet#SelectedTabChangeEvent) = action(event)
}

class Accordion(width: Option[Measure] = 100 percent, height: Option[Measure] = None, caption: String = null, style: String = null)
    extends com.vaadin.ui.Accordion() {
  setCaption(caption)
  setWidth(if (width.isDefined) width.get.toString else null)
  setHeight(if (height.isDefined) height.get.toString else null)
  setStyleName(style)

  def addListener(action: com.vaadin.ui.TabSheet#SelectedTabChangeEvent => Unit): Unit = addListener(new SelectedTabChangeListener(action))

  def getComponents(): TraversableOnce[VaadinComponent] = getComponentIterator.asScala.toSeq

}

class CustomComponent(width: Option[Measure] = 100 percent, height: Option[Measure] = None, compositionRoot: Component = null)
    extends com.vaadin.ui.CustomComponent(if (compositionRoot != null) compositionRoot.p else null) {
  setWidth(if (width.isDefined) width.get.toString else null)
  setHeight(if (height.isDefined) height.get.toString else null)
}