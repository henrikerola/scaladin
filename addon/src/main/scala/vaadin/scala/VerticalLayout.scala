package vaadin.scala

trait AbstractOrderedLayout extends AbstractLayout with SpacingHandler with AlignmentHandler {

  override def p: com.vaadin.ui.AbstractOrderedLayout

  def add[C <: Component](component: C = null, ratio: Float = -1, alignment: Alignment.Value = null, index: Int = -1): C = {
    if (index < 0)
      p.addComponent(component.p)
    else
      p.addComponent(component.p, index)

    if (alignment != null) p.setComponentAlignment(component.p, new com.vaadin.ui.Alignment(alignment.id))
    if (ratio >= 0) p.setExpandRatio(component.p, ratio)

    component
  }

  // Allow adding com.vaadin.ui.Components too
  def addComponent[C <: com.vaadin.ui.Component](component: C = null, ratio: Float = -1, alignment: Alignment.Value = null, index: Int = -1): C = {
    if (index < 0)
      p.addComponent(component)
    else
      p.addComponent(component, index)

    if (alignment != null) p.setComponentAlignment(component, new com.vaadin.ui.Alignment(alignment.id))
    if (ratio >= 0) p.setExpandRatio(component, ratio)

    component
  }
  
  def expandRatio(component: Component) = p.getExpandRatio(component.p)
  def expandRatio(component: Component, ratio: Float) = p.setExpandRatio(component.p, ratio)
}

class VerticalLayout extends AbstractOrderedLayout /*with LayoutClickListener*/ {

  override val p = new com.vaadin.ui.VerticalLayout

  def this(width: String = 100 percent, height: String = null, margin: Boolean = false, spacing: Boolean = false, caption: String = null, style: String = null, size: Tuple2[String, String] = null) {
    this()

    if (size != null) {
      p.setWidth(size._1)
      p.setHeight(size._2)
    } else {
      p.setWidth(width)
      p.setHeight(height)
    }
    p.setMargin(margin)
    p.setSpacing(spacing)
    p.setCaption(caption)
    p.setStyleName(style)
  }

}