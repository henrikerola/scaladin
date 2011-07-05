package vaadin.scala

import com.vaadin.terminal.Resource
import com.vaadin.ui.themes.BaseTheme
import com.vaadin.ui._
import com.vaadin.data.Property

class ButtonClickListener(action: com.vaadin.ui.Button#ClickEvent => Unit) extends com.vaadin.ui.Button.ClickListener {
  def buttonClick(event: com.vaadin.ui.Button#ClickEvent) { action(event) }
}

class Button(caption: String = null, action: com.vaadin.ui.Button#ClickEvent => Unit = null, icon: Resource = null, style: String = null) extends com.vaadin.ui.Button(caption, new ButtonClickListener(action)) {
  setIcon(icon)
  setStyleName(style)
}

class LinkButton(caption: String = null, action: com.vaadin.ui.Button#ClickEvent => Unit = null, icon: Resource = null, style: String = null) extends Button(caption, action, icon) {
  setStyleName(BaseTheme.BUTTON_LINK)
  addStyleName(style);
}

class CheckBox(caption: String = null, immediate: Boolean = false, action: com.vaadin.ui.Button#ClickEvent => Unit = null, icon: Resource = null, style: String = null) extends com.vaadin.ui.CheckBox(caption, new ButtonClickListener(action)) {
  setImmediate(immediate)
  setIcon(icon)
  setStyleName(style)
}

// icon, caption as constructor parameters?
class Label(width: String = 100 percent, height: String = null, content: String = null, property: Property = null, contentMode: Integer = com.vaadin.ui.Label.CONTENT_DEFAULT, style: String = null) extends com.vaadin.ui.Label(content, contentMode) {
  setWidth(width)
  setHeight(height)
  if (property != null) setPropertyDataSource(property)
  setStyleName(style)
}

class HtmlLabel(width: String = 100 percent, height: String = null, content: String = null, property: Property = null, style: String = null) extends Label(width, height, content, property, com.vaadin.ui.Label.CONTENT_XHTML, style)