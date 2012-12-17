package vaadin.scala.internal

import scala.collection.mutable
import scala.util.Try

trait HandlersTrait[E, H <: Handler[E]] extends mutable.Set[E => Try[Unit]] {

  private val internalSet: mutable.Set[H] = mutable.Set.empty

  def iterator = internalSet map (_.action) iterator

  def contains(elem: E => Try[Unit]) = internalSet.exists(h => h.action == elem)

  def +=(elem: E => Try[Unit]) = {
    val listener = createListener(elem)
    addHandler(listener)
    internalSet += listener
    this
  }

  def -=(elem: E => Try[Unit]) = {
    val toRemove = internalSet filter (e => e.action == elem)
    toRemove foreach {
      removeHandler(_)
      internalSet -= _
    }
    this
  }

  protected def addHandler(elem: H)

  protected def removeHandler(elem: H)

  protected def createListener(e: E => Try[Unit]): H
}