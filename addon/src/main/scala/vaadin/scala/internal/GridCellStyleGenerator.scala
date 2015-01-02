package vaadin.scala.internal

import com.vaadin.ui.Grid.CellReference
import vaadin.scala.Grid

/**
 *
 * @author Henri Kerola / Vaadin
 */
class GridCellStyleGenerator(val action: Grid.CellReference => Option[String])
    extends com.vaadin.ui.Grid.CellStyleGenerator with Listener {
  override def getStyle(cellReference: CellReference): String = {
    val grid = wrapperFor[Grid](cellReference.getGrid).get
    val itemId = cellReference.getItemId
    val propertyId = cellReference.getPropertyId
    action(new Grid.CellReference(grid, itemId, propertyId)).orNull
  }
}
