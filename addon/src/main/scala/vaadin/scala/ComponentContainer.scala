package vaadin.scala

import scala.collection.mutable
import vaadin.scala.mixins.HasComponentsMixin
import vaadin.scala.mixins.ComponentContainerMixin

package mixins {
  trait ComponentContainerMixin extends HasComponentsMixin { self: com.vaadin.ui.ComponentContainer => }
}

trait ComponentContainer extends HasComponents {

  override def p: com.vaadin.ui.ComponentContainer with ComponentContainerMixin with HasComponentsMixin
  // provide add or addComponent or both?
  def add[C <: Component](component: C): C = {
    p.addComponent(component.p)
    component
  }
  def addComponent[C <: Component](component: C): C = add(component)

  def removeComponent(c: Component) {
    p.removeComponent(c.p)
  }

  def removeAllComponents() {
    p.removeAllComponents()
  }

  def replaceComponent(oldComponent: Component, newComponent: Component) {
    p.replaceComponent(oldComponent.p, newComponent.p)
  }

  def moveComponentsFrom(source: ComponentContainer) {
    p.moveComponentsFrom(source.p)
  }

  override lazy val components: mutable.Set[Component] = new mutable.Set[Component] with Serializable {
    import scala.collection.JavaConversions.asScalaIterator
    def contains(key: Component) = {
      p.iterator.contains(key.p)
    }
    def iterator: Iterator[Component] = {
      p.iterator.map(wrapperFor[Component](_).get)
    }
    def +=(elem: Component) = { addComponent(elem); this }
    def -=(elem: Component) = { removeComponent(elem); this }
  }

  // TODO: listeners
}