package vaadin.scala.tests

import vaadin.scala.converter._
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.util.Date
import java.util.Locale

@RunWith(classOf[JUnitRunner])
class ConverterTests extends FunSuite {

  class MyConverter extends Converter[String, Date] {
    override def convertToModel(value: Option[String], targetType: Class[_ <: Date], locale: Locale): Option[Date] = {
      Some(new Date)
    }
    override def convertToPresentation(value: Option[Date], targetType: Class[_ <: String], locale: Locale): Option[String] = {
      Some("")
    }
  }

  test("modelType") {
    val myConverter = new MyConverter
    assert(myConverter.modelType === classOf[Date])
  }

  test("p.getModelType") {
    val myConverter = new MyConverter
    assert(myConverter.p.getModelType === classOf[Date])
  }

  test("presentationType") {
    val myConverter = new MyConverter
    assert(myConverter.presentationType === classOf[String])
  }

  test("p.getPresentationType") {
    val myConverter = new MyConverter
    assert(myConverter.p.getPresentationType === classOf[String])
  }

  val locale = Locale.UK

  test("DateToLongConverter") {
    val converter = new DateToLongConverter

    assert(converter.presentationType === classOf[java.util.Date])
    assert(converter.modelType === classOf[java.lang.Long])

    assert(converter.convertToModel(None, classOf[java.lang.Long], locale) === None)
    assert(converter.convertToPresentation(None, classOf[java.util.Date], locale) === None)

    assert(converter.convertToModel(Some(new Date(1358627734477L)), classOf[java.lang.Long], locale) === Some(1358627734477L))
    assert(converter.convertToPresentation(Some(1358627734477L), classOf[java.util.Date], locale) === Some(new Date(1358627734477L)))
  }

  test("DateToSQLConverter") {
    val converter = DefaultConverterFactory.createConverter[java.util.Date, java.sql.Date]
    assert(converter.isDefined)
    assert(converter.get.isInstanceOf[DateToSqlDateConverter])
    assert(converter.get.presentationType === classOf[java.util.Date])
    assert(converter.get.modelType === classOf[java.sql.Date])
  }

  test("OptionConverter") {
    val optionConverter = new TypeToOptionConverter(new StringToIntegerConverter)

    assert(Some("2") === optionConverter.convertToPresentation(Some(Some(2)), null, Locale.US))

    assert(Some(Some(2)) === optionConverter.convertToModel(Some("2"), null, Locale.US))

  }
}