package vaadin.scala

import vaadin.scala.converter.Converter
import vaadin.scala.mixins.AbstractFieldMixin
import vaadin.scala.mixins.FieldMixin

package mixins {
  trait AbstractFieldMixin[T] extends AbstractComponentMixin with FieldMixin[T] { self: com.vaadin.ui.AbstractField[T] =>
    override def wrapper = super.wrapper.asInstanceOf[AbstractField[T]]
  }
  trait FieldMixin[T] extends ComponentMixin with BufferedValidatableMixin { self: com.vaadin.ui.Field[T] =>
    override def wrapper = super.wrapper.asInstanceOf[Field[T]]
  }
}

trait Field[T] extends Component with BufferedValidatable with Property[T] with Focusable with Wrapper {

  def p: com.vaadin.ui.Field[T] with FieldMixin[T]

  //readOnly is inherited from Component and Property, needs override
  override def readOnly: Boolean = p.isReadOnly
  override def readOnly_=(readOnly: Boolean): Unit = p.setReadOnly(readOnly)

  //  def description: Option[String] = Option(p.getDescription)
  //  def description_=(description: String): Unit = p.setDescription(description)
  //  def description_=(description: Option[String]): Unit = p.setDescription(description.getOrElse(null))

  def required: Boolean = p.isRequired
  def required_=(required: Boolean): Unit = p.setRequired(required)

  def requiredError: Option[String] = Option(p.getRequiredError)
  def requiredError_=(requiredError: String): Unit = p.setRequiredError(requiredError)
  def requiredError_=(requiredError: Option[String]): Unit = p.setRequiredError(requiredError.orNull)
}

abstract class AbstractField[T](override val p: com.vaadin.ui.AbstractField[T] with AbstractFieldMixin[T])
    extends AbstractComponent(p) with Field[T] with PropertyViewer with ValueChangeNotifier {

  //description is inherited from AbstractComponent and Field, needs override
  // override def description: Option[String] = Option(p.getDescription)
  // override def description_=(description: String): Unit = p.setDescription(description)
  // override def description_=(description: Option[String]): Unit = p.setDescription(description.orNull)

  def validationVisible: Boolean = p.isValidationVisible
  def validationVisible_=(isValidationVisible: Boolean): Unit = p.setValidationVisible(isValidationVisible)

  def conversionError: String = p.getConversionError
  def conversionError_=(conversionError: String): Unit = p.setConversionError(conversionError)

  def converter: Option[Converter[T, Any]] = wrapperFor[Converter[T, Any]](p.getConverter)
  def converter_=(converter: Converter[T, _]): Unit = p.setConverter(converter.p)
  def converter_=(datamodelType: Class[_]): Unit = p.setConverter(datamodelType)

  //override def getType: Class[_ <: T]
}

