package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala.FunctionProperty
import vaadin.scala.Property
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FunctionPropertyTests extends FunSuite {

  test("function property with getter") {
    val fp: Property = new FunctionProperty(_ => "testValue")
    assert("testValue" === fp.value)
  }

  test("function property with getter and setter") {
    var y: String = "Start"

    val fp: Property = new FunctionProperty[String](_ => "testValue", x => (y += x))

    fp.value_=("End")

    assert("StartEnd" === y)
  }
}