package vaadin.scala

class RichTextArea(val p: com.vaadin.ui.RichTextArea = new com.vaadin.ui.RichTextArea) extends AbstractField {

  def selectAll() = p.selectAll()

  def nullRepresentation = p.getNullRepresentation
  def nullRepresentation_=(nullRepresentation: String) = { require(nullRepresentation != null); p.setNullRepresentation(nullRepresentation) }

  def nullSettingAllowed = p.isNullSettingAllowed
  def nullSettingAllowed_=(nullSettingAllowed: Boolean) = p.setNullSettingAllowed(nullSettingAllowed)
}
