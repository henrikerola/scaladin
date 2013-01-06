package vaadin.scala

import scala.collection.mutable
import com.vaadin.ui.Layout.MarginHandler
import vaadin.scala.mixins.AbstractComponentContainerMixin
import vaadin.scala.mixins.AbstractLayoutMixin
import vaadin.scala.mixins.ComponentContainerMixin
import vaadin.scala.mixins.LayoutMixin

package mixins {
  trait ComponentContainerMixin extends ComponentMixin
  trait AbstractComponentContainerMixin extends AbstractComponentMixin with ComponentContainerMixin
  trait LayoutMixin extends ComponentContainerMixin
  trait AbstractLayoutMixin extends AbstractComponentContainerMixin with LayoutMixin
}

trait ComponentContainer extends Component {

  override def p: com.vaadin.ui.ComponentContainer with ComponentContainerMixin

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

  //    public void requestRepaintAll();
  //

  def moveComponentsFrom(source: ComponentContainer): Unit = {
    p.moveComponentsFrom(source.p)
  }

  lazy val components: mutable.Set[Component] = new mutable.Set[Component] with Serializable {
    import scala.collection.JavaConversions.asScalaIterator
    def contains(key: Component) = {
      p.getComponentIterator.contains(key.p)
    }
    def iterator: Iterator[Component] = {
      p.getComponentIterator.map(wrapperFor[Component](_).get)
    }
    def +=(elem: Component) = { addComponent(elem); this }
    def -=(elem: Component) = { removeComponent(elem); this }
  }

  // TODO: listeners
}

abstract class AbstractComponentContainer(override val p: com.vaadin.ui.AbstractComponentContainer with AbstractComponentContainerMixin) extends AbstractComponent(p) with ComponentContainer

case class Margin(top: Boolean = false, right: Boolean = false, bottom: Boolean = false, left: Boolean = false)

trait Layout extends ComponentContainer {

  override def p: com.vaadin.ui.Layout with LayoutMixin

  def margin_=(margin: Boolean) = p.setMargin(margin)
  def margin_=(margin: Margin) = p.setMargin(margin.top, margin.right, margin.bottom, margin.left)
  def margin(top: Boolean = false, right: Boolean = false, bottom: Boolean = false, left: Boolean = false) = p.setMargin(top, right, bottom, left)

}

abstract class AbstractLayout(override val p: com.vaadin.ui.AbstractLayout with AbstractLayoutMixin) extends AbstractComponentContainer(p) with Layout {

  def margin: Margin = {
    val margin = p.getMargin()
    Margin(margin.hasTop, margin.hasRight, margin.hasBottom, margin.hasLeft)
  }
}

trait SpacingHandler {

  def p: com.vaadin.ui.Layout.SpacingHandler

  def spacing = p.isSpacing()
  def spacing_=(spacing: Boolean) = p.setSpacing(spacing)
}

trait AlignmentHandler {

  def p: com.vaadin.ui.Layout.AlignmentHandler

  def alignment(component: Component) = Alignment(p.getComponentAlignment(component.p).getBitMask())

  def alignment(component: Component, alignment: Alignment.Value) = p.setComponentAlignment(component.p, new com.vaadin.ui.Alignment(alignment.id))
  def alignment(componentAlignment: Tuple2[Component, Alignment.Value]) = p.setComponentAlignment(componentAlignment._1.p, new com.vaadin.ui.Alignment(componentAlignment._2.id))
}