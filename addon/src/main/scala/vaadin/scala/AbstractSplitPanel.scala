package vaadin.scala

import vaadin.scala.mixins.AbstractSplitPanelMixin
import vaadin.scala.mixins.HorizontalSplitPanelMixin
import vaadin.scala.mixins.VerticalSplitPanelMixin

package mixins {
  trait AbstractSplitPanelMixin extends AbstractLayoutMixin
  trait VerticalSplitPanelMixin extends AbstractSplitPanelMixin
  trait HorizontalSplitPanelMixin extends AbstractSplitPanelMixin
}

// TODO extend MouseClickEvents.ClickEvent
case class SplitterClickEvent(component: Component) extends Event

class SplitterClickListener(val action: SplitterClickEvent => Unit) extends com.vaadin.ui.AbstractSplitPanel.SplitterClickListener with Listener {
  def splitterClick(e: com.vaadin.ui.AbstractSplitPanel#SplitterClickEvent) = action(SplitterClickEvent(wrapperFor[AbstractSplitPanel](e.getComponent()).get))
}

abstract class AbstractSplitPanel(override val p: com.vaadin.ui.AbstractSplitPanel with AbstractSplitPanelMixin) extends AbstractLayout(p) {

  def first = wrapperFor[Component](p.getFirstComponent)
  def first_=(component: Component) = p.setFirstComponent(if (component != null) component.p else null)
  def first_=(component: Option[Component]) = p.setFirstComponent(if (component.isDefined) component.get.p else null)

  def second = wrapperFor[Component](p.getSecondComponent)
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
  
  lazy val splitterClickListeners = new ListenersTrait[SplitterClickEvent, SplitterClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.AbstractSplitPanel#SplitterClickEvent])
    override def addListener(elem: SplitterClickEvent => Unit) = p.addListener(new SplitterClickListener(elem))
    override def removeListener(elem: SplitterClickListener) = p.removeListener(elem)
  }

}

class HorizontalSplitPanel(override val p: com.vaadin.ui.HorizontalSplitPanel with HorizontalSplitPanelMixin = new com.vaadin.ui.HorizontalSplitPanel with HorizontalSplitPanelMixin) extends AbstractSplitPanel(p)

class VerticalSplitPanel(override val p: com.vaadin.ui.VerticalSplitPanel with VerticalSplitPanelMixin = new com.vaadin.ui.VerticalSplitPanel with VerticalSplitPanelMixin) extends AbstractSplitPanel(p)