package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala.FunctionProperty
import vaadin.scala.Property
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FunctionPropertyTests extends FunSuite {

  test("function property with getter") {
    val fp: Property[String] = new FunctionProperty(_ => "testValue")
    assert(Some("testValue") === fp.value)
  }

  test("function property with getter and setter") {
    var y: String = "Start"

    val fp: Property[String] = new FunctionProperty[String](_ => "testValue", x => (y += x))

    fp.value = "End"

    assert("StartEnd" === y)
  }

  test("function property without setter is readonly") {
    val fp: Property[String] = new FunctionProperty(_ => "testValue")
    assert(fp.readOnly === true)

  }

  test("function property with setter is not readonly") {
    var y: String = "Start"
    val fp: Property[String] = new FunctionProperty[String](_ => "testValue", x => (y += x))

    assert(fp.readOnly === false)

  }
}
