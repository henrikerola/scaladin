package vaadin.scala

import scala.collection.mutable
import vaadin.scala.listeners.ExpandListener
import vaadin.scala.listeners.CollapseListener

trait Listener {
  def action: Any

  protected def wrapperFor[T](vaadinComponent: com.vaadin.ui.Component): Option[T] = internal.WrapperUtil.wrapperFor[T](vaadinComponent)
}

trait ListenersTrait[E, L <: Listener] extends mutable.Set[E => Unit] {

  import scala.collection.JavaConversions._
  def contains(key: E => Unit) = {
    iterator.contains(key)
  }
  def iterator(): Iterator[E => Unit] = {
    val list = listeners.map(_.asInstanceOf[L].action)
    list.iterator.asInstanceOf[Iterator[E => Unit]]
  }
  def +=(elem: => Unit) = { addListener((e: E) => elem); this }
  def +=(elem: E => Unit) = { addListener(elem); this }
  def -=(elem: E => Unit) = {
    val list = listeners.foreach { e =>
      if (e.asInstanceOf[L].action == elem) {
        removeListener(e.asInstanceOf[L])
        this
      }
    }
    this
  }

  protected def listeners: java.util.Collection[_]

  protected def addListener(elem: E => Unit);

  protected def removeListener(elem: L);
}

case class BlurEvent(component: Component) extends Event
case class FocusEvent(component: Component) extends Event

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

package listeners {

  class BlurListener(val action: BlurEvent => Unit) extends com.vaadin.event.FieldEvents.BlurListener with Listener {
    def blur(e: com.vaadin.event.FieldEvents.BlurEvent) = action(BlurEvent(wrapperFor[Component](e.getComponent()).get))
  }

  class BlurListeners(p: { def getListeners(eventType: Class[_]): java.util.Collection[_]; def addListener(l: com.vaadin.event.FieldEvents.BlurListener); def removeListener(l: com.vaadin.event.FieldEvents.BlurListener) }) extends ListenersTrait[BlurEvent, BlurListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.FieldEvents.BlurEvent])
    override def addListener(elem: BlurEvent => Unit) = p.addListener(new BlurListener(elem))
    override def removeListener(elem: BlurListener) = p.removeListener(elem)
  }

  class FocusListener(val action: FocusEvent => Unit) extends com.vaadin.event.FieldEvents.FocusListener with Listener {
    def focus(e: com.vaadin.event.FieldEvents.FocusEvent) = action(FocusEvent(wrapperFor[Component](e.getComponent()).get))
  }

  class FocusListeners(p: { def getListeners(eventType: Class[_]): java.util.Collection[_]; def addListener(l: com.vaadin.event.FieldEvents.FocusListener); def removeListener(l: com.vaadin.event.FieldEvents.FocusListener) }) extends ListenersTrait[FocusEvent, FocusListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.FieldEvents.FocusEvent])
    override def addListener(elem: FocusEvent => Unit) = p.addListener(new FocusListener(elem))
    override def removeListener(elem: FocusListener) = p.removeListener(elem)
  }

  class ExpandListener(val action: ExpandEvent => Unit) extends com.vaadin.ui.Tree.ExpandListener with Listener {
    def nodeExpand(e: com.vaadin.ui.Tree.ExpandEvent) = action(ExpandEvent(wrapperFor[Component](e.getComponent).get, e.getItemId))
  }

  class CollapseListener(val action: CollapseEvent => Unit) extends com.vaadin.ui.Tree.CollapseListener with Listener {
    def nodeCollapse(e: com.vaadin.ui.Tree.CollapseEvent) = action(CollapseEvent(wrapperFor[Component](e.getComponent).get, e.getItemId))
  }
}