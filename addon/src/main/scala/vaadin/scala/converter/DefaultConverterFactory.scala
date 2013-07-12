package vaadin.scala.converter

import java.util.Date
import scala.reflect._

/**
 * @author Henri Kerola / Vaadin
 */
class DefaultConverterFactory extends ConverterFactory {

  // TODO move these to a common util class
  protected val LongClass = classOf[Long]
  protected val JavaLongClass = classOf[java.lang.Long]
  protected val DoubleClass = classOf[Double]
  protected val JavaDoubleClass = classOf[java.lang.Double]
  protected val FloatClass = classOf[Float]
  protected val JavaFloatClass = classOf[java.lang.Float]
  protected val IntClass = classOf[Int]
  protected val JavaIntegerClass = classOf[Integer]
  protected val BooleanClass = classOf[Boolean]
  protected val JavaBooleanClass = classOf[java.lang.Boolean]
  protected val DateClass = classOf[Date]

  override def createConverter[Presentation, Model](implicit presentationType: ClassTag[Presentation],
                                                    modelType: ClassTag[Model]): Option[Converter[Presentation, Model]] = {
    //if (presentationType.runtimeClass == classOf[Option[_]]

    lazy val converter = findConverter(presentationType, modelType)
    lazy val reverse = findConverter(modelType, presentationType) map { new ReverseConverter[Presentation, Model](_) }
    converter orElse reverse
  }

  protected def findConverter[Presentation, Model](implicit presentationType: ClassTag[Presentation],
                                                   modelType: ClassTag[Model]) =
    if (presentationType.runtimeClass == classOf[String])
      createStringConverter(modelType.runtimeClass).asInstanceOf[Option[Converter[Presentation, Model]]]
    else if (presentationType.runtimeClass == classOf[Date])
      createDateConverter(modelType.runtimeClass).asInstanceOf[Option[Converter[Presentation, Model]]]
    else
      None

  protected def createDateConverter(sourceType: Class[_]): Option[Converter[Date, _]] =
    sourceType match {
      //case LongClass => Some(new DateToLongConverter)
      //case JavaLongClass => Some(new DateToJavaLongConverter)
      case _ => None
    }

  protected def createStringConverter(sourceType: Class[_]): Option[Converter[String, _]] =
    sourceType match {
      //case DoubleClass => Some(new StringToDoubleConverter)
      case JavaDoubleClass => Some(new StringToJavaDoubleConverter)
      //case FloatClass => Some(new StringToFloatConverter)
      case JavaFloatClass => Some(new StringToJavaFloatConverter)
      case IntClass => println("1234"); Some(new StringToIntConverter)
      case JavaIntegerClass => println("456"); Some(new StringToJavaIntegerConverter)
      //case BooleanClass => Some(new StringToBooleanConverter)
      case JavaBooleanClass => Some(new StringToJavaBooleanConverter)
      case DateClass => Some(new StringToDateConverter)
      case _ => None
    }
}
