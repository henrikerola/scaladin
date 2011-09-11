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

class HorizontalLayout(caption: String = null, width: String = null, height: String = null, margin: Boolean = false, spacing: Boolean = false, style: String = null)
    extends com.vaadin.ui.HorizontalLayout with LayoutClickListener {
  setCaption(caption)
  setWidth(width);
  setHeight(height);
  setMargin(margin)
  setSpacing(spacing);
  setStyleName(style)

  def add(component: Component = null, ratio: Float = 0, alignment: Alignment = null): HorizontalLayout = {
    addComponent(component);
    if (alignment != null) setComponentAlignment(component, alignment);
    setExpandRatio(component, ratio);
    this
  }
}

class VerticalLayout(caption: String = null, width: String = 100 percent, height: String = null, margin: Boolean = false, spacing: Boolean = false, style: String = null)
    extends com.vaadin.ui.VerticalLayout with LayoutClickListener {
  setCaption(caption)
  setWidth(width)
  setHeight(height)
  setMargin(margin)
  setSpacing(spacing)
  setStyleName(style)

  def add(component: Component, ratio: Float = 0, alignment: Alignment = null): VerticalLayout = {
    addComponent(component);
    setExpandRatio(component, ratio);
    if (alignment != null) setComponentAlignment(component, alignment);
    this
  }
}

// TODO com.vaadin.ui.FormLayout calls setMargin(true, false, true, false); in constructor
class FormLayout(width: String = 100 percent, height: String = null, margin: Boolean = false, spacing: Boolean = true, style: String = null)
    extends com.vaadin.ui.FormLayout {
  def add(component: Component): FormLayout = {
    addComponent(component);
    this
  }
}

class GridLayout(caption: String = null, width: String = null, height: String = null, margin: Boolean = false, spacing: Boolean = false, style: String = null, columns: Int = 1, rows: Int = 1)
    extends com.vaadin.ui.GridLayout with LayoutClickListener {
  setCaption(caption)
  setWidth(width)
  setHeight(height)
  setMargin(margin)
  setSpacing(spacing)
  setStyleName(style)
  setColumns(columns)
  setRows(rows)

  def add(component: Component = null, col: Int = -1, row: Int = -1, col2: Int = -1, row2: Int = -1, alignment: Alignment = null): GridLayout = {
    if (col >= 0 && row >= 0)
      addComponent(component, col, row)
    else if (col >= 0 && row >= 0 && col2 >= 0 && row2 >= 0)
      addComponent(component, col, row, col2, row2)
    else
      addComponent(component)
    if (alignment != null) setComponentAlignment(component, alignment);
    this
  }
}

class CssLayout(width: String = null, height: String = null, margin: Boolean = false, style: String = null, caption: String = null)
    extends com.vaadin.ui.CssLayout with LayoutClickListener {
  setWidth(width)
  setHeight(height)
  setMargin(margin)
  setStyleName(style)
  setCaption(caption)

  // TODO remove css from map when component is removed from the layout
  val cssMap = Map[Component, String]()

  def add(component: Component, css: String = null): CssLayout = {
    addComponent(component)
    if (css != null) cssMap(component) = css;
    this
  }

  override def getCss(c: Component): String = {
    if (cssMap contains c) cssMap(c) else null
  }
}

class CustomLayout(width: String = 100 percent, height: String = null, template: String = null, contents: String = null, style: String = null)
    extends com.vaadin.ui.CustomLayout {
  setWidth(width)
  setHeight(height)
  setStyleName(style)

  if (template != null)
    setTemplateName(template)
  else if (contents != null)
    setTemplateContents(contents)

  def add(component: Component, location: String): CustomLayout = {
    addComponent(component, location)
    this
  }
}