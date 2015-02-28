package vaadin.scala

import vaadin.scala.mixins.RichTextAreaMixin

package mixins {
  trait RichTextAreaMixin extends AbstractFieldMixin[String, String] { self: com.vaadin.ui.RichTextArea => }
}

class RichTextArea(override val p: com.vaadin.ui.RichTextArea with RichTextAreaMixin = new com.vaadin.ui.RichTextArea with RichTextAreaMixin) extends AbstractField[String, String](p) {

  def selectAll() = p.selectAll()

  def nullRepresentation = p.getNullRepresentation
  def nullRepresentation_=(nullRepresentation: String) = { require(nullRepresentation != null); p.setNullRepresentation(nullRepresentation) }

  def nullSettingAllowed = p.isNullSettingAllowed
  def nullSettingAllowed_=(nullSettingAllowed: Boolean) = p.setNullSettingAllowed(nullSettingAllowed)
}
