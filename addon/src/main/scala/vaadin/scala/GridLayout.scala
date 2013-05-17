package vaadin.scala

import event.LayoutClickNotifier
import vaadin.scala.mixins.GridLayoutMixin
import vaadin.scala.Layout._

package mixins {
  trait GridLayoutMixin extends AbstractLayoutMixin { self: com.vaadin.ui.GridLayout => }
}

object GridLayout {
  case class Area(component: Component, column1: Int, row1: Int, column2: Int, row2: Int)
}

class GridLayout(
  override val p: com.vaadin.ui.GridLayout with GridLayoutMixin = new com.vaadin.ui.GridLayout with GridLayoutMixin)
    extends AbstractLayout(p) with SpacingHandler with AlignmentHandler with MarginHandler with LayoutClickNotifier {

  def add[C <: Component](component: C = null,
                          col: Int = -1,
                          row: Int = -1,
                          col2: Int = -1,
                          row2: Int = -1,
                          alignment: Alignment.Value = null): C = {

    if (col >= 0 && row >= 0 && col2 >= 0 && row2 >= 0)
      p.addComponent(component.p, col, row, col2, row2)
    else if (col >= 0 && row >= 0)
      p.addComponent(component.p, col, row)
    else
      p.addComponent(component.p)

    if (alignment != null) {
      this.setAlignment(component, alignment)
    }

    component
  }

  def newLine() { p.newLine() }

  def space() { p.space() }

  def columns: Int = p.getColumns
  def columns_=(columns: Int) { p.setColumns(columns) }

  def rows: Int = p.getRows
  def rows_=(rows: Int) { p.setRows(rows) }

  def cursorX: Int = p.getCursorX
  def cursorX_=(cursorX: Int) { p.setCursorX(cursorX) }

  def cursorY: Int = p.getCursorY
  def cursorY_=(cursorY: Int) { p.setCursorY(cursorY) }

  def insertRow(row: Int) { p.insertRow(row) }

  def removeRow(row: Int) { p.removeRow(row) }

  def getColumnExpandRatio(column: Int): Float = p.getColumnExpandRatio(column)
  def setColumnExpandRatio(column: Int, ratio: Float) { p.setColumnExpandRatio(column, ratio) }

  def getRowExpandRatio(row: Int): Float = p.getRowExpandRatio(row)
  def setRowExpandRatio(row: Int, ratio: Float) { p.setRowExpandRatio(row, ratio) }

  def getComponent(x: Int, y: Int): Option[Component] = Option(p.getComponent(x, y)) map { wrapperFor(_).get }

  def getComponentArea(component: Component): Option[GridLayout.Area] = p.getComponentArea(component.p) match {
    case area: com.vaadin.ui.GridLayout#Area => Some(mapVaadinArea(area))
    case _ => None
  }

  protected def mapVaadinArea(area: com.vaadin.ui.GridLayout#Area): GridLayout.Area =
    GridLayout.Area(wrapperFor(area.getComponent).get, area.getColumn1, area.getRow1, area.getColumn2, area.getRow2)

}

