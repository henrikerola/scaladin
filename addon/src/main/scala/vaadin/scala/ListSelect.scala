package vaadin.scala

class ListSelect(override val p: com.vaadin.ui.ListSelect = new com.vaadin.ui.ListSelect) extends AbstractSelect(p) with MultiSelectable {

  WrapperRegistry.put(this)

  def rows = p.getRows
  def rows_=(rows: Int) = p.setRows(rows)

  def columns = p.getColumns
  def columns_=(columns: Int) = p.setColumns(columns)
}