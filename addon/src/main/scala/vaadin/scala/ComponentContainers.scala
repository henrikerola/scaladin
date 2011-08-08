package vaadin.scala

import com.vaadin.terminal.Resource
import com.vaadin.ui.ComponentContainer

class WindowCloseListener(action: com.vaadin.ui.Window#CloseEvent => Unit) extends com.vaadin.ui.Window.CloseListener {
  def windowClose(event: com.vaadin.ui.Window#CloseEvent) { action(event) }
}

class Window(caption: String = null, width: String = null, height: String = null, content: ComponentContainer = null, modal: Boolean = false, icon: Resource = null, style: String = null) extends com.vaadin.ui.Window(caption, content) {
  setWidth(width);
  setHeight(height);
  setModal(modal);
  setIcon(icon)
  setStyleName(style)
}