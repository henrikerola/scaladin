package vaadin.scala

class NativeSelect(override val p: com.vaadin.ui.NativeSelect = new com.vaadin.ui.NativeSelect)(implicit wrapper: WrapperRegistry) extends AbstractSelect {

  wrapper.put(this)

  def columns = p.getColumns
  def columns_=(columns: Int) = p.setColumns(columns)
}