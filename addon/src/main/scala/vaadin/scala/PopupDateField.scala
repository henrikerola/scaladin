package vaadin.scala

import vaadin.scala.mixins.PopupDateFieldMixin

package mixins {
  trait PopupDateFieldMixin extends DateFieldMixin
}

class PopupDateField(override val p: com.vaadin.ui.PopupDateField with PopupDateFieldMixin = new com.vaadin.ui.PopupDateField with PopupDateFieldMixin) extends DateField(p) {

  def prompt: Option[String] = Option(p.getInputPrompt())
  def prompt_=(prompt: Option[String]) = p.setInputPrompt(prompt.getOrElse(null))
  def prompt_=(prompt: String) = p.setInputPrompt(prompt)

}