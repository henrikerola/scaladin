package vaadin.scala

class RichTextArea(override val p: com.vaadin.ui.RichTextArea = new com.vaadin.ui.RichTextArea) extends AbstractField(p) {

  def selectAll() = p.selectAll()

  def nullRepresentation = p.getNullRepresentation
  def nullRepresentation_=(nullRepresentation: String) = { require(nullRepresentation != null); p.setNullRepresentation(nullRepresentation) }

  def nullSettingAllowed = p.isNullSettingAllowed
  def nullSettingAllowed_=(nullSettingAllowed: Boolean) = p.setNullSettingAllowed(nullSettingAllowed)
}
