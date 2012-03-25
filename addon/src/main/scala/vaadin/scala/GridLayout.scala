package vaadin.scala

class GridLayout extends AbstractLayout with SpacingHandler with AlignmentHandler {

  val p = new com.vaadin.ui.GridLayout
  WrapperRegistry.put(this)

  def this(width: Option[Measure] = None, height: Option[Measure] = None, margin: Boolean = false, spacing: Boolean = false, caption: String = null, style: String = null, columns: Int = 1, rows: Int = 1) = {
    this()
    this.width = width
    this.height = height
    this.margin = margin
    this.spacing = spacing
    p.setStyleName(style)
    this.caption = caption
    p.setColumns(columns)
    p.setRows(rows)
  }

  def add[C <: Component](component: C = null, col: Int = -1, row: Int = -1, col2: Int = -1, row2: Int = -1, alignment: Alignment.Value = null): C = {
    if (col >= 0 && row >= 0)
      p.addComponent(component.p, col, row)
    else if (col >= 0 && row >= 0 && col2 >= 0 && row2 >= 0)
      p.addComponent(component.p, col, row, col2, row2)
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

  // TODO return wrapped Area
  def componentArea(component: Component) = p.getComponentArea(component.p)
  
  // TODO: LayoutClickListener

}