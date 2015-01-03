package vaadin.scala.internal

import com.vaadin.event.{ SortEvent => VaadinSortEvent }
import com.vaadin.event.SortEvent.SortListener
import vaadin.scala.{ Component, Grid }
import vaadin.scala.SortDirection
import collection.JavaConverters._
import vaadin.scala.event.SortEvent

/**
 *
 * @author Henri Kerola / Vaadin
 */
class GridSortListener(val action: SortEvent => Unit) extends SortListener with Listener {
  override def sort(event: VaadinSortEvent): Unit = {
    val component = wrapperFor[Component](event.getSource).get
    val sortOrder = event.getSortOrder.asScala.map(so => (so.getPropertyId, SortDirection(so.getDirection.ordinal)))
    action(SortEvent(component, sortOrder, event.isUserOriginated))
  }
}
