package vaadin.scala

import vaadin.scala.mixins.NativeSelectMixin

package mixins {
  trait NativeSelectMixin extends AbstractSelectMixin
}

class NativeSelect(override val p: com.vaadin.ui.NativeSelect with NativeSelectMixin = new com.vaadin.ui.NativeSelect with NativeSelectMixin) extends AbstractSelect(p) {

  def columns = p.getColumns
  def columns_=(columns: Int) = p.setColumns(columns)
}