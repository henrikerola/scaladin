package vaadin.scala.event

import vaadin.scala.internal.ItemClickListener
import vaadin.scala.internal.ListenersTrait

trait ItemClickNotifier {

  self: {
    def p: {
      def getListeners(eventType: Class[_]): java.util.Collection[_]
      def addListener(listener: com.vaadin.event.ItemClickEvent.ItemClickListener)
      def removeListener(listener: com.vaadin.event.ItemClickEvent.ItemClickListener)
    }
  } =>

  lazy val itemClickListeners = new ListenersTrait[ItemClickEvent, ItemClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.ItemClickEvent.ItemClickListener])
    override def addListener(elem: ItemClickEvent => Unit) = p.addListener(new ItemClickListener(elem))
    override def removeListener(elem: ItemClickListener) = p.removeListener(elem)
  }
}