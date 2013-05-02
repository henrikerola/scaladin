package vaadin.scala

import event.AbstractClickEvent
import vaadin.scala.mixins.AbstractSplitPanelMixin
import vaadin.scala.mixins.HorizontalSplitPanelMixin
import vaadin.scala.mixins.VerticalSplitPanelMixin
import vaadin.scala.internal.SplitterClickListener
import vaadin.scala.internal.ListenersTrait
import com.vaadin.server.Sizeable.{ Unit => VaadinUnit }

package mixins {
  trait AbstractSplitPanelMixin extends AbstractComponentContainerMixin { self: com.vaadin.ui.AbstractSplitPanel => }
  trait VerticalSplitPanelMixin extends AbstractSplitPanelMixin { self: com.vaadin.ui.VerticalSplitPanel => }
  trait HorizontalSplitPanelMixin extends AbstractSplitPanelMixin { self: com.vaadin.ui.HorizontalSplitPanel => }
}

object AbstractSplitPanel {
  case class SplitterClickEvent(
    component: Component,
    button: MouseButton.Value,
    clientX: Int,
    clientY: Int,
    relativeX: Int,
    relativeY: Int,
    doubleClick: Boolean,
    altKey: Boolean,
    ctrlKey: Boolean,
    metaKey: Boolean,
    shiftKey: Boolean)
      extends AbstractClickEvent(
        component,
        button,
        clientX,
        clientY,
        relativeX,
        relativeY,
        doubleClick,
        altKey,
        ctrlKey,
        metaKey,
        shiftKey)
}

abstract class AbstractSplitPanel(override val p: com.vaadin.ui.AbstractSplitPanel with AbstractSplitPanelMixin)
    extends AbstractComponentContainer(p) {

  def firstComponent: Option[Component] = wrapperFor(p.getFirstComponent)
  def firstComponent_=(component: Component) { p.setFirstComponent(component.p) }
  def firstComponent_=(component: Option[Component]) { p.setFirstComponent(peerFor(component)) }

  def secondComponent: Option[Component] = wrapperFor(p.getSecondComponent)
  def secondComponent_=(component: Component) { p.setSecondComponent(component.p) }
  def secondComponent_=(component: Option[Component]) { p.setSecondComponent(peerFor(component)) }

  var reserved = false

  def splitPosition: Measure = new Measure(p.getSplitPosition, Units(p.getSplitPositionUnit.ordinal))
  def splitPosition_=(position: Option[Measure]) {
    position match {
      case None => p.setSplitPosition(50, VaadinUnit.PERCENTAGE, reserved)
      case Some(pos) => p.setSplitPosition(pos.value.intValue, VaadinUnit.values.apply(pos.unit.id), reserved)
    }
  }
  def splitPosition_=(position: Measure) { splitPosition = Some(position) }

  def minSplitPosition: Measure = Measure(p.getMinSplitPosition, Units(p.getMinSplitPositionUnit.ordinal))
  def minSplitPosition_=(minSplitPosition: Option[Measure]) {
    minSplitPosition match {
      case None => p.setMinSplitPosition(0, com.vaadin.server.Sizeable.Unit.PERCENTAGE)
      case Some(pos) => p.setMinSplitPosition(pos.value.intValue, VaadinUnit.values.apply(pos.unit.id))
    }
  }

  def maxSplitPosition: Measure = Measure(p.getMaxSplitPosition, Units(p.getMaxSplitPositionUnit.ordinal))
  def maxSplitPosition_=(maxSplitPosition: Option[Measure]) {
    maxSplitPosition match {
      case None => p.setMaxSplitPosition(100, VaadinUnit.PERCENTAGE)
      case Some(pos) => p.setMaxSplitPosition(pos.value.floatValue, VaadinUnit.values.apply(pos.unit.id))
    }
  }

  def locked: Boolean = p.isLocked
  def locked_=(locked: Boolean) { p.setLocked(locked) }

  lazy val splitterClickListeners: ListenersSet[AbstractSplitPanel.SplitterClickEvent => Unit] =
    new ListenersTrait[AbstractSplitPanel.SplitterClickEvent, SplitterClickListener] {
      override def listeners = p.getListeners(classOf[com.vaadin.ui.AbstractSplitPanel.SplitterClickEvent])
      override def addListener(elem: AbstractSplitPanel.SplitterClickEvent => Unit) =
        p.addSplitterClickListener(new SplitterClickListener(elem))
      override def removeListener(elem: SplitterClickListener) = p.removeSplitterClickListener(elem)
    }

}

class HorizontalSplitPanel(override val p: com.vaadin.ui.HorizontalSplitPanel with HorizontalSplitPanelMixin = new com.vaadin.ui.HorizontalSplitPanel with HorizontalSplitPanelMixin) extends AbstractSplitPanel(p)

class VerticalSplitPanel(override val p: com.vaadin.ui.VerticalSplitPanel with VerticalSplitPanelMixin = new com.vaadin.ui.VerticalSplitPanel with VerticalSplitPanelMixin) extends AbstractSplitPanel(p)