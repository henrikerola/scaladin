package vaadin.scala

import scala.collection.mutable
import vaadin.scala.mixins.HasComponentsMixin
import vaadin.scala.mixins.AbstractComponentContainerMixin
import vaadin.scala.mixins.AbstractLayoutMixin
import vaadin.scala.mixins.ComponentContainerMixin
import vaadin.scala.mixins.LayoutMixin
import vaadin.scala.mixins.SingleComponentContainerMixin
import vaadin.scala.mixins.AbstractSingleComponentContainerMixin

package mixins {
  trait ComponentContainerMixin extends HasComponentsMixin { self: com.vaadin.ui.ComponentContainer => }
  trait SingleComponentContainerMixin extends HasComponentsMixin { self: com.vaadin.ui.SingleComponentContainer => }
  trait AbstractSingleComponentContainerMixin extends AbstractComponentMixin with SingleComponentContainerMixin { self: com.vaadin.ui.AbstractSingleComponentContainer => }
  trait AbstractLayoutMixin extends AbstractComponentContainerMixin with LayoutMixin { self: com.vaadin.ui.AbstractLayout => }
}

trait ComponentContainer extends HasComponents {

  override def p: com.vaadin.ui.ComponentContainer with ComponentContainerMixin with HasComponentsMixin
  // provide add or addComponent or both?
  def add[C <: Component](component: C): C = {
    p.addComponent(component.p)
    component
  }
  def addComponent[C <: Component](component: C): C = add(component)

  def removeComponent(c: Component): Unit = {
    p.removeComponent(c.p)
  }

  def removeAllComponents(): Unit = {
    p.removeAllComponents()
  }

  def replaceComponent(oldComponent: Component, newComponent: Component): Unit = {
    p.replaceComponent(oldComponent.p, newComponent.p)
  }

  def moveComponentsFrom(source: ComponentContainer): Unit = {
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

trait SingleComponentContainer extends HasComponents { // TODO: implements also ComponentAttachDetachNotifier

  def p: com.vaadin.ui.SingleComponentContainer with SingleComponentContainerMixin

  def componentCount: Int = p.getComponentCount

  def content: Option[Component] = wrapperFor[Component](p.getContent)
  def content_=(content: Component) = p.setContent(content.p) // TODO: content_=(Option[Component]) ?
}

trait SelectiveRenderer extends HasComponents {

  def p: com.vaadin.ui.SelectiveRenderer with HasComponentsMixin

  def isRendered(component: Component): Boolean = p.isRendered(component.p)
}

abstract class AbstractSingleComponentContainer(override val p: com.vaadin.ui.AbstractSingleComponentContainer with AbstractSingleComponentContainerMixin) extends AbstractComponent(p) with SingleComponentContainer {

  // TODO
}

abstract class AbstractLayout(override val p: com.vaadin.ui.AbstractLayout with AbstractLayoutMixin) extends AbstractComponentContainer(p) with Layout {

}