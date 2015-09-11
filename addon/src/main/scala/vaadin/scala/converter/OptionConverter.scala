package vaadin.scala.converter

import java.util.Locale

import scala.reflect.ClassTag

object OptionConverter {

  def apply[P: ClassTag](): OptionConverter[P] = {
    new OptionConverter[P]()
  }
}

/**
 * Converts between a value and an [[Option]]-wrapped value, which both are same type.
 * For example, for converting between [[java.util.Date]] and [[Option[java.util.Date]]]
 * for a [[vaadin.scala.DateField]], one could say:
 *
 * {{{
 * dateField.converter = OptionConverter[Date]()
 * }}}
 *
 * @author Henri Kerola / Vaadin
 */
class OptionConverter[P: ClassTag]() extends Converter[P, Option[P]] {

  override def convertToPresentation(value: Option[Option[P]], targetType: Class[_ <: P], locale: Locale): Option[P] = {
    value.orNull
  }

  override def convertToModel(value: Option[P], targetType: Class[_ <: Option[P]], locale: Locale): Option[Option[P]] = {
    Option(value)
  }
}
