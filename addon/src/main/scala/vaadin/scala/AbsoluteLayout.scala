package vaadin.scala

class AbsoluteLayout(val p: com.vaadin.ui.AbsoluteLayout = new com.vaadin.ui.AbsoluteLayout)(implicit wrapper: WrapperRegistry) extends AbstractLayout {

  def add[C <: Component](component: C, location: String): C = {
    p.addComponent(component.p, location)
    component
  }

  override def add[C <: Component](component: C): C = {
    add(component, "")
  }

  // TODO: layout click listener

  def position(component: Component) = p.getPosition(component.p) match {
    case null     => throw new RuntimeException("Component is not attached to the layout")
    case position => new ComponentPosition(position)
  }

  class ComponentPosition(val p: com.vaadin.ui.AbsoluteLayout#ComponentPosition) extends Wrapper {

    def cssString = p.getCSSString()
    def cssString_=(cssString: String) = p.setCSSString(cssString)

    def zIndex = p.getZIndex
    def zIndex_=(zIndex: Int) = p.setZIndex(zIndex)

    def top = Option(if (p.getTopValue == null) null else Measure(p.getTopValue, Units(p.getTopUnits)))
    def top_=(top: Option[Measure]): Unit = if (top == None) p.setTopValue(null) else this.top = top.get
    def top_=(top: Measure): Unit = p.setTop(top.value.floatValue, top.unit.id)

    def right = Option(if (p.getRightValue == null) null else Measure(p.getRightValue, Units(p.getRightUnits)))
    def right_=(right: Option[Measure]): Unit = if (right == None) p.setRightValue(null) else this.right = right.get
    def right_=(right: Measure): Unit = p.setRight(right.value.floatValue, right.unit.id)

    def bottom = Option(if (p.getBottomValue == null) null else Measure(p.getBottomValue, Units(p.getBottomUnits)))
    def bottom_=(bottom: Option[Measure]): Unit = if (bottom == None) p.setBottomValue(null) else this.bottom = bottom.get
    def bottom_=(bottom: Measure): Unit = p.setBottom(bottom.value.floatValue, bottom.unit.id)

    def left = Option(if (p.getLeftValue == null) null else Measure(p.getLeftValue, Units(p.getLeftUnits)))
    def left_=(left: Option[Measure]): Unit = if (left == None) p.setLeftValue(null) else this.left = left.get
    def left_=(left: Measure): Unit = p.setLeft(left.value.floatValue, left.unit.id)
  }
}