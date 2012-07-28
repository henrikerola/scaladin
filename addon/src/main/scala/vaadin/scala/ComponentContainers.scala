package vaadin.scala

import com.vaadin.ui.{ ComponentContainer => VaadinComponentContainer }
import com.vaadin.ui.{ Component => VaadinComponent }
import scala.collection.JavaConverters._

trait FilterableComponentContainer extends ComponentContainer {

  def filter(filterFunction: VaadinComponent => Boolean): List[VaadinComponent] = p.getComponentIterator.asScala.filter(filterFunction).toList

  def filterRecursive(filterFunction: VaadinComponent => Boolean): List[VaadinComponent] = {
    var newList: List[VaadinComponent] = Nil
    for (component <- p.getComponentIterator.asScala) {
      if (filterFunction(component))
        newList = component :: newList
      if (component.isInstanceOf[VaadinComponentContainer]) {
        newList = component.asInstanceOf[FilterableComponentContainer].filterRecursive(filterFunction) ::: newList
      }
    }
    newList
  }
}