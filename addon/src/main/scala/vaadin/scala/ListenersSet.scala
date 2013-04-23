package vaadin.scala

import collection.mutable

/**
 * @author Henri Kerola / Vaadin
 */
// TODO: better name for this trait
trait ListenersSet[E] extends mutable.Set[E] {
  def +=(elem: => Unit): this.type
}
