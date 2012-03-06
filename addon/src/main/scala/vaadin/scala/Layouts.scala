package vaadin.scala

import scala.collection.mutable.Map
import com.vaadin.ui.{ Alignment => VaadinAlignment, Component => VaadinComponent }
import com.vaadin.event.LayoutEvents.LayoutClickNotifier

trait LayoutClickListener extends LayoutClickNotifier {
  def addLayoutClickListener(action: com.vaadin.event.LayoutEvents.LayoutClickEvent => Unit) {
    addListener(new com.vaadin.event.LayoutEvents.LayoutClickListener {
      def layoutClick(event: com.vaadin.event.LayoutEvents.LayoutClickEvent) = action(event)
    })
  }
}

class HorizontalLayout(width: String = null, height: String = null, margin: Boolean = false, spacing: Boolean = false, caption: String = null, style: String = null)
  extends AbstractOrderedLayout /*with LayoutClickListener*/ {
  
  override val p = new com.vaadin.ui.HorizontalLayout
  WrapperRegistry.put(this)
  
  p.setWidth(width)
  p.setHeight(height)
  p.setMargin(margin)
  p.setSpacing(spacing)
  p.setCaption(caption)
  p.setStyleName(style)
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

  def add[C <: com.vaadin.ui.Component](component: C = null, col: Int = -1, row: Int = -1, col2: Int = -1, row2: Int = -1, alignment: VaadinAlignment = null): C = {
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
  extends AbstractLayout /*with LayoutClickListener*/ {

  override val p = new com.vaadin.ui.CssLayout {
    override def getCss(c: VaadinComponent): String = {
      cssMap.getOrElse(c, null)
    }
  }
  WrapperRegistry.put(this)

  if (size != null) {
    p.setWidth(size._1)
    p.setHeight(size._2)
  } else {
    p.setWidth(width)
    p.setHeight(height)
  }
  p.setMargin(margin)
  p.setStyleName(style)
  p.setCaption(caption)

  // TODO remove css from map when component is removed from the layout
  val cssMap = Map[VaadinComponent, String]()

  def add[C <: Component](component: C, css: String = null): C = {
    addComponent(component)
    if (css != null) cssMap(component.p) = css
    component
  }

  // TODO: addComponentAsFirst?
  // TODO: listeners
  
  // TODO:
  //  def css(c: Component): String = {
  //	  p.getCss(c.p)
  //  }
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