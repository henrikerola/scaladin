package vaadin.scala.internal

import scala.collection.mutable
import scala.util.Try

trait HandlersTrait[E, H <: Handler[E, R], R] extends mutable.Set[E => R] {

  type Event = E
  type Result = R
  type HandlerSubtype = H

  private val internalSet: mutable.Set[HandlerSubtype] = mutable.Set.empty

  def iterator = internalSet map (_.action) iterator

  def contains(elem: Event => Result) = internalSet.exists(h => h.action == elem)

  def +=(elem: Event => Result) = {
    val listener = createListener(elem)
    addHandler(listener)
    internalSet += listener
    this
  }

  def -=(elem: Event => Result) = {
    val toRemove = internalSet filter (e => e.action == elem)
    toRemove foreach { elementToRemove =>
      removeHandler(elementToRemove)
      internalSet -= elementToRemove
    }
    this
  }

  protected def addHandler(elem: HandlerSubtype)

  protected def removeHandler(elem: HandlerSubtype)

  protected def createListener(e: Event => Result): HandlerSubtype
}