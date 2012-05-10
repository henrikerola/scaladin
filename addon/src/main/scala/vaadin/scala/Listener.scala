package vaadin.scala

import scala.collection.mutable

trait Listener {
  def action: Any

  protected def wrapperFor[T](vaadinComponent: com.vaadin.ui.Component): Option[T] = internal.WrapperUtil.wrapperFor[T](vaadinComponent)
}

trait ListenersTrait[E, L <: Listener] extends mutable.Set[E] {

  import scala.collection.JavaConversions._
  def contains(key: E) = {
    iterator.contains(key)
  }
  def iterator(): Iterator[E] = {
    val list = listeners.map(_.asInstanceOf[L].action)
    list.iterator.asInstanceOf[Iterator[E]]
  }
  def +=(elem: E) = { addListener(elem); this }
  def -=(elem: E) = {
    val list = listeners.foreach { e =>
      if (e.asInstanceOf[L].action == elem) {
        removeListener(e.asInstanceOf[L])
        this
      }
    }
    this
  }

  protected def listeners: java.util.Collection[_]

  protected def addListener(elem: E);

  protected def removeListener(elem: L);
}

case class BlurEvent(component: Component) extends Event
case class FocusEvent(component: Component) extends Event

package listeners {

  class BlurListener(val action: BlurEvent => Unit) extends com.vaadin.event.FieldEvents.BlurListener with Listener {
    def blur(e: com.vaadin.event.FieldEvents.BlurEvent) = action(BlurEvent(wrapperFor[Component](e.getComponent()).get))
  }

  class BlurListeners(p: { def getListeners(eventType: Class[_]): java.util.Collection[_]; def addListener(l: com.vaadin.event.FieldEvents.BlurListener); def removeListener(l: com.vaadin.event.FieldEvents.BlurListener) }) extends ListenersTrait[BlurEvent => Unit, BlurListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.FieldEvents.BlurEvent])
    override def addListener(elem: BlurEvent => Unit) = p.addListener(new BlurListener(elem))
    override def removeListener(elem: BlurListener) = p.removeListener(elem)
  }

  class FocusListener(val action: FocusEvent => Unit) extends com.vaadin.event.FieldEvents.FocusListener with Listener {
    def focus(e: com.vaadin.event.FieldEvents.FocusEvent) = action(FocusEvent(wrapperFor[Component](e.getComponent()).get))
  }

  class FocusListeners(p: { def getListeners(eventType: Class[_]): java.util.Collection[_]; def addListener(l: com.vaadin.event.FieldEvents.FocusListener); def removeListener(l: com.vaadin.event.FieldEvents.FocusListener) }) extends ListenersTrait[FocusEvent => Unit, FocusListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.FieldEvents.FocusEvent])
    override def addListener(elem: FocusEvent => Unit) = p.addListener(new FocusListener(elem))
    override def removeListener(elem: FocusListener) = p.removeListener(elem)
  }
}
