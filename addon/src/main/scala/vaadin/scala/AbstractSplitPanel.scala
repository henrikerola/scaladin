package vaadin.scala

trait AbstractSplitPanel extends AbstractLayout {

  override def p: com.vaadin.ui.AbstractSplitPanel

  def first = wr.get[Component](p.getFirstComponent)
  def first_=(component: Component) = p.setFirstComponent(if (component != null) component.p else null)
  def first_=(component: Option[Component]) = p.setFirstComponent(if (component.isDefined) component.get.p else null)

  def second = wr.get[Component](p.getSecondComponent)
  def second_=(component: Component) = p.setSecondComponent(if (component != null) component.p else null)
  def second_=(component: Option[Component]) = p.setSecondComponent(if (component.isDefined) component.get.p else null)

  // TODO: methods for first and second component that return the added component?

  // TODO: methods for setting split position

  def locked = p.isLocked();
  def locked_=(locked: Boolean) = p.setLocked(locked)

}

class HorizontalSplitPanel(width: String = 100 percent, height: String = 100 percent, caption: String = null, style: String = null)(implicit val wr: WrapperRegistry)
    extends AbstractSplitPanel {

  override val p = new com.vaadin.ui.HorizontalSplitPanel()
  wr.put(this)

  p.setWidth(width)
  p.setHeight(height)
  p.setCaption(caption)
  p.setStyleName(style)

  //  def add[C <: VaadinComponent](component: C = null): C = {
  //    addComponent(component)
  //    component
  //  }

  //def getComponents(): TraversableOnce[VaadinComponent] = getComponentIterator.asScala.toSeq
}

class VerticalSplitPanel(width: String = 100 percent, height: String = 100 percent, caption: String = null, style: String = null)(implicit val wr: WrapperRegistry)
    extends AbstractSplitPanel {

  override val p = new com.vaadin.ui.VerticalSplitPanel()
  wr.put(this)

  p.setWidth(width)
  p.setHeight(height)
  p.setCaption(caption)
  p.setStyleName(style)

  //  def add[C <: VaadinComponent](component: C = null): C = {
  //    addComponent(component)
  //    component
  //  }
  //
  //  def getComponents(): TraversableOnce[VaadinComponent] = getComponentIterator.asScala.toSeq
}
