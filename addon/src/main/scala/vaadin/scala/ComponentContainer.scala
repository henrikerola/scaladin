package vaadin.scala

import scala.collection.mutable

trait ComponentContainer extends Component {

  override def p: com.vaadin.ui.ComponentContainer

  // TODO: remove Component part from methods => add, remove, removeAll... ?

  def addComponent(c: Component): Unit = {
    p.addComponent(c.p)
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

  def components(): mutable.Set[Component] = new mutable.Set[Component] {
    import scala.collection.JavaConversions.asScalaIterator
    def contains(key: Component) = {
      p.getComponentIterator.contains(key.p)
    }
    def iterator: Iterator[Component] = {
      p.getComponentIterator.map(WrapperRegistry.get(_))
    }
    def +=(elem: Component) = { p.addComponent(elem.p); this }
    def -=(elem: Component) = { p.removeComponent(elem.p); this }
  }

  // TODO: listeners
}

trait AbstractComponentContainer extends AbstractComponent with ComponentContainer {

  override def p: com.vaadin.ui.AbstractComponentContainer

}

trait Layout extends ComponentContainer {
  // TODO
}

// TODO implement MarginHandler
trait AbstractLayout extends AbstractComponentContainer with Layout {

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