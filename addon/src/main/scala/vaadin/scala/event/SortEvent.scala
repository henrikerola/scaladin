package vaadin.scala.event

import vaadin.scala.Component
import vaadin.scala.SortDirection

/**
 *
 * @author Henri Kerola / Vaadin
 */
case class SortEvent(component: Component, sortOrder: Seq[(Any, SortDirection.Value)], userOriginated: Boolean)
  extends ComponentEvent(component)
