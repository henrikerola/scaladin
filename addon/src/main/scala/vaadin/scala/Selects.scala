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
    extends com.vaadin.ui.TwinColSelect with ValueChangeFunction {
  setWidth(width)
  setHeight(height)
  if (dataSource != null) setContainerDataSource(dataSource)
  setStyleName(style)
  setNullSelectionAllowed(nullSelectionAllowed)
}

class Table(caption: String = null, width: String = null, height: String = null, property: com.vaadin.data.Property = null, style: String = null)
    extends com.vaadin.ui.Table with ValueChangeFunction {
  setWidth(width)
  setHeight(height)
  if (property != null) setPropertyDataSource(property)
  setStyleName(style)
}