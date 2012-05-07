package vaadin.scala

import vaadin.scala.mixins.AbstractTextFieldMixin

package mixins {
  trait AbstractTextFieldMixin extends AbstractFieldMixin
}

abstract class AbstractTextField(override val p: com.vaadin.ui.AbstractTextField with AbstractTextFieldMixin) extends AbstractField(p) {

  def prompt: Option[String] = Option(p.getInputPrompt())
  def prompt_=(prompt: Option[String]) = p.setInputPrompt(prompt.getOrElse(null))
  def prompt_=(prompt: String) = p.setInputPrompt(prompt)

  def nullRepresentation = p.getNullRepresentation
  def nullRepresentation_=(nullRepresentation: String) = { require(nullRepresentation != null); p.setNullRepresentation(nullRepresentation) }

  def nullSettingAllowed = p.isNullSettingAllowed
  def nullSettingAllowed_=(nullSettingAllowed: Boolean) = p.setNullSettingAllowed(nullSettingAllowed)

  def maxLength = p.getMaxLength
  def maxLength_=(maxLength: Int) = p.setMaxLength(maxLength)

  def columns = p.getColumns
  def columns_=(columns: Int) = p.setColumns(columns)
}