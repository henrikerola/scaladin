package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.util.Date
import java.util.Locale

@RunWith(classOf[JUnitRunner])
class ConverterTests extends FunSuite {

  class MyConverter extends Converter[String, Date] {
    override def convertToModel(value: String, locale: Locale): Date = {
      new Date
    }
    override def convertToPresentation(value: Date, locale: Locale): String = {
      ""
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

}