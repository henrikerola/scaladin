package vaadin.scala

class ListSelect(override val p: com.vaadin.ui.ListSelect = new com.vaadin.ui.ListSelect)(implicit wrapper: WrapperRegistry) extends AbstractSelect with MultiSelectable {

  wrapper.put(this)

  def rows = p.getRows
  def rows_=(rows: Int) = p.setRows(rows)

  def columns = p.getColumns
  def columns_=(columns: Int) = p.setColumns(columns)
}