package vaadin.scala.converter

import java.util.Locale
import scala.reflect.ClassTag

/**
 * @see com.vaadin.data.util.converter.ReverseConverter
 * @author Henri Kerola / Vaadin
 */
class ReverseConverter[Presentation: ClassTag, Model: ClassTag](converter: Converter[Model, Presentation])
    extends Converter[Presentation, Model] {

  def convertToPresentation(value: Model, locale: Locale) = converter.convertToModel(value, locale)

  def convertToModel(value: Presentation, locale: Locale) = converter.convertToPresentation(value, locale)
}
