package vaadin.scala

import com.vaadin.ui.themes.BaseTheme
import com.vaadin.ui._

class CheckBox(caption: String = null, checked: Boolean = false, immediate: Boolean = false, action: com.vaadin.ui.Button#ClickEvent => Unit = null, icon: Resource = null, style: String = null, enabled: Boolean = true, description: String = null)
  extends com.vaadin.ui.CheckBox(caption, checked) {
  setImmediate(immediate)
  if (icon == null) setIcon(null) else setIcon(icon.p)
  setStyleName(style)
  setEnabled(enabled)
  setDescription(description)

  // FIXME
  //if (action != null) addListener(action)

  // FIXME
  //def addListener(action: com.vaadin.ui.Button#ClickEvent => Unit): Unit = addListener(new ButtonClickListener(action))
}

class TextArea(caption: String = null, width: Option[Measure] = None, height: Option[Measure] = None, property: com.vaadin.data.Property = null, value: Any = null, style: String = null, prompt: String = null)
  extends com.vaadin.ui.TextArea(caption) {
  setWidth(if (width.isDefined) width.get.toString else null)
  setHeight(if (height.isDefined) height.get.toString else null)
  if (property != null) setPropertyDataSource(property)
  if (value != null) setValue(value)
  setStyleName(style)
  setInputPrompt(prompt)
}

class RichTextArea(caption: String = null, width: Option[Measure] = None, height: Option[Measure] = None, property: com.vaadin.data.Property = null, value: Any = null, style: String = null)
  extends com.vaadin.ui.RichTextArea(caption) {
  setWidth(if (width.isDefined) width.get.toString else null)
  setHeight(if (height.isDefined) height.get.toString else null)
  if (property != null) setPropertyDataSource(property)
  if (value != null) setValue(value)
  setStyleName(style)
}

class PopupDateField(caption: String = null, width: Option[Measure] = None, height: Option[Measure] = None, property: com.vaadin.data.Property = null, style: String = null, value: Any = null, resolution: Int = com.vaadin.ui.DateField.RESOLUTION_MSEC, prompt: String = null)
  extends com.vaadin.ui.PopupDateField(caption) {
  setWidth(if (width.isDefined) width.get.toString else null)
  setHeight(if (height.isDefined) height.get.toString else null)
  if (property != null) setPropertyDataSource(property)
  setStyleName(style)
  setResolution(resolution)
  if (value != null) setValue(value)
  setInputPrompt(prompt)
}

class InlineDateField(caption: String = null, width: Option[Measure] = None, height: Option[Measure] = None, property: com.vaadin.data.Property = null, style: String = null, value: Any = null, resolution: Int = com.vaadin.ui.DateField.RESOLUTION_MSEC)
  extends com.vaadin.ui.InlineDateField(caption) {
  setWidth(if (width.isDefined) width.get.toString else null)
  setHeight(if (height.isDefined) height.get.toString else null)
  if (property != null) setPropertyDataSource(property)
  setStyleName(style)
  setResolution(resolution)
  if (value != null) setValue(value)
}

class MenuBar(width: Option[Measure] = None, height: Option[Measure] = None, style: String = null)
  extends com.vaadin.ui.MenuBar {
  setWidth(if (width.isDefined) width.get.toString else null)
  setHeight(if (height.isDefined) height.get.toString else null)
  setStyleName(style)
}

class MenuBarCommand(action: com.vaadin.ui.MenuBar#MenuItem => Unit) extends com.vaadin.ui.MenuBar.Command {
  override def menuSelected(selectedItem: com.vaadin.ui.MenuBar#MenuItem) = {
    action(selectedItem)
  }
}

class Form(width: Option[Measure] = None, height: Option[Measure] = 100 percent, item: com.vaadin.data.Item = null, layout: com.vaadin.ui.Layout = null, fieldFactory: FormFieldFactory = DefaultFieldFactory.get())
  extends com.vaadin.ui.Form(layout, fieldFactory) {
  setWidth(if (width.isDefined) width.get.toString else null)
  setHeight(if (height.isDefined) height.get.toString else null)
  if (item != null) setItemDataSource(item)
}