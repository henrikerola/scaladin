package vaadin.scala

import scala.collection.mutable
import vaadin.scala.internal.BlurListener
import vaadin.scala.internal.FocusListener
import vaadin.scala.internal.ExpandListener
import vaadin.scala.internal.CollapseListener
import vaadin.scala.internal.ValueChangeListener
import vaadin.scala.internal.ListenersTrait

case class BlurEvent(component: Component) extends Event

trait BlurNotifier { self: { def p: { def getListeners(eventType: Class[_]): java.util.Collection[_]; def addListener(l: com.vaadin.event.FieldEvents.BlurListener); def removeListener(l: com.vaadin.event.FieldEvents.BlurListener) } } =>
  lazy val blurListeners = new ListenersTrait[BlurEvent, BlurListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.FieldEvents.BlurEvent])
    override def addListener(elem: BlurEvent => Unit) = p.addListener(new BlurListener(elem))
    override def removeListener(elem: BlurListener) = p.removeListener(elem)
  }

}

case class FocusEvent(component: Component) extends Event

trait FocusNotifier { self: { def p: { def getListeners(eventType: Class[_]): java.util.Collection[_]; def addListener(l: com.vaadin.event.FieldEvents.FocusListener); def removeListener(l: com.vaadin.event.FieldEvents.FocusListener) } } =>
  lazy val focusListeners = new ListenersTrait[FocusEvent, FocusListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.FieldEvents.FocusEvent])
    override def addListener(elem: FocusEvent => Unit) = p.addListener(new FocusListener(elem))
    override def removeListener(elem: FocusListener) = p.removeListener(elem)
  }

}

case class ExpandEvent(component: Component, itemId: Any) extends Event

trait ExpandNotifier { self: { def p: { def getListeners(eventType: Class[_]): java.util.Collection[_]; def addListener(listener: com.vaadin.ui.Tree.ExpandListener); def removeListener(listener: com.vaadin.ui.Tree.ExpandListener); } } =>
  lazy val expandListeners = new ListenersTrait[ExpandEvent, ExpandListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Tree.ExpandEvent])
    override def addListener(elem: ExpandEvent => Unit) = p.addListener(new ExpandListener(elem))
    override def removeListener(elem: ExpandListener) = p.removeListener(elem)
  }
}

case class CollapseEvent(component: Component, itemId: Any) extends Event

trait CollapseNotifier { self: { def p: { def getListeners(eventType: Class[_]): java.util.Collection[_]; def addListener(listener: com.vaadin.ui.Tree.CollapseListener); def removeListener(listener: com.vaadin.ui.Tree.CollapseListener); } } =>
  lazy val collapseListeners = new ListenersTrait[CollapseEvent, CollapseListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Tree.CollapseEvent])
    override def addListener(elem: CollapseEvent => Unit) = p.addListener(new CollapseListener(elem))
    override def removeListener(elem: CollapseListener) = p.removeListener(elem)
  }
}

case class ValueChangeEvent(property: Property[_]) extends Event

trait ValueChangeNotifier { self: { def p: { def getListeners(eventType: Class[_]): java.util.Collection[_]; def addListener(l: com.vaadin.data.Property.ValueChangeListener); def removeListener(l: com.vaadin.data.Property.ValueChangeListener) } } =>

  lazy val valueChangeListeners = new ListenersTrait[ValueChangeEvent, ValueChangeListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.data.Property.ValueChangeEvent])
    override def addListener(elem: ValueChangeEvent => Unit) = p.addListener(new ValueChangeListener(elem))
    override def removeListener(elem: ValueChangeListener) = p.removeListener(elem)
  }
}