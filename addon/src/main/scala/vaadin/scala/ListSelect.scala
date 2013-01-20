package vaadin.scala

import vaadin.scala.mixins.ListSelectMixin

package mixins {
  trait ListSelectMixin extends AbstractSelectMixin { self: com.vaadin.ui.ListSelect => }
}

class ListSelect(override val p: com.vaadin.ui.ListSelect with ListSelectMixin = new com.vaadin.ui.ListSelect with ListSelectMixin) extends AbstractSelect(p) with MultiSelectable {

  def rows = p.getRows
  def rows_=(rows: Int) = p.setRows(rows)
}