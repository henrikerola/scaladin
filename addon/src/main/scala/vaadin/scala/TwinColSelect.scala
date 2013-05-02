package vaadin.scala

import com.vaadin.ui.{ TwinColSelect => VaadinTwinColSelect }
import vaadin.scala.mixins.TwinColSelectMixin

package mixins {
  trait TwinColSelectMixin extends AbstractSelectMixin { self: com.vaadin.ui.TwinColSelect => }
}

class TwinColSelect(
  override val p: VaadinTwinColSelect with TwinColSelectMixin = new VaadinTwinColSelect with TwinColSelectMixin)
    extends AbstractSelect(p) with MultiSelectable {

  def rows: Int = p.getRows
  def rows_=(rows: Int) { p.setRows(rows) }

  def rightColumnCaption: Option[String] = Option(p.getRightColumnCaption)
  def rightColumnCaption_=(rightColumnCaption: Option[String]) { p.setRightColumnCaption(rightColumnCaption.orNull) }
  def rightColumnCaption_=(rightColumnCaption: String) { p.setRightColumnCaption(rightColumnCaption) }

  def leftColumnCaption: Option[String] = Option(p.getLeftColumnCaption)
  def leftColumnCaption_=(leftColumnCaption: Option[String]) { p.setLeftColumnCaption(leftColumnCaption.orNull) }
  def leftColumnCaption_=(leftColumnCaption: String) { p.setLeftColumnCaption(leftColumnCaption) }
}