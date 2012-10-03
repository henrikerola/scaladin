package vaadin.scala

import vaadin.scala.mixins.GridLayoutMixin
import vaadin.scala.internal.WrapperUtil

package mixins {
  trait GridLayoutMixin extends AbstractLayoutMixin
}

object GridLayout {
  case class Area(component: Component, column1: Int, row1: Int, column2: Int, row2: Int)
}

class GridLayout(override val p: com.vaadin.ui.GridLayout with GridLayoutMixin = new com.vaadin.ui.GridLayout with GridLayoutMixin)
    extends AbstractLayout(p) with SpacingHandler with AlignmentHandler with LayoutClickNotifier {

  def add[C <: Component](component: C = null, col: Int = -1, row: Int = -1, col2: Int = -1, row2: Int = -1, alignment: Alignment.Value = null): C = {
    if (col >= 0 && row >= 0 && col2 >= 0 && row2 >= 0)
      p.addComponent(component.p, col, row, col2, row2)
    else if (col >= 0 && row >= 0)
      p.addComponent(component.p, col, row)
    else
      p.addComponent(component.p)
    if (alignment != null) this.alignment(component, alignment)
    component
  }

  def newLine() = p.newLine()

  def space() = p.space()

  def columns = p.getColumns
  def columns_=(columns: Int) = p.setColumns(columns)

  def rows = p.getRows
  def rows_=(rows: Int) = p.setRows(rows)

  def cursorX = p.getCursorX
  def cursorX_=(cursorX: Int) = p.setCursorX(cursorX)

  def cursorY = p.getCursorY
  def cursorY_=(cursorY: Int) = p.setCursorY(cursorY)

  def insertRow(row: Int) = p.insertRow(row)

  def removeRow(row: Int) = p.removeRow(row)

  def columnExpandRatio(column: Int) = p.getColumnExpandRatio(column)
  def columnExpandRatio(column: Int, ratio: Float) = p.setColumnExpandRatio(column, ratio)

  def rowExpandRatio(row: Int) = p.getRowExpandRatio(row)
  def rowExpandRatio(row: Int, ratio: Float) = p.setRowExpandRatio(row, ratio)

  def component(x: Int, y: Int) = p.getComponent(x, y)

  def componentArea(component: Component): Option[GridLayout.Area] = p.getComponentArea(component.p) match {
    case area: com.vaadin.ui.GridLayout#Area => Some(mapVaadinArea(area))
    case _ => None
  }

  protected def mapVaadinArea(area: com.vaadin.ui.GridLayout#Area): GridLayout.Area =
    GridLayout.Area(WrapperUtil.wrapperFor[Component](area.getComponent).get,
      area.getColumn1, area.getRow1, area.getColumn2, area.getRow2)

}

