package vaadin.scala.event

import vaadin.scala.internal.LayoutClickListener
import vaadin.scala.internal.ListenersTrait

trait LayoutClickNotifier {

  self: { def p: com.vaadin.ui.AbstractLayout with com.vaadin.event.LayoutEvents.LayoutClickNotifier } =>

  lazy val layoutClickListeners = new ListenersTrait[LayoutClickEvent, LayoutClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.LayoutEvents.LayoutClickEvent])
    override def addListener(elem: LayoutClickEvent => Unit) = p.addLayoutClickListener(new LayoutClickListener(elem))
    override def removeListener(elem: LayoutClickListener) = p.removeLayoutClickListener(elem)
  }
}