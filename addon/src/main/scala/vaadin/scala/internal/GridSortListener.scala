package vaadin.scala.internal

import com.vaadin.event.SortEvent
import com.vaadin.event.SortEvent.SortListener
import vaadin.scala.Grid
import vaadin.scala.Grid.SortDirection
import collection.JavaConverters._

/**
 *
 * @author Henri Kerola / Vaadin
 */
class GridSortListener(val action: Grid.SortEvent => Unit) extends SortListener with Listener {
  override def sort(event: SortEvent): Unit = {
    val grid = wrapperFor[Grid](event.getSource).get
    val sortOrder = event.getSortOrder.asScala.map(so => (so.getPropertyId, SortDirection(so.getDirection.ordinal)))
    action(Grid.SortEvent(grid, sortOrder, event.isUserOriginated))
  }
}
