package vaadin.scala

import vaadin.scala.mixins.TwinColSelectMixin

package mixins {
  trait TwinColSelectMixin extends AbstractSelectMixin { self: com.vaadin.ui.TwinColSelect => }
}

class TwinColSelect(override val p: com.vaadin.ui.TwinColSelect with TwinColSelectMixin = new com.vaadin.ui.TwinColSelect with TwinColSelectMixin)
    extends AbstractSelect(p) with MultiSelectable {

  def rows: Int = p.getRows
  def rows_=(rows: Int): Unit = p.setRows(rows)

  def rightColumnCaption: Option[String] = Option(p.getRightColumnCaption)
  def rightColumnCaption_=(rightColumnCaption: Option[String]): Unit = p.setRightColumnCaption(rightColumnCaption.orNull)
  def rightColumnCaption_=(rightColumnCaption: String): Unit = p.setRightColumnCaption(rightColumnCaption)

  def leftColumnCaption: Option[String] = Option(p.getLeftColumnCaption)
  def leftColumnCaption_=(leftColumnCaption: Option[String]): Unit = p.setLeftColumnCaption(leftColumnCaption.orNull)
  def leftColumnCaption_=(leftColumnCaption: String): Unit = p.setLeftColumnCaption(leftColumnCaption)
}