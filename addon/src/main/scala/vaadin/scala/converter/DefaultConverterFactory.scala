package vaadin.scala.converter

import java.util.Date

import vaadin.scala.server.Resource
import com.vaadin.server.{ Resource => VaadinResource }

import scala.reflect.{ ClassTag, classTag }

/**
 *
 * @author Henri Kerola / Vaadin
 */
object DefaultConverterFactory extends ConverterFactory {
  override def createConverter[Presentation: ClassTag, Model: ClassTag]: Option[Converter[Presentation, Model]] = {
    val converter = findConverter[Presentation, Model] orElse findConverter[Model, Presentation]
    converter.asInstanceOf[Option[Converter[Presentation, Model]]]
  }

  def findConverter[Presentation: ClassTag, Model: ClassTag]: Option[Converter[_, _]] = {
    val presentationType = classTag[Presentation].runtimeClass
    val modelType = classTag[Model].runtimeClass

    val scaladinWrapperConverterOption = createScaladinWrapperConverter(presentationType, modelType)

    if (scaladinWrapperConverterOption.isDefined) scaladinWrapperConverterOption
    else if (presentationType == classOf[String]) createStringConverter(modelType)
    else if (presentationType == classOf[Date]) createDateConverter(modelType)
    else None
  }

  private[this] def createScaladinWrapperConverter(presentationType: Class[_], modelType: Class[_]): Option[Converter[_, _]] = {
    if (classOf[Resource].isAssignableFrom(presentationType) && classOf[VaadinResource].isAssignableFrom(modelType))
      Option(new ResourceConverter)
    else None
  }

  private[this] def createStringConverter(sourceType: Class[_]): Option[Converter[String, _]] = {
    if (classOf[Double].isAssignableFrom(sourceType) || classOf[java.lang.Double].isAssignableFrom(sourceType))
      Option(new StringToDoubleConverter)
    else if (classOf[Float].isAssignableFrom(sourceType) || classOf[java.lang.Float].isAssignableFrom(sourceType))
      Option(new StringToFloatConverter)
    else if (classOf[Integer].isAssignableFrom(sourceType) || classOf[java.lang.Integer].isAssignableFrom(sourceType))
      Option(new StringToIntegerConverter)
    else if (classOf[Long].isAssignableFrom(sourceType) || classOf[java.lang.Long].isAssignableFrom(sourceType))
      Option(new StringToLongConverter)
    else if (classOf[BigDecimal].isAssignableFrom(sourceType) || classOf[java.math.BigDecimal].isAssignableFrom(sourceType))
      Option(new StringToBigDecimalConverter)
    else if (classOf[Boolean].isAssignableFrom(sourceType) || classOf[java.lang.Boolean].isAssignableFrom(sourceType))
      Option(new StringToBooleanConverter)
    else if (classOf[java.util.Date].isAssignableFrom(sourceType))
      Option(new StringToDateConverter)
    else if (classOf[BigInt].isAssignableFrom(sourceType) || classOf[java.math.BigInteger].isAssignableFrom(sourceType))
      Option(new StringToBigIntegerConverter)
    else if (classOf[Short].isAssignableFrom(sourceType) || classOf[java.lang.Short].isAssignableFrom(sourceType))
      Option(new StringToShortConverter)
    else if (classOf[Byte].isAssignableFrom(sourceType) || classOf[java.lang.Byte].isAssignableFrom(sourceType))
      Option(new StringToByteConverter)
    else None
  }

  private[this] def createDateConverter(sourceType: Class[_]): Option[Converter[Date, _]] = {
    if (classOf[Long].isAssignableFrom(sourceType) || classOf[java.lang.Long].isAssignableFrom(sourceType))
      Option(new DateToLongConverter)
    else None
  }
}
