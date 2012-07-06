package vaadin.scala

import vaadin.scala.mixins.AbstractFieldMixin
import vaadin.scala.listeners.ValueChangeListener
import vaadin.scala.mixins.FieldMixin
import vaadin.scala.mixins.ValidatableMixin

package mixins {
  trait AbstractFieldMixin extends AbstractComponentMixin with FieldMixin
  trait FieldMixin extends ComponentMixin with BufferedValidatableMixin
}

trait Field extends Component with BufferedValidatable with Property with Focusable with Wrapper {

  def p: com.vaadin.ui.Field with FieldMixin

  def description: Option[String] = Option(p.getDescription)
  def description_=(description: String): Unit = p.setDescription(description)
  def description_=(description: Option[String]): Unit = p.setDescription(description.getOrElse(null))

  def required: Boolean = p.isRequired
  def required_=(required: Boolean): Unit = p.setRequired(required)

  def requiredError: Option[String] = Option(p.getRequiredError)
  def requiredError_=(requiredError: String): Unit = p.setRequiredError(requiredError)
  def requiredError_=(requiredError: Option[String]): Unit = p.setRequiredError(requiredError.getOrElse(null))
}

abstract class AbstractField(override val p: com.vaadin.ui.AbstractField with AbstractFieldMixin) extends AbstractComponent(p) with Field with PropertyViewer with Focusable {

  //description is inherited from AbstractComponent and Field, needs override
  override def description: Option[String] = Option(p.getDescription)
  override def description_=(description: String): Unit = p.setDescription(description)
  override def description_=(description: Option[String]): Unit = p.setDescription(description.getOrElse(null))

  lazy val valueChangeListeners = new ListenersTrait[ValueChangeEvent, ValueChangeListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.data.Property.ValueChangeEvent])
    override def addListener(elem: ValueChangeEvent => Unit) = p.addListener(new ValueChangeListener(elem))
    override def removeListener(elem: ValueChangeListener) = p.removeListener(elem)
  }
}

package listeners {
  class ValueChangeListener(val action: ValueChangeEvent => Unit) extends com.vaadin.data.Property.ValueChangeListener with Listener {
    def valueChange(e: com.vaadin.data.Property.ValueChangeEvent) = action(ValueChangeEvent(new BasicProperty(e.getProperty)))
  }
}