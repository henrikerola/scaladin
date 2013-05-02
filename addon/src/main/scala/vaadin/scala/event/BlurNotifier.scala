package vaadin.scala.event

import vaadin.scala.internal.{ BlurListener, ListenersTrait }

trait BlurNotifier {

  self: {
    def p: {
      def getListeners(eventType: Class[_]): java.util.Collection[_]
      def addListener(l: com.vaadin.event.FieldEvents.BlurListener)
      def removeListener(l: com.vaadin.event.FieldEvents.BlurListener)
    }
  } =>

  lazy val blurListeners = new ListenersTrait[BlurEvent, BlurListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.FieldEvents.BlurEvent])
    override def addListener(elem: BlurEvent => Unit) = p.addListener(new BlurListener(elem))
    override def removeListener(elem: BlurListener) = p.removeListener(elem)
  }
}
