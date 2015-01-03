package vaadin.scala.event

import vaadin.scala.Component

/**
 *
 * @author Henri Kerola / Vaadin
 */
case class SelectionEvent(component: Component, added: Seq[Any], removed: Seq[Any])
  extends ComponentEvent(component)
