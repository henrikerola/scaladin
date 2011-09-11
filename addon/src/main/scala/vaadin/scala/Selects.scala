package vaadin.scala

class NativeSelect(caption: String = null, width: String = null, height: String = null, style: String = null, nullSelectionAllowed: Boolean = true)
    extends com.vaadin.ui.NativeSelect {
  setWidth(width)
  setHeight(height)
  setStyleName(style)
  setNullSelectionAllowed(nullSelectionAllowed)

  def addListener(action: com.vaadin.data.Property.ValueChangeEvent => Unit): Unit = addListener(new PropertyValueChangeListener(action))
}

class PropertyValueChangeListener(action: com.vaadin.data.Property.ValueChangeEvent => Unit)
    extends com.vaadin.data.Property.ValueChangeListener {
  override def valueChange(event: com.vaadin.data.Property.ValueChangeEvent) = action(event)
}

class Table(caption: String = null, width: String = null, height: String = null, property: com.vaadin.data.Property = null, style: String = null)
    extends com.vaadin.ui.Table {
  setWidth(width)
  setHeight(height)
  if (property != null) setPropertyDataSource(property)
  setStyleName(style)

  def addListener(action: com.vaadin.data.Property.ValueChangeEvent => Unit): Unit = addListener(new PropertyValueChangeListener(action))
}

