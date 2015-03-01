package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CheckBoxTests extends FunSuite {

  test("Default constructor") {
    val checkBox = new CheckBox()
    assert(checkBox.caption === None)
    assert(checkBox.value === Some(false))
    assert(checkBox.immediate === false)
    assert(checkBox.icon === None)
    assert(checkBox.p.getStyleName === "")
    assert(checkBox.enabled === true)
    assert(checkBox.description === Some(""))
  }

  test("value") {
    val checkBox = new CheckBox()

    assert(checkBox.value === Some(false))

    checkBox.value = true
    assert(checkBox.value === Some(true))
  }

  test("booleanValue") {
    val checkBox = new CheckBox()

    assert(checkBox.booleanValue === false)

    checkBox.value = true
    assert(checkBox.booleanValue === true)
  }

  test("null value from Java is considered as false in Scaladin") {
    val checkBox = new CheckBox

    checkBox.p.setValue(null)

    assert(!checkBox.booleanValue)
    assert(Some(false) == checkBox.value)
  }

  test("property binding") {
    val property = new ObjectProperty[Boolean](true)

    val checkBox = new CheckBox
    checkBox.property = property

    assert(checkBox.booleanValue)
    assert(checkBox.p.getValue)

    property.value = false
    assert(!checkBox.booleanValue)
    assert(!checkBox.p.getValue)
  }

  // TODO test focus and blur listeners
}