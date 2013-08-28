package vaadin.scala.tests

import vaadin.scala.{ AbstractField }
import org.mockito.Mockito
import vaadin.scala.mixins.AbstractFieldMixin
import java.util.Locale
import vaadin.scala.converter.Converter

/**
 * @author Henri Kerola / Vaadin
 */
class AbstractFieldTests extends ScaladinTestSuite {

  abstract class VaadinAbstractField extends com.vaadin.ui.AbstractField[String] with AbstractFieldMixin[String]

  var vaadinAbstractField: VaadinAbstractField = _
  var abstractField: AbstractField[String] = _

  before {
    vaadinAbstractField = mock[VaadinAbstractField]
    abstractField = new AbstractField[String](vaadinAbstractField) {}
  }

  test("conversionError") {
    Mockito.when(vaadinAbstractField.getConversionError).thenReturn("conversion error msg")

    assert(abstractField.conversionError === "conversion error msg")

    abstractField.conversionError = "new conversion error msg"
    Mockito.verify(vaadinAbstractField).setConversionError("new conversion error msg")
  }

  test("converter") {

    val converter = new Converter[String, Object]() {
      def convertToPresentation(value: Option[Object], targetType: Class[_ <: String], locale: Locale) = ???
      def convertToModel(value: Option[String], targetType: Class[_ <: Object], locale: Locale) = ???
    }

    Mockito.when(vaadinAbstractField.getConverter).thenReturn(converter.p)
    assert(abstractField.converter === Some(converter))

    abstractField.converter = converter
    Mockito.verify(vaadinAbstractField).setConverter(converter.p)

    abstractField.converter = classOf[Int]
    Mockito.verify(vaadinAbstractField).setConverter(classOf[Int])
  }

}
