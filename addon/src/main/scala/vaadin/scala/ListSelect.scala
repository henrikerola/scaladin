package vaadin.scala

import com.vaadin.ui.{ ListSelect => VaadinListSelect }
import vaadin.scala.mixins.ListSelectMixin

package mixins {
  trait ListSelectMixin extends AbstractSelectMixin { self: com.vaadin.ui.ListSelect => }
}

class ListSelect(override val p: VaadinListSelect with ListSelectMixin = new VaadinListSelect with ListSelectMixin)
    extends AbstractSelect(p) with MultiSelectable {

  def rows: Int = p.getRows
  def rows_=(rows: Int) { p.setRows(rows) }
}