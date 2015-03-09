package vaadin.scala.converter

import java.util.Locale

import vaadin.scala.server.Resource
import com.vaadin.server.{ Resource => VaadinResource }

object ResourceConverter {

  def apply(): ResourceConverter = new ResourceConverter

}

/**
 * A converter converting between Vaadin Resources and Scaladin Resources.
 *
 * @author Henri Kerola / Vaadin
 */
class ResourceConverter extends Converter[VaadinResource, Resource] {

  override def convertToPresentation(
    value: Option[Resource],
    targetType: Class[_ <: VaadinResource],
    locale: Locale): Option[VaadinResource] = value.map(_.pResource)

  override def convertToModel(
    value: Option[VaadinResource],
    targetType: Class[_ <: Resource],
    locale: Locale): Option[Resource] = value.flatMap(wrapperFor(_))
}
