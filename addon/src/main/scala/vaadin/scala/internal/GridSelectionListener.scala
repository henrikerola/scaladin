package vaadin.scala.internal

import com.vaadin.event.SelectionEvent
import com.vaadin.event.SelectionEvent.SelectionListener
import vaadin.scala.Grid

/**
 *
 * @author Henri Kerola / Vaadin
 */
class GridSelectionListener(val action: Grid.SelectionEvent => Unit)
    extends SelectionListener with Listener {
  override def select(event: SelectionEvent): Unit = {
    val grid = wrapperFor[Grid](event.getSource).get
    val added = event.getAdded.toArray
    val removed = event.getRemoved.toArray
    action(Grid.SelectionEvent(grid, added, removed))
  }
}
