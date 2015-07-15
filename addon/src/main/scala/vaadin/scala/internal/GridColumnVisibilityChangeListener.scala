package vaadin.scala.internal

import com.vaadin.ui.{ Grid => VaadinGrid }
import vaadin.scala.{ Component, Grid }
import vaadin.scala.Grid.Column

/**
 *
 * @author Henri Kerola / Vaadin
 */
class GridColumnVisibilityChangeListener(val action: Grid.ColumnVisibilityChangeEvent => Unit)
    extends VaadinGrid.ColumnVisibilityChangeListener with Listener {

  override def columnVisibilityChanged(event: VaadinGrid.ColumnVisibilityChangeEvent): Unit = {
    val grid = wrapperFor[Grid](event.getComponent).get
    val column = new Column(event.getColumn)
    val userOriginated = event.isUserOriginated
    val hidden = event.isHidden

    action(new Grid.ColumnVisibilityChangeEvent(grid, column, userOriginated, hidden))
  }

}
