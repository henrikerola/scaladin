package vaadin.scala

import scala.reflect.Manifest
import java.util.Locale
import java.util.Date
import vaadin.scala.mixins.ConverterMixin

package mixins {
  trait ConverterMixin[Presentation, Model] extends ScaladinMixin { self: com.vaadin.data.util.converter.Converter[Presentation, Model] =>

    override def wrapper = super.wrapper.asInstanceOf[Converter[Presentation, Model]]

    override def convertToModel(value: Presentation, locale: Locale): Model = wrapper.convertToModel(value, locale)

    override def convertToPresentation(value: Model, locale: Locale): Presentation = wrapper.convertToPresentation(value, locale)

    override def getPresentationType: Class[Presentation] = wrapper.presentationType

    override def getModelType: Class[Model] = wrapper.modelType
  }
}

/**
 * @see com.vaadin.data.util.converter.Converter
 * @author Henri Kerola / Vaadin
 */
abstract class Converter[Presentation: Manifest, Model: Manifest](val p: com.vaadin.data.util.converter.Converter[Presentation, Model] with ConverterMixin[Presentation, Model] = new com.vaadin.data.util.converter.Converter[Presentation, Model] with ConverterMixin[Presentation, Model])
  extends Wrapper {

  p.wrapper = this

  private var presentationManifest: Option[Manifest[Presentation]] = None
  private var modelManifest: Option[Manifest[Model]] = None
  setManifests()

  private def setManifests()(implicit presentationManifest: Manifest[Presentation], modelManifest: Manifest[Model]) {
    this.presentationManifest = Option(presentationManifest)
    this.modelManifest = Option(modelManifest)
  }

  def convertToPresentation(value: Model, locale: Locale): Presentation

  def convertToModel(value: Presentation, locale: Locale): Model

  def presentationType: Class[Presentation] = presentationManifest.get.erasure.asInstanceOf[Class[Presentation]]

  def modelType: Class[Model] = modelManifest.get.erasure.asInstanceOf[Class[Model]]
}