package vaadin.scala

import vaadin.scala.mixins.AbstractOrderedLayoutMixin
import vaadin.scala.mixins.HorizontalLayoutMixin
import vaadin.scala.mixins.FormLayoutMixin
import vaadin.scala.mixins.VerticalLayoutMixin

package mixins {
  trait AbstractOrderedLayoutMixin extends AbstractLayoutMixin
  trait VerticalLayoutMixin extends AbstractOrderedLayoutMixin
  trait HorizontalLayoutMixin extends AbstractOrderedLayoutMixin
  trait FormLayoutMixin extends AbstractOrderedLayoutMixin
}

abstract class AbstractOrderedLayout(override val p: com.vaadin.ui.AbstractOrderedLayout with AbstractOrderedLayoutMixin)
  extends AbstractLayout(p) with SpacingHandler with AlignmentHandler with LayoutClickNotifier {

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

class VerticalLayout(override val p: com.vaadin.ui.VerticalLayout with VerticalLayoutMixin = new com.vaadin.ui.VerticalLayout with VerticalLayoutMixin) extends AbstractOrderedLayout(p) /*with LayoutClickListener*/ {

  /*-
  def this(width: Option[Measure] = 100 percent, height: Option[Measure] = None, margin: Boolean = false, spacing: Boolean = false, caption: String = null, style: String = null, size: Tuple2[String, String] = null) {
    this(new com.vaadin.ui.VerticalLayout)

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
  }*/

}

class HorizontalLayout(override val p: com.vaadin.ui.HorizontalLayout with HorizontalLayoutMixin = new com.vaadin.ui.HorizontalLayout with HorizontalLayoutMixin) extends AbstractOrderedLayout(p) /*with LayoutClickListener*/ {

  /*-
  def this(width: Option[Measure] = None, height: Option[Measure] = None, margin: Boolean = false, spacing: Boolean = false, caption: String = null, style: String = null) = {
    this(new com.vaadin.ui.HorizontalLayout)
    this.width = width
    this.height = height
    this.margin = margin
    this.spacing = spacing
    this.caption = caption
    p.setStyleName(style)
  }*/
}

// TODO com.vaadin.ui.FormLayout calls setMargin(true, false, true, false) in constructor
class FormLayout(override val p: com.vaadin.ui.FormLayout with FormLayoutMixin = new com.vaadin.ui.FormLayout with FormLayoutMixin) extends AbstractOrderedLayout(p) {

  /*-
  def this(width: Option[Measure] = 100 percent, height: Option[Measure] = None, margin: Boolean = false, spacing: Boolean = true, style: String = null) = {
    this(new com.vaadin.ui.FormLayout)
    this.width = width
    this.height = height
    this.margin = margin
    this.spacing = spacing
    p.setStyleName(style)
  }*/
}