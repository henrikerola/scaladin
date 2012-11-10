package vaadin.scala

import vaadin.scala.mixins.TwinColSelectMixin

package mixins {
  trait TwinColSelectMixin extends AbstractSelectMixin { self: com.vaadin.ui.TwinColSelect => }
}

class TwinColSelect(override val p: com.vaadin.ui.TwinColSelect with TwinColSelectMixin = new com.vaadin.ui.TwinColSelect with TwinColSelectMixin) extends AbstractSelect(p) with MultiSelectable {

  def rows = p.getRows
  def rows_=(rows: Int) = p.setRows(rows)

  def columns = p.getColumns
  def columns_=(columns: Int) = p.setColumns(columns)

  def rightColumnCaption = Option(p.getRightColumnCaption)
  def rightColumnCaption_=(rightColumnCaption: Option[String]) = p.setRightColumnCaption(rightColumnCaption.orNull)
  def rightColumnCaption_=(rightColumnCaption: String) = p.setRightColumnCaption(rightColumnCaption)

  def leftColumnCaption = Option(p.getLeftColumnCaption)
  def leftColumnCaption_=(leftColumnCaption: Option[String]) = p.setLeftColumnCaption(leftColumnCaption.orNull)
  def leftColumnCaption_=(leftColumnCaption: String) = p.setLeftColumnCaption(leftColumnCaption)
}