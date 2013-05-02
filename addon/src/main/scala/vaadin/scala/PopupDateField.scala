package vaadin.scala

import com.vaadin.ui.{ PopupDateField => VaadinPopupDateField }
import vaadin.scala.mixins.PopupDateFieldMixin

package mixins {
  trait PopupDateFieldMixin extends DateFieldMixin { self: com.vaadin.ui.PopupDateField => }
}

class PopupDateField(
  override val p: VaadinPopupDateField with PopupDateFieldMixin = new VaadinPopupDateField with PopupDateFieldMixin)
    extends DateField(p) {

  def prompt: Option[String] = Option(p.getInputPrompt())
  def prompt_=(prompt: Option[String]) { p.setInputPrompt(prompt.orNull) }
  def prompt_=(prompt: String) { p.setInputPrompt(prompt) }

}