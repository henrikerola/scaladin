package vaadin.scala

import com.vaadin.shared.data.sort.SortDirection._

/**
 *
 * @author Henri Kerola / Vaadin
 */
object SortDirection extends Enumeration {
  val Ascending = Value(ASCENDING.ordinal)
  val Descending = Value(DESCENDING.ordinal)
}
