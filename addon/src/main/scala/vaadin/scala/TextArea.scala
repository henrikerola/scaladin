package vaadin.scala

import vaadin.scala.mixins.TextAreaMixin

package mixins {
  trait TextAreaMixin extends AbstractTextFieldMixin { self: com.vaadin.ui.TextArea => }
}

class TextArea(
  override val p: com.vaadin.ui.TextArea with TextAreaMixin = new com.vaadin.ui.TextArea with TextAreaMixin)
    extends AbstractTextField(p) {

  def rows: Int = p.getRows
  def rows_=(rows: Int) { p.setRows(rows) }

  def wordwrap: Boolean = p.isWordwrap
  def wordwrap_=(wordwrap: Boolean) { p.setWordwrap(wordwrap) }
}