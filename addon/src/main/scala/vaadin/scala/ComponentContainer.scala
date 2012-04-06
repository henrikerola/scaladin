package vaadin.scala

import scala.collection.mutable
import com.vaadin.ui.Layout.MarginHandler

trait ComponentContainer extends Component {

  override def p: com.vaadin.ui.ComponentContainer

  // TODO: remove Component part from methods => add, remove, removeAll... ?

  def add[C <: Component](component: C): C = {
    p.addComponent(component.p)
    component
  }

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

  def components: mutable.Set[Component] = new mutable.Set[Component] {
    import scala.collection.JavaConversions.asScalaIterator
    def contains(key: Component) = {
      p.getComponentIterator.contains(key.p)
    }
    def iterator: Iterator[Component] = {
      p.getComponentIterator.map(wr.get[Component](_).get)
    }
    def +=(elem: Component) = { p.addComponent(elem.p); this }
    def -=(elem: Component) = { p.removeComponent(elem.p); this }
  }

  // TODO: listeners
}

abstract class AbstractComponentContainer(implicit wrapper: WrapperRegistry) extends AbstractComponent with ComponentContainer {

  override def p: com.vaadin.ui.AbstractComponentContainer
}

case class Margin(top: Boolean = false, right: Boolean = false, bottom: Boolean = false, left: Boolean = false)

trait Layout extends ComponentContainer {

  override def p: com.vaadin.ui.Layout

  def margin_=(margin: Boolean) = p.setMargin(margin)
  def margin_=(margin: Margin) = p.setMargin(margin.top, margin.right, margin.bottom, margin.left)
  def margin(top: Boolean = false, right: Boolean = false, bottom: Boolean = false, left: Boolean = false) = p.setMargin(top, right, bottom, left)

}

abstract class AbstractLayout(implicit wrapper: WrapperRegistry) extends AbstractComponentContainer with Layout {
  override def p: com.vaadin.ui.AbstractLayout

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
}