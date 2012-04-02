package vaadin.scala

class NativeSelect(override val p: com.vaadin.ui.NativeSelect = new com.vaadin.ui.NativeSelect) extends AbstractSelect {

  WrapperRegistry.put(this)

  def columns = p.getColumns
  def columns_=(columns: Int) = p.setColumns(columns)
}