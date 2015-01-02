package vaadin.scala.internal

import vaadin.scala.Grid
import com.vaadin.ui.Grid.RowReference

/**
 *
 * @author Henri Kerola / Vaadin
 */
class GridRowStyleGenerator(val action: Grid.RowReference => Option[String])
    extends com.vaadin.ui.Grid.RowStyleGenerator with Listener {
  override def getStyle(rowReference: RowReference): String = {
    val grid = wrapperFor[Grid](rowReference.getGrid).get
    val itemId = rowReference.getItemId
    action(new Grid.RowReference(grid, itemId)).orNull
  }
}
