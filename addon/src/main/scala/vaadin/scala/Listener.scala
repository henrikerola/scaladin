package vaadin.scala

import scala.collection.mutable

trait Listener { def action: Any }

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