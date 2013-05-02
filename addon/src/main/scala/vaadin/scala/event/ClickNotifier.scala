package vaadin.scala.event

import vaadin.scala.ListenersSet
import vaadin.scala.internal.{ ClickListener, ListenersTrait }

/**
 * @author Henri Kerola / Vaadin
 */
trait ClickNotifier {

  self: {
    def p: {
      def getListeners(eventType: Class[_]): java.util.Collection[_]
      def addClickListener(listener: com.vaadin.event.MouseEvents.ClickListener)
      def removeClickListener(listener: com.vaadin.event.MouseEvents.ClickListener)
    }
  } =>

  lazy val clickListeners: ListenersSet[ClickEvent => Unit] = new ListenersTrait[ClickEvent, ClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.MouseEvents.ClickListener])
    override def addListener(elem: ClickEvent => Unit) = p.addClickListener(new ClickListener(elem))
    override def removeListener(elem: ClickListener) = p.removeClickListener(elem)
  }
}
