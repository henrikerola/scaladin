package vaadin.scala

abstract class AbstractOrderedLayout(implicit wrapper: WrapperRegistry) extends AbstractLayout with SpacingHandler with AlignmentHandler {

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

  // TODO: add addComponentAsFirst ?, listeners
}

class VerticalLayout(implicit wrapper: WrapperRegistry) extends AbstractOrderedLayout /*with LayoutClickListener*/ {

  override val p = new com.vaadin.ui.VerticalLayout
  wr.put(this)

  def this(width: Option[Measure] = 100 percent, height: Option[Measure] = None, margin: Boolean = false, spacing: Boolean = false, caption: String = null, style: String = null, size: Tuple2[String, String] = null)(implicit wrapper: WrapperRegistry) {
    this()

    if (size != null) {
      p.setWidth(size._1)
      p.setHeight(size._2)
    } else {
      this.width = width
      this.height = height
    }
    this.margin = margin
    this.spacing = spacing
    this.caption = caption
    p.setStyleName(style)
  }

}

class HorizontalLayout(implicit wrapper: WrapperRegistry) extends AbstractOrderedLayout /*with LayoutClickListener*/ {

  override val p = new com.vaadin.ui.HorizontalLayout
  wr.put(this)

  def this(width: Option[Measure] = None, height: Option[Measure] = None, margin: Boolean = false, spacing: Boolean = false, caption: String = null, style: String = null)(implicit wrapper: WrapperRegistry) {
    this()
    this.width = width
    this.height = height
    this.margin = margin
    this.spacing = spacing
    this.caption = caption
    p.setStyleName(style)
  }
}

// TODO com.vaadin.ui.FormLayout calls setMargin(true, false, true, false) in constructor
class FormLayout(implicit wrapper: WrapperRegistry) extends AbstractOrderedLayout {

  override val p = new com.vaadin.ui.FormLayout
  wr.put(this)

  def this(width: Option[Measure] = 100 percent, height: Option[Measure] = None, margin: Boolean = false, spacing: Boolean = true, style: String = null)(implicit wrapper: WrapperRegistry) {
    this()
    this.width = width
    this.height = height
    this.margin = margin
    this.spacing = spacing
    p.setStyleName(style)
  }
}