package vaadin.scala

import scala.collection.mutable.Map
import com.vaadin.ui.{ Alignment, Component }
import com.vaadin.event.LayoutEvents.LayoutClickNotifier

trait LayoutClickListener extends LayoutClickNotifier {
  def addLayoutClickListener(action: com.vaadin.event.LayoutEvents.LayoutClickEvent => Unit) {
    addListener(new com.vaadin.event.LayoutEvents.LayoutClickListener {
      def layoutClick(event: com.vaadin.event.LayoutEvents.LayoutClickEvent) = action(event)
    })
  }
}

trait ParametrizedAddComponentForOrdered extends com.vaadin.ui.AbstractOrderedLayout {
  def add[C <: com.vaadin.ui.Component](component: C = null, ratio: Float = -1, alignment: Alignment = null, index: Int = -1): C = {
    if (index < 0)
      addComponent(component)
    else
      addComponent(component, index)

    if (alignment != null) setComponentAlignment(component, alignment)
    if (ratio >= 0) setExpandRatio(component, ratio)

    component
  }
}

class HorizontalLayout(width: String = null, height: String = null, margin: Boolean = false, spacing: Boolean = false, caption: String = null, style: String = null)
  extends com.vaadin.ui.HorizontalLayout with LayoutClickListener with ParametrizedAddComponentForOrdered {
  setWidth(width)
  setHeight(height)
  setMargin(margin)
  setSpacing(spacing)
  setCaption(caption)
  setStyleName(style)
}

class VerticalLayout(width: String = 100 percent, height: String = null, margin: Boolean = false, spacing: Boolean = false, caption: String = null, style: String = null, size: Tuple2[String, String] = null)
  extends com.vaadin.ui.VerticalLayout with LayoutClickListener with ParametrizedAddComponentForOrdered {
  if (size != null) {
    setWidth(size._1)
    setHeight(size._2)
  } else {
    setWidth(width)
    setHeight(height)
  }
  setMargin(margin)
  setSpacing(spacing)
  setCaption(caption)
  setStyleName(style)
}

// TODO com.vaadin.ui.FormLayout calls setMargin(true, false, true, false) in constructor
class FormLayout(width: String = 100 percent, height: String = null, margin: Boolean = false, spacing: Boolean = true, style: String = null)
  extends com.vaadin.ui.FormLayout {
  def add[C <: com.vaadin.ui.Component](component: C): C = {
    addComponent(component)
    component
  }
}

class GridLayout(width: String = null, height: String = null, margin: Boolean = false, spacing: Boolean = false, caption: String = null, style: String = null, columns: Int = 1, rows: Int = 1)
  extends com.vaadin.ui.GridLayout with LayoutClickListener {
  setWidth(width)
  setHeight(height)
  setMargin(margin)
  setSpacing(spacing)
  setStyleName(style)
  setCaption(caption)
  setColumns(columns)
  setRows(rows)

  def add[C <: com.vaadin.ui.Component](component: C = null, col: Int = -1, row: Int = -1, col2: Int = -1, row2: Int = -1, alignment: Alignment = null): C = {
    if (col >= 0 && row >= 0)
      addComponent(component, col, row)
    else if (col >= 0 && row >= 0 && col2 >= 0 && row2 >= 0)
      addComponent(component, col, row, col2, row2)
    else
      addComponent(component)
    if (alignment != null) setComponentAlignment(component, alignment)
    component
  }

}

class CssLayout(width: String = null, height: String = null, margin: Boolean = false, style: String = null, caption: String = null, size: Tuple2[String, String] = null)
  extends com.vaadin.ui.CssLayout with LayoutClickListener {
    if (size != null) {
    setWidth(size._1)
    setHeight(size._2)
  } else {
    setWidth(width)
    setHeight(height)
  }
  setMargin(margin)
  setStyleName(style)
  setCaption(caption)

  // TODO remove css from map when component is removed from the layout
  val cssMap = Map[Component, String]()

  def add[C <: com.vaadin.ui.Component](component: C, css: String = null): C = {
    addComponent(component)
    if (css != null) cssMap(component) = css
    component
  }

  override def getCss(c: Component): String = {
    if (cssMap contains c) cssMap(c) else null
  }
}

class CustomLayout(width: String = 100 percent, height: String = null, template: String = null, contents: String = null, caption: String = null, style: String = null)
  extends com.vaadin.ui.CustomLayout {
  setWidth(width)
  setHeight(height)
  setCaption(caption)
  setStyleName(style)

  if (template != null)
    setTemplateName(template)
  else if (contents != null)
    setTemplateContents(contents)

  def add[C <: com.vaadin.ui.Component](component: C, location: String): C = {
    addComponent(component, location)
    component
  }
}

class AbsoluteLayout(width: String = 100 percent, height: String = 100 percent, caption: String = null, style: String = null)
  extends com.vaadin.ui.AbsoluteLayout with LayoutClickListener {
  setWidth(width)
  setHeight(height)
  setCaption(caption)
  setStyleName(style)
  
  def add[C <: com.vaadin.ui.Component](component: C, location: String): C = {
    addComponent(component, location)
    component
  }
}