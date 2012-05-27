package vaadin.scala

import vaadin.scala.listeners.LayoutClickListener

case class LayoutClickEvent(component: Component, clickedComponent: Component, childComponent: Component, button: Int, clientX: Int, clientY: Int, relativeX: Int, relativeY: Int, doubleClick: Boolean, altKey: Boolean, ctrlKey: Boolean, metaKey: Boolean, shiftKey: Boolean) extends AbstractClickEvent(component, button, clientX, clientY, relativeX, relativeY, doubleClick, altKey, ctrlKey, metaKey, shiftKey)

package listeners {
  class LayoutClickListener(val action: LayoutClickEvent => Unit) extends com.vaadin.event.LayoutEvents.LayoutClickListener with Listener {
    def layoutClick(e: com.vaadin.event.LayoutEvents.LayoutClickEvent) = action(LayoutClickEvent(wrapperFor[Component](e.getComponent).get, wrapperFor[Component](e.getClickedComponent).get, wrapperFor[Component](e.getChildComponent).get, e.getButton, e.getClientX, e.getClientY, e.getRelativeX, e.getRelativeY, e.isDoubleClick, e.isAltKey, e.isCtrlKey, e.isMetaKey, e.isShiftKey))
  }
}

trait LayoutClickNotifier { self: { def p: com.vaadin.ui.AbstractLayout with com.vaadin.event.LayoutEvents.LayoutClickNotifier; } =>
  lazy val layoutClickListeners = new ListenersTrait[LayoutClickEvent, LayoutClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.LayoutEvents.LayoutClickEvent])
    override def addListener(elem: LayoutClickEvent => Unit) = p.addListener(new LayoutClickListener(elem))
    override def removeListener(elem: LayoutClickListener) = p.removeListener(elem)
  }
}