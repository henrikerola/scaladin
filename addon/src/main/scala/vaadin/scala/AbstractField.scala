package vaadin.scala

import event.ValueChangeNotifier
import vaadin.scala.converter.Converter
import vaadin.scala.mixins.AbstractFieldMixin

package mixins {
  trait AbstractFieldMixin[T] extends AbstractComponentMixin with FieldMixin[T] {
    self: com.vaadin.ui.AbstractField[T] =>
    override def wrapper = super.wrapper.asInstanceOf[AbstractField[T]]
  }
}

abstract class AbstractField[T](override val p: com.vaadin.ui.AbstractField[T] with AbstractFieldMixin[T])
    extends AbstractComponent(p) with Field[T] with PropertyViewer with ValueChangeNotifier {

  def validationVisible: Boolean = p.isValidationVisible
  def validationVisible_=(isValidationVisible: Boolean): Unit = p.setValidationVisible(isValidationVisible)

  def conversionError: String = p.getConversionError
  def conversionError_=(conversionError: String): Unit = p.setConversionError(conversionError)

  def converter: Option[Converter[T, Any]] = wrapperFor[Converter[T, Any]](p.getConverter)
  def converter_=(converter: Converter[T, _]): Unit = p.setConverter(converter.p)
  def converter_=(datamodelType: Class[_]): Unit = p.setConverter(datamodelType)

  override def value: Option[T] = super.value.asInstanceOf[Option[T]]

}

