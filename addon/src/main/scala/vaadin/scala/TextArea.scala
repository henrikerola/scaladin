package vaadin.scala

import vaadin.scala.mixins.TextAreaMixin

package mixins {
  trait TextAreaMixin extends AbstractTextFieldMixin
}

class TextArea(override val p: com.vaadin.ui.TextArea with TextAreaMixin = new com.vaadin.ui.TextArea with TextAreaMixin) extends AbstractTextField(p) {

  def rows = p.getRows
  def rows_=(rows: Int) = p.setRows(rows)

  def wordwrap = p.isWordwrap
  def wordwrap_=(wordwrap: Boolean) = p.setWordwrap(wordwrap)
}