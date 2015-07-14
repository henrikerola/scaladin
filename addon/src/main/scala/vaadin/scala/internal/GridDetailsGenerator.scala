package vaadin.scala.internal

import com.vaadin.ui.Grid.RowReference
import vaadin.scala.{ Grid, Component }

/**
 *
 * @author Henri Kerola / Vaadin
 */
class GridDetailsGenerator(val action: Grid.RowReference => Option[Component])
    extends com.vaadin.ui.Grid.DetailsGenerator with Listener {

  override def getDetails(rowReference: RowReference): com.vaadin.ui.Component = {
    val grid = wrapperFor[Grid](rowReference.getGrid).get
    val itemId = rowReference.getItemId
    val detailsComponent = action(new Grid.RowReference(grid, itemId))
    WrapperUtil.peerFor(detailsComponent)
  }

}
