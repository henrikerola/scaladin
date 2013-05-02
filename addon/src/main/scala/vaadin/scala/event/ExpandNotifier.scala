package vaadin.scala.event

import vaadin.scala.internal.{ ExpandListener, ListenersTrait }

trait ExpandNotifier {

  self: {
    def p: {
      def getListeners(eventType: Class[_]): java.util.Collection[_]
      def addListener(listener: com.vaadin.ui.Tree.ExpandListener)
      def removeListener(listener: com.vaadin.ui.Tree.ExpandListener)
    }
  } =>

  lazy val expandListeners = new ListenersTrait[ExpandEvent, ExpandListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Tree.ExpandEvent])
    override def addListener(elem: ExpandEvent => Unit) = p.addListener(new ExpandListener(elem))
    override def removeListener(elem: ExpandListener) = p.removeListener(elem)
  }
}