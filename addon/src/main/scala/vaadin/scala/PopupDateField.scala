package vaadin.scala

class PopupDateField(override val p: com.vaadin.ui.PopupDateField = new com.vaadin.ui.PopupDateField) extends DateField(p) {

  def prompt: Option[String] = Option(p.getInputPrompt())
  def prompt_=(prompt: Option[String]) = p.setInputPrompt(prompt.getOrElse(null))
  def prompt_=(prompt: String) = p.setInputPrompt(prompt)

}