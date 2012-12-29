package vaadin.scala

import vaadin.scala.mixins.NativeSelectMixin

package mixins {
  trait NativeSelectMixin extends AbstractSelectMixin { self: com.vaadin.ui.NativeSelect => }
}

class NativeSelect(override val p: com.vaadin.ui.NativeSelect with NativeSelectMixin = new com.vaadin.ui.NativeSelect with NativeSelectMixin) extends AbstractSelect(p) {

  def columns: Int = p.getColumns
  def columns_=(columns: Int): Unit = p.setColumns(columns)
}