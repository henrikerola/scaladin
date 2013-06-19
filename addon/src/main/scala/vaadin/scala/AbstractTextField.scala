package vaadin.scala

import event.{ FocusNotifier, BlurNotifier, ComponentEvent }
import vaadin.scala.mixins.AbstractTextFieldMixin
import vaadin.scala.internal.TextChangeListener
import vaadin.scala.internal.ListenersTrait
import com.vaadin.event.FieldEvents
import vaadin.scala.event.ComponentEvent

package mixins {
  trait AbstractTextFieldMixin extends AbstractFieldMixin[String] { self: com.vaadin.ui.AbstractTextField => }
}

object AbstractTextField {
  object TextChangeEventMode extends Enumeration {
    import com.vaadin.ui.AbstractTextField.TextChangeEventMode._
    val Eager = Value(EAGER.ordinal)
    val Timeout = Value(TIMEOUT.ordinal)
    val Lazy = Value(LAZY.ordinal)
  }

  case class TextChangeEvent(textField: AbstractTextField, text: String, cursorPosition: Int) extends ComponentEvent(textField)
}

abstract class AbstractTextField(override val p: com.vaadin.ui.AbstractTextField with AbstractTextFieldMixin)
    extends AbstractField[String](p) with BlurNotifier with FocusNotifier {

  def prompt: Option[String] = Option(p.getInputPrompt)
  def prompt_=(prompt: Option[String]) { p.setInputPrompt(prompt.orNull) }
  def prompt_=(prompt: String) { p.setInputPrompt(prompt) }

  def nullRepresentation: String = p.getNullRepresentation
  def nullRepresentation_=(nullRepresentation: String) { p.setNullRepresentation(nullRepresentation) }

  def nullSettingAllowed: Boolean = p.isNullSettingAllowed
  def nullSettingAllowed_=(nullSettingAllowed: Boolean) { p.setNullSettingAllowed(nullSettingAllowed) }

  def maxLength: Int = p.getMaxLength
  def maxLength_=(maxLength: Int) { p.setMaxLength(maxLength) }

  def columns: Int = p.getColumns
  def columns_=(columns: Int) { p.setColumns(columns) }

  def selectAll() { p.selectAll() }

  // TODO:
  //def selectionRange(pos: Int, length: Int) = p.setSelectionRange(pos, length)

  def cursorPosition: Int = p.getCursorPosition
  def cursorPosition_=(cursorPosition: Int) { p.setCursorPosition(cursorPosition) }

  def textChangeEventMode = AbstractTextField.TextChangeEventMode(p.getTextChangeEventMode.ordinal)
  def textChangeEventMode_=(textChangeEventMode: AbstractTextField.TextChangeEventMode.Value) {
    p.setTextChangeEventMode(com.vaadin.ui.AbstractTextField.TextChangeEventMode.values()(textChangeEventMode.id))
  }

  def textChangeTimeout = p.getTextChangeTimeout
  def textChangeTimeout_=(textChangeTimeout: Int) = p.setTextChangeTimeout(textChangeTimeout)

  lazy val textChangeListeners: ListenersSet[AbstractTextField.TextChangeEvent => Unit] =
    new ListenersTrait[AbstractTextField.TextChangeEvent, TextChangeListener] {
      override def listeners = p.getListeners(classOf[com.vaadin.event.FieldEvents.TextChangeEvent])
      override def addListener(elem: AbstractTextField.TextChangeEvent => Unit) =
        p.addTextChangeListener(new TextChangeListener(elem))
      override def removeListener(elem: TextChangeListener) = p.removeTextChangeListener(elem)
    }
}