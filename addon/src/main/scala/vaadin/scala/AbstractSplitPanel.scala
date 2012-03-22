package vaadin.scala


// TODO extend MouseClickEvents.ClickEvent
case class SplitterClickEvent(component: Component) extends Event

class SplitterClickListener(val action: SplitterClickEvent => Unit)(implicit wrapper: WrapperRegistry) extends com.vaadin.ui.AbstractSplitPanel.SplitterClickListener with Listener {
  def splitterClick(e: com.vaadin.ui.AbstractSplitPanel#SplitterClickEvent) = action(SplitterClickEvent(wrapper.get[AbstractSplitPanel](e.getComponent()).get))
}

abstract class AbstractSplitPanel(implicit wrapper: WrapperRegistry) extends AbstractLayout {

  override def p: com.vaadin.ui.AbstractSplitPanel

  def first = wr.get[Component](p.getFirstComponent)
  def first_=(component: Component) = p.setFirstComponent(if (component != null) component.p else null)
  def first_=(component: Option[Component]) = p.setFirstComponent(if (component.isDefined) component.get.p else null)

  def second = wr.get[Component](p.getSecondComponent)
  def second_=(component: Component) = p.setSecondComponent(if (component != null) component.p else null)
  def second_=(component: Option[Component]) = p.setSecondComponent(if (component.isDefined) component.get.p else null)

  // TODO: methods for first and second component that return the added component?

  var reserved = false

  def splitPosition = new Measure(p.getSplitPosition(), Units(p.getSplitPositionUnit()))
  def splitPosition_=(position: Option[Measure]) = position match {
    case None => p.setSplitPosition(50, Units.pct.id, reserved)
    case Some(position) => p.setSplitPosition(position.value.intValue, position.unit.id, reserved)
  }

  def locked = p.isLocked();
  def locked_=(locked: Boolean) = p.setLocked(locked)
  
  def splitterClickListeners = new ListenersTrait[SplitterClickEvent => Unit, SplitterClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.AbstractSplitPanel#SplitterClickEvent])
    override def addListener(elem: SplitterClickEvent => Unit) = p.addListener(new SplitterClickListener(elem))
    override def removeListener(elem: SplitterClickListener) = p.removeListener(elem)
  }

}

class HorizontalSplitPanel(implicit wrapper: WrapperRegistry) extends AbstractSplitPanel {

  override val p = new com.vaadin.ui.HorizontalSplitPanel()
  wr.put(this)

  def this(width: Option[Measure] = 100 percent, height: Option[Measure] = 100 percent, caption: String = null, style: String = null)(implicit wrapper: WrapperRegistry) = {
    this()
    this.width = width;
    this.height = height;
    this.caption = caption;
    p.setStyleName(style)
  }

  //  def add[C <: VaadinComponent](component: C = null): C = {
  //    addComponent(component)
  //    component
  //  }

  //def getComponents(): TraversableOnce[VaadinComponent] = getComponentIterator.asScala.toSeq
}

class VerticalSplitPanel(implicit wrapper: WrapperRegistry) extends AbstractSplitPanel {

  override val p = new com.vaadin.ui.VerticalSplitPanel()
  wr.put(this)

  def this(width: Option[Measure] = 100 percent, height: Option[Measure] = 100 percent, caption: String = null, style: String = null)(implicit wrapper: WrapperRegistry) = {
    this()
    this.width = width;
    this.height = height;
    this.caption = caption;
    p.setStyleName(style)
  }

  //  def add[C <: VaadinComponent](component: C = null): C = {
  //    addComponent(component)
  //    component
  //  }
  //
  //  def getComponents(): TraversableOnce[VaadinComponent] = getComponentIterator.asScala.toSeq
}
