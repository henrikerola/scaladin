package vaadin.scala
import com.vaadin.data.Container

trait ValueChangeFunction extends com.vaadin.data.Property.ValueChangeNotifier {
  def addListener(action: com.vaadin.data.Property.ValueChangeEvent => Unit): Unit = addListener(new vaadin.scala.PropertyValueChangeListener(action))
}

class PropertyValueChangeListener(action: com.vaadin.data.Property.ValueChangeEvent => Unit)
    extends com.vaadin.data.Property.ValueChangeListener {
  override def valueChange(event: com.vaadin.data.Property.ValueChangeEvent) = action(event)
}

class NativeSelect(caption: String = null, width: String = null, height: String = null, style: String = null, nullSelectionAllowed: Boolean = true)
    extends com.vaadin.ui.NativeSelect(caption) with ValueChangeFunction {
  setWidth(width)
  setHeight(height)
  setStyleName(style)
  setNullSelectionAllowed(nullSelectionAllowed)
}

class ComboBox(caption: String = null, width: String = null, height: String = null, dataSource: Container = null, style: String = null, prompt: String = null, nullSelectionAllowed: Boolean = true)
    extends com.vaadin.ui.ComboBox(caption) with ValueChangeFunction {
  setWidth(width)
  setHeight(height)
  if (dataSource != null) setContainerDataSource(dataSource)
  setStyleName(style)
  setInputPrompt(prompt)
  setNullSelectionAllowed(nullSelectionAllowed)
}

class ListSelect(caption: String = null, width: String = null, height: String = null, dataSource: Container = null, style: String = null, nullSelectionAllowed: Boolean = true)
    extends com.vaadin.ui.ListSelect(caption) with ValueChangeFunction {
  setWidth(width)
  setHeight(height)
  if (dataSource != null) setContainerDataSource(dataSource)
  setStyleName(style)
  setNullSelectionAllowed(nullSelectionAllowed)
}

class OptionGroup(caption: String = null, width: String = null, height: String = null, dataSource: Container = null, style: String = null, prompt: String = null, nullSelectionAllowed: Boolean = true)
    extends com.vaadin.ui.OptionGroup(caption) with ValueChangeFunction {
  setWidth(width)
  setHeight(height)
  if (dataSource != null) setContainerDataSource(dataSource)
  setStyleName(style)
  setNullSelectionAllowed(nullSelectionAllowed)
}

class TwinColSelect(caption: String = null, width: String = null, height: String = null, dataSource: Container = null, style: String = null, prompt: String = null, nullSelectionAllowed: Boolean = true)
    extends com.vaadin.ui.TwinColSelect(caption) with ValueChangeFunction {
  setWidth(width)
  setHeight(height)
  if (dataSource != null) setContainerDataSource(dataSource)
  setStyleName(style)
  setNullSelectionAllowed(nullSelectionAllowed)
}

trait ItemClickListener extends com.vaadin.event.ItemClickEvent.ItemClickNotifier {
  def addItemClickListener(action: com.vaadin.event.ItemClickEvent => Unit) {
    addListener(new com.vaadin.event.ItemClickEvent.ItemClickListener {
      override def itemClick(event: com.vaadin.event.ItemClickEvent) = action(event)
    })
  }
}

class Table(caption: String = null, width: String = null, height: String = null, dataSource: Container = null, property: com.vaadin.data.Property = null, selectable: Boolean = false, immediate: Boolean = false, style: String = null)
    extends com.vaadin.ui.Table(caption) with ValueChangeFunction with ItemClickListener {
  setWidth(width)
  setHeight(height)
  if (dataSource != null) setContainerDataSource(dataSource)
  if (property != null) setPropertyDataSource(property)
  setSelectable(selectable)
  setImmediate(immediate)
  setStyleName(style)
}

class Tree(caption: String = null, width: String = null, height: String = null, dataSource: Container = null, property: com.vaadin.data.Property = null, selectable: Boolean = false, immediate: Boolean = false, style: String = null)
    extends com.vaadin.ui.Tree with ValueChangeFunction with ItemClickListener {
  setWidth(width)
  setHeight(height)
  if (dataSource != null) setContainerDataSource(dataSource)
  if (property != null) setPropertyDataSource(property)
  setSelectable(selectable)
  setImmediate(immediate)
  setStyleName(style)
}