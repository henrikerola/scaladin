package vaadin.scala
import scala.collection.mutable

object WrapperRegistry {
  
  private[this] val map = new mutable.WeakHashMap[com.vaadin.ui.Component, Component]
  
  def put(component: Component) {
    if (component == null) {
      throw new IllegalArgumentException("component cannot be null")
    }
    if (component.p == null) {
      throw new IllegalArgumentException("component.p cannot be null")
    }
    
    map += component.p -> component
  }
  
  def get(vaadinComponent: com.vaadin.ui.Component): Component = {
    map.getOrElse(vaadinComponent, throw new IllegalStateException("cannot find wrapper for " + vaadinComponent))
  }
}