package vaadin.scala

import event.LayoutClickNotifier
import vaadin.scala.mixins.AbsoluteLayoutMixin
import com.vaadin.ui.{ AbsoluteLayout => VaadinAbsoluteLayout }
import com.vaadin.server.Sizeable.{ Unit => VaadinUnit }

package mixins {
  trait AbsoluteLayoutMixin extends AbstractLayoutMixin { self: com.vaadin.ui.AbsoluteLayout => }
}

/**
 * @see com.vaadin.ui.AbsoluteLayout
 * @author Henri Kerola / Vaadin
 */
class AbsoluteLayout(
  override val p: VaadinAbsoluteLayout with AbsoluteLayoutMixin = new VaadinAbsoluteLayout with AbsoluteLayoutMixin)
    extends AbstractLayout(p) with LayoutClickNotifier {

  def add[C <: Component](component: C, location: String): C = {
    p.addComponent(component.p, location)
    component
  }

  def getPosition(component: Component): Option[ComponentPosition] =
    Option(p.getPosition(component.p)) map { new ComponentPosition(_) }

  class ComponentPosition(override val p: com.vaadin.ui.AbsoluteLayout#ComponentPosition) extends Wrapper {

    def cssString: String = p.getCSSString()
    def cssString_=(cssString: String) { p.setCSSString(cssString) }

    def zIndex: Int = p.getZIndex
    def zIndex_=(zIndex: Int) { p.setZIndex(zIndex) }

    def top: Option[Measure] =
      Option(if (p.getTopValue == null) null else Measure(p.getTopValue, Units(p.getTopUnits.ordinal)))
    def top_=(top: Option[Measure]) { if (top == None) p.setTopValue(null) else this.top = top.get }
    def top_=(top: Measure) { p.setTop(top.value.floatValue, VaadinUnit.values.apply(top.unit.id)) }

    def right: Option[Measure] =
      Option(if (p.getRightValue == null) null else Measure(p.getRightValue, Units(p.getRightUnits.ordinal)))
    def right_=(right: Option[Measure]) { if (right == None) p.setRightValue(null) else this.right = right.get }
    def right_=(right: Measure) { p.setRight(right.value.floatValue, VaadinUnit.values.apply(right.unit.id)) }

    def bottom: Option[Measure] =
      Option(if (p.getBottomValue == null) null else Measure(p.getBottomValue, Units(p.getBottomUnits.ordinal)))
    def bottom_=(bottom: Option[Measure]) { if (bottom == None) p.setBottomValue(null) else this.bottom = bottom.get }
    def bottom_=(bottom: Measure) { p.setBottom(bottom.value.floatValue, VaadinUnit.values.apply(bottom.unit.id)) }

    def left: Option[Measure] =
      Option(if (p.getLeftValue == null) null else Measure(p.getLeftValue, Units(p.getLeftUnits.ordinal)))
    def left_=(left: Option[Measure]) { if (left == None) p.setLeftValue(null) else this.left = left.get }
    def left_=(left: Measure) { p.setLeft(left.value.floatValue, VaadinUnit.values.apply(left.unit.id)) }
  }
}