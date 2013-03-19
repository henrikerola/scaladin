package vaadin.scala.event

import vaadin.scala.internal.{ ListenersTrait, FocusListener }

trait FocusNotifier {

  self: {
    def p: {
      def getListeners(eventType: Class[_]): java.util.Collection[_]
      def addListener(l: com.vaadin.event.FieldEvents.FocusListener)
      def removeListener(l: com.vaadin.event.FieldEvents.FocusListener)
    }
  } =>

  lazy val focusListeners = new ListenersTrait[FocusEvent, FocusListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.FieldEvents.FocusEvent])
    override def addListener(elem: FocusEvent => Unit) = p.addListener(new FocusListener(elem))
    override def removeListener(elem: FocusListener) = p.removeListener(elem)
  }
}