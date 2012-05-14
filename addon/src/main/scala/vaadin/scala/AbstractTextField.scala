package vaadin.scala

import vaadin.scala.mixins.AbstractTextFieldMixin
import vaadin.scala.listeners.BlurListeners
import vaadin.scala.listeners.FocusListeners
import vaadin.scala.listeners.TextChangeListener
import com.vaadin.event.FieldEvents

package mixins {
  trait AbstractTextFieldMixin extends AbstractFieldMixin
}

object AbstractTextField {
  object TextChangeEventMode extends Enumeration {
    import com.vaadin.ui.AbstractTextField.TextChangeEventMode._
    val Eager = Value(EAGER.ordinal)
    val Timeout = Value(TIMEOUT.ordinal)
    val Lazy = Value(LAZY.ordinal)
  }

  case class TextChangeEvent(textField: AbstractTextField, text: String, cursorPosition: Int) extends Event
}

abstract class AbstractTextField(override val p: com.vaadin.ui.AbstractTextField with AbstractTextFieldMixin) extends AbstractField(p) {

  def prompt: Option[String] = Option(p.getInputPrompt)
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

  def selectAll() = p.selectAll()

  // TODO:
  //def selectionRange(pos: Int, length: Int) = p.setSelectionRange(pos, length)

  def cursorPosition = p.getCursorPosition
  def cursorPosition_=(cursorPosition: Int) = p.setCursorPosition(cursorPosition)

  def textChangeEventMode = AbstractTextField.TextChangeEventMode(p.getTextChangeEventMode.ordinal)
  def textChangeEventMode_=(textChangeEventMode: AbstractTextField.TextChangeEventMode.Value) = p.setTextChangeEventMode(com.vaadin.ui.AbstractTextField.TextChangeEventMode.values()(textChangeEventMode.id))

  def textChangeTimeout = p.getTextChangeTimeout
  def textChangeTimeout_=(textChangeTimeout: Int) = p.setTextChangeTimeout(textChangeTimeout)

  lazy val textChangeListeners = new ListenersTrait[AbstractTextField.TextChangeEvent, TextChangeListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.FieldEvents.TextChangeEvent])
    override def addListener(elem: AbstractTextField.TextChangeEvent => Unit) = p.addListener(new TextChangeListener(elem))
    override def removeListener(elem: TextChangeListener) = p.removeListener(elem)
  }

  lazy val blurListeners = new BlurListeners(p)
  lazy val focusListeners = new FocusListeners(p)
}

package listeners {
  class TextChangeListener(val action: AbstractTextField.TextChangeEvent => Unit) extends com.vaadin.event.FieldEvents.TextChangeListener with Listener {
    def textChange(e: com.vaadin.event.FieldEvents.TextChangeEvent) = action(AbstractTextField.TextChangeEvent(wrapperFor[AbstractTextField](e.getComponent).get, e.getText, e.getCursorPosition))
  }
}