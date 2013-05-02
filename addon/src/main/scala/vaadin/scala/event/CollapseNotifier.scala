package vaadin.scala.event

import vaadin.scala.internal.{ CollapseListener, ListenersTrait }

trait CollapseNotifier {

  self: {
    def p: {
      def getListeners(eventType: Class[_]): java.util.Collection[_]
      def addListener(listener: com.vaadin.ui.Tree.CollapseListener)
      def removeListener(listener: com.vaadin.ui.Tree.CollapseListener)
    }
  } =>

  lazy val collapseListeners = new ListenersTrait[CollapseEvent, CollapseListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Tree.CollapseEvent])
    override def addListener(elem: CollapseEvent => Unit) = p.addListener(new CollapseListener(elem))
    override def removeListener(elem: CollapseListener) = p.removeListener(elem)
  }
}
