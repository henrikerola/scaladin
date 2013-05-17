package vaadin.scala

import event.LayoutClickNotifier
import vaadin.scala.mixins.AbstractOrderedLayoutMixin
import vaadin.scala.Layout._

package mixins {
  trait AbstractOrderedLayoutMixin extends AbstractLayoutMixin { self: com.vaadin.ui.AbstractOrderedLayout => }
}

/**
 * @see com.vaadin.ui.AbstractOrderedLayout
 * @author Henri Kerola / Vaadin
 */
abstract class AbstractOrderedLayout(
  override val p: com.vaadin.ui.AbstractOrderedLayout with AbstractOrderedLayoutMixin)
    extends AbstractLayout(p) with SpacingHandler with AlignmentHandler with LayoutClickNotifier with MarginHandler {

  def add[C <: Component](
    component: C = null,
    ratio: Float = -1,
    alignment: Alignment.Value = null,
    index: Int = -1): C = {

    if (index < 0)
      p.addComponent(component.p)
    else
      p.addComponent(component.p, index)

    if (alignment != null) {
      p.setComponentAlignment(component.p, new com.vaadin.ui.Alignment(alignment.id))
    }

    if (ratio >= 0) {
      p.setExpandRatio(component.p, ratio)
    }

    component
  }

  def getExpandRatio(component: Component): Float = p.getExpandRatio(component.p)
  def setExpandRatio(component: Component, ratio: Float) { p.setExpandRatio(component.p, ratio) }

  // TODO: add addComponentAsFirst ?, listeners
}