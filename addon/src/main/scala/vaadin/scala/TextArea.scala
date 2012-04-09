package vaadin.scala

class TextArea(val p: com.vaadin.ui.TextArea = new com.vaadin.ui.TextArea) extends AbstractTextField {

  WrapperRegistry.put(this)

  def rows = p.getRows
  def rows_=(rows: Int) = p.setRows(rows)

  def wordwrap = p.isWordwrap()
  def wordwrap_=(wordwrap: Boolean) = p.setWordwrap(wordwrap)
}