package vaadin.scala

import com.vaadin.terminal.Resource
import com.vaadin.ui.themes.BaseTheme
import com.vaadin.ui._
import com.vaadin.data.Property

class ButtonClickListener(action: com.vaadin.ui.Button#ClickEvent => Unit) extends com.vaadin.ui.Button.ClickListener {
  def buttonClick(event: com.vaadin.ui.Button#ClickEvent) { action(event) }
}

class Button(caption: String = null, action: com.vaadin.ui.Button#ClickEvent => Unit = null, icon: Resource = null, style: String = null)
    extends com.vaadin.ui.Button(caption) {
  setIcon(icon)
  setStyleName(style)
  if (action != null) addListener(new ButtonClickListener(action))
}

class LinkButton(caption: String = null, action: com.vaadin.ui.Button#ClickEvent => Unit = null, icon: Resource = null, style: String = null)
    extends Button(caption, action, icon) {
  setStyleName(BaseTheme.BUTTON_LINK)
  addStyleName(style);
}

class CheckBox(caption: String = null, immediate: Boolean = false, action: com.vaadin.ui.Button#ClickEvent => Unit = null, icon: Resource = null, style: String = null)
    extends com.vaadin.ui.CheckBox(caption) {
  setImmediate(immediate)
  setIcon(icon)
  setStyleName(style)
  if (action != null) addListener(new ButtonClickListener(action))
}

// icon, caption as constructor parameters?
class Label(content: String = null, width: String = 100 percent, height: String = null, property: Property = null, contentMode: Integer = com.vaadin.ui.Label.CONTENT_DEFAULT, style: String = null)
    extends com.vaadin.ui.Label(content, contentMode) {
  setWidth(width)
  setHeight(height)
  if (property != null) setPropertyDataSource(property)
  setStyleName(style)
}

class HtmlLabel(content: String = null, width: String = 100 percent, height: String = null, property: Property = null, style: String = null)
  extends Label(width = width, height = height, content = content, property = property, contentMode = com.vaadin.ui.Label.CONTENT_XHTML, style = style)