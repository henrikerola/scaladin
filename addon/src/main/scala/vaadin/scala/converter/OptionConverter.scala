package vaadin.scala.converter

import java.util.Locale
import scala.reflect.ClassTag

object OptionConverter {

  def apply[P: ClassTag, M: ClassTag](converter: Converter[P, M]): OptionConverter[P, M] = {
    new OptionConverter[P, M](converter)
  }
}

/**
 *
 * @author Henri Kerola / Vaadin
 */
class OptionConverter[P: ClassTag, M: ClassTag](converter: Converter[P, M]) extends Converter[P, Option[M]] {

  override def convertToPresentation(value: Option[Option[M]], targetType: Class[_ <: P], locale: Locale): Option[P] = {
    converter.convertToPresentation(value.orNull, targetType, locale)
  }

  override def convertToModel(value: Option[P], targetType: Class[_ <: Option[M]], locale: Locale): Option[Option[M]] = {
    Option(converter.convertToModel(value, converter.modelType, locale))
  }
}
