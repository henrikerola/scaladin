package vaadin.scala.converter

import scala.reflect.{ ClassTag, classTag }
import java.util.Locale
import vaadin.scala.Wrapper
import vaadin.scala.mixins.ScaladinMixin
import vaadin.scala.converter.mixins.{ DelegatingConverterMixin, ConverterMixin }

package mixins {
  trait ConverterMixin[Presentation, Model] extends ScaladinMixin
  trait DelegatingConverterMixin[Presentation, Model] extends ConverterMixin[Presentation, Model] {
    self: com.vaadin.data.util.converter.Converter[Presentation, Model] =>

    override def wrapper = super.wrapper.asInstanceOf[Converter[Presentation, Model]]

    override def convertToModel(value: Presentation, locale: Locale): Model =
      wrapper.convertToModel(Option(value), locale).getOrElse(null).asInstanceOf[Model]

    override def convertToPresentation(value: Model, locale: Locale): Presentation =
      wrapper.convertToPresentation(Option(value), locale).getOrElse(null).asInstanceOf[Presentation]

    override def getPresentationType: Class[Presentation] = wrapper.presentationType

    override def getModelType: Class[Model] = wrapper.modelType
  }
}

/**
 * @see com.vaadin.data.util.converter.Converter
 * @author Henri Kerola / Vaadin
 */
abstract class Converter[Presentation: ClassTag, Model: ClassTag](val p: com.vaadin.data.util.converter.Converter[Presentation, Model] with ConverterMixin[Presentation, Model] = new com.vaadin.data.util.converter.Converter[Presentation, Model] with DelegatingConverterMixin[Presentation, Model])
    extends Wrapper {

  p.wrapper = this

  private val presentationClassTag = classTag[Presentation]
  private val modelClassTag = classTag[Model]

  def convertToPresentation(value: Option[Model], locale: Locale): Option[Presentation]

  def convertToModel(value: Option[Presentation], locale: Locale): Option[Model]

  def presentationType: Class[Presentation] = presentationClassTag.runtimeClass.asInstanceOf[Class[Presentation]]

  def modelType: Class[Model] = modelClassTag.runtimeClass.asInstanceOf[Class[Model]]
}
abstract class DeletagePeerConverter[Presentation: ClassTag, Model: ClassTag](override val p: com.vaadin.data.util.converter.Converter[Presentation, Model] with ConverterMixin[Presentation, Model]) extends Converter[Presentation, Model](p) {

  def convertToPresentation(value: Option[Model], locale: Locale): Option[Presentation] =
    Option(p.convertToPresentation(value.getOrElse(null).asInstanceOf[Model], locale))

  def convertToModel(value: Option[Presentation], locale: Locale): Option[Model] =
    Option(p.convertToModel(value.getOrElse(null).asInstanceOf[Presentation], locale))

}