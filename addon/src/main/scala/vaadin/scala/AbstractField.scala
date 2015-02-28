package vaadin.scala

import event.ValueChangeNotifier
import vaadin.scala.converter.Converter
import vaadin.scala.mixins.AbstractFieldMixin

package mixins {
  trait AbstractFieldMixin[T, V] extends AbstractComponentMixin with FieldMixin[T, V] {
    self: com.vaadin.ui.AbstractField[V] =>
    override def wrapper = super.wrapper.asInstanceOf[AbstractField[T, V]]
  }
}

abstract class AbstractField[T, V](override val p: com.vaadin.ui.AbstractField[V] with AbstractFieldMixin[T, V])
    extends AbstractComponent(p) with Field[T, V] with PropertyViewer with ValueChangeNotifier {

  def validationVisible: Boolean = p.isValidationVisible
  def validationVisible_=(isValidationVisible: Boolean): Unit = p.setValidationVisible(isValidationVisible)

  def conversionError: String = p.getConversionError
  def conversionError_=(conversionError: String): Unit = p.setConversionError(conversionError)

  def converter: Option[Converter[T, Any]] = wrapperFor[Converter[T, Any]](p.getConverter)
  def converter_=(converter: Converter[T, _]): Unit = {} //p.setConverter(converter.p) FIXME
  def converter_=(datamodelType: Class[_]): Unit = p.setConverter(datamodelType)

  override def value: Option[T] = super.value.asInstanceOf[Option[T]]

}

