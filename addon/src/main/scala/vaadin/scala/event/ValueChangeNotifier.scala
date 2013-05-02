package vaadin.scala.event

import vaadin.scala.internal._

trait ValueChangeNotifier {

  self: {
    def p: {
      def getListeners(eventType: Class[_]): java.util.Collection[_]
      def addListener(l: com.vaadin.data.Property.ValueChangeListener)
      def removeListener(l: com.vaadin.data.Property.ValueChangeListener)
    }
  } =>

  lazy val valueChangeListeners = new ListenersTrait[ValueChangeEvent, ValueChangeListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.data.Property.ValueChangeEvent])
    override def addListener(elem: ValueChangeEvent => Unit) = p.addListener(new ValueChangeListener(elem))
    override def removeListener(elem: ValueChangeListener) = p.removeListener(elem)
  }
}

