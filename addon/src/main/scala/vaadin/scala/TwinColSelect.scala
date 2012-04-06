package vaadin.scala

class TwinColSelect(override val p: com.vaadin.ui.TwinColSelect = new com.vaadin.ui.TwinColSelect)(implicit wrapper: WrapperRegistry) extends AbstractSelect with MultiSelectable {

  wrapper.put(this)

  def rows = p.getRows
  def rows_=(rows: Int) = p.setRows(rows)

  def columns = p.getColumns
  def columns_=(columns: Int) = p.setColumns(columns)

  def rightColumnCaption = Option(p.getRightColumnCaption)
  def rightColumnCaption_=(rightColumnCaption: Option[String]) = p.setRightColumnCaption(rightColumnCaption.getOrElse(null))
  def rightColumnCaption_=(rightColumnCaption: String) = p.setRightColumnCaption(rightColumnCaption)

  def leftColumnCaption = Option(p.getLeftColumnCaption)
  def leftColumnCaption_=(leftColumnCaption: Option[String]) = p.setLeftColumnCaption(leftColumnCaption.getOrElse(null))
  def leftColumnCaption_=(leftColumnCaption: String) = p.setLeftColumnCaption(leftColumnCaption)
}