package vaadin.scala

import com.vaadin.terminal.Resource
import com.vaadin.ui.themes.BaseTheme
import com.vaadin.ui._

class ButtonClickListener(action: com.vaadin.ui.Button#ClickEvent => Unit) extends com.vaadin.ui.Button.ClickListener {
  def buttonClick(event: com.vaadin.ui.Button#ClickEvent) = action(event)
}

class Button(caption: String = null, action: com.vaadin.ui.Button#ClickEvent => Unit = null, icon: Resource = null, style: String = null, enabled: Boolean = true)
  extends com.vaadin.ui.Button(caption) {
  setIcon(icon)
  setStyleName(style)
  setEnabled(enabled)

  if (action != null) addListener(action)

  def addListener(action: com.vaadin.ui.Button#ClickEvent => Unit): Unit = addListener(new ButtonClickListener(action))
}

class LinkButton(caption: String = null, action: com.vaadin.ui.Button#ClickEvent => Unit = null, icon: Resource = null, style: String = null)
  extends Button(caption, action, icon) {
  setStyleName(BaseTheme.BUTTON_LINK)
  addStyleName(style)
}

class CheckBox(caption: String = null, checked: Boolean = false, immediate: Boolean = false, action: com.vaadin.ui.Button#ClickEvent => Unit = null, icon: Resource = null, style: String = null, enabled: Boolean = true, description: String = null)
  extends com.vaadin.ui.CheckBox(caption, checked) {
  setImmediate(immediate)
  setIcon(icon)
  setStyleName(style)
  setEnabled(enabled)
  setDescription(description)

  if (action != null) addListener(action)

  def addListener(action: com.vaadin.ui.Button#ClickEvent => Unit): Unit = addListener(new ButtonClickListener(action))
}

class Link(caption: String = null, resource: Resource = null, targetName: String = null, width: Int = -1, height: Int = -1, border: Int = com.vaadin.ui.Link.TARGET_BORDER_DEFAULT)
  extends com.vaadin.ui.Link(caption, resource, targetName, width, height, border)

// icon, caption as constructor parameters?
class Label(content: String = null, width: String = 100 percent, height: String = null, property: com.vaadin.data.Property = null, contentMode: Int = com.vaadin.ui.Label.CONTENT_DEFAULT, style: String = null)
  extends com.vaadin.ui.Label(content, contentMode) {
  setWidth(width)
  setHeight(height)
  if (property != null) setPropertyDataSource(property)
  setStyleName(style)
}

class HtmlLabel(content: String = null, width: String = 100 percent, height: String = null, property: com.vaadin.data.Property = null, style: String = null)
  extends Label(width = width, height = height, content = content, property = property, contentMode = com.vaadin.ui.Label.CONTENT_XHTML, style = style)

class TextField(caption: String = null, width: String = null, height: String = null, property: com.vaadin.data.Property = null, value: Any = null, style: String = null, prompt: String = null)
  extends com.vaadin.ui.TextField(caption) {
  setWidth(width)
  setHeight(height)
  if (property != null) setPropertyDataSource(property)
  if (value != null) setValue(value)
  setStyleName(style)
  setInputPrompt(prompt)
}

class TextArea(caption: String = null, width: String = null, height: String = null, property: com.vaadin.data.Property = null, value: Any = null, style: String = null, prompt: String = null)
  extends com.vaadin.ui.TextArea(caption) {
  setWidth(width)
  setHeight(height)
  if (property != null) setPropertyDataSource(property)
  if (value != null) setValue(value)
  setStyleName(style)
  setInputPrompt(prompt)
}

class RichTextArea(caption: String = null, width: String = null, height: String = null, property: com.vaadin.data.Property = null, value: Any = null, style: String = null)
  extends com.vaadin.ui.RichTextArea(caption) {
  setWidth(width)
  setHeight(height)
  if (property != null) setPropertyDataSource(property)
  if (value != null) setValue(value)
  setStyleName(style)
}

class PopupDateField(caption: String = null, width: String = null, height: String = null, property: com.vaadin.data.Property = null, style: String = null, value: Any = null, resolution: Int = com.vaadin.ui.DateField.RESOLUTION_MSEC, prompt: String = null)
  extends com.vaadin.ui.PopupDateField(caption) {
  setWidth(width)
  setHeight(height)
  if (property != null) setPropertyDataSource(property)
  setStyleName(style)
  setResolution(resolution)
  if (value != null) setValue(value)
  setInputPrompt(prompt)
}

class InlineDateField(caption: String = null, width: String = null, height: String = null, property: com.vaadin.data.Property = null, style: String = null, value: Any = null, resolution: Int = com.vaadin.ui.DateField.RESOLUTION_MSEC)
  extends com.vaadin.ui.InlineDateField(caption) {
  setWidth(width)
  setHeight(height)
  if (property != null) setPropertyDataSource(property)
  setStyleName(style)
  setResolution(resolution)
  if (value != null) setValue(value)
}

class MenuBar(width: String = null, height: String = null, style: String = null)
  extends com.vaadin.ui.MenuBar {
  setWidth(width)
  setHeight(height)
  setStyleName(style)
}

class MenuBarCommand(action: com.vaadin.ui.MenuBar#MenuItem => Unit) extends com.vaadin.ui.MenuBar.Command {
  override def menuSelected(selectedItem: com.vaadin.ui.MenuBar#MenuItem) = {
    action(selectedItem)
  }
}

class Form(width: String = null, height: String = 100 percent, item: com.vaadin.data.Item = null, layout: Layout = null, fieldFactory: FormFieldFactory = DefaultFieldFactory.get())
  extends com.vaadin.ui.Form(layout, fieldFactory) {
  setWidth(width)
  setHeight(height)
  if (item != null) setItemDataSource(item)
}

class Embedded(caption: String = null, width: String = null, height: String = null, source: Resource = null, objectType: Int = com.vaadin.ui.Embedded.TYPE_OBJECT, style: String = null)
  extends com.vaadin.ui.Embedded(caption) {
  setWidth(width)
  setHeight(height)
  if (source != null) setSource(source)
  setType(objectType)
  setStyleName(style)
}

class HorizontalSlider(caption: String = null, width: String = null, height: String = null, property: com.vaadin.data.Property = null, value: Any = null, min: Double = 0, max: Double = 100, resolution: Int = 0, style: String = null)
  extends com.vaadin.ui.Slider(caption) {
  setOrientation(com.vaadin.ui.Slider.ORIENTATION_HORIZONTAL)
  setWidth(width)
  setHeight(height)
  if (property != null) setPropertyDataSource(property)
  if (value != null) setValue(value)
  setMin(min)
  setMax(max)
  setResolution(resolution)
  setStyleName(style)
}

class VerticalSlider(caption: String = null, width: String = null, height: String = null, property: com.vaadin.data.Property = null, value: Any = null, min: Double = 0, max: Double = 100, resolution: Int = 0, style: String = null)
  extends com.vaadin.ui.Slider(caption) {
  setOrientation(com.vaadin.ui.Slider.ORIENTATION_VERTICAL)
  setWidth(width)
  setHeight(height)
  if (property != null) setPropertyDataSource(property)
  if (value != null) setValue(value)
  setMin(min)
  setMax(max)
  setResolution(resolution)
  setStyleName(style)
} 