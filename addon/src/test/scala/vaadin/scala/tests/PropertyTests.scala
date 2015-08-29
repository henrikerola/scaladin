package vaadin.scala.tests

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import vaadin.scala._
import vaadin.scala.implicits._

@RunWith(classOf[JUnitRunner])
class PropertyTests extends FunSuite {
  test("property creation with a string") {
    val result = Property("foobar")
    assert(classOf[String] === result.getType)
    assert(Some("foobar") === result.value)
  }

  test("property creation with a symbol") {
    val result = Property('foobar)
    assert(classOf[Symbol] === result.getType)
    assert(Some('foobar) === result.value)
  }

  test("Property pattern matching") {
    val property1 = Property("test")
    property1 match {
      case Property(x: String) => assert("test" === x)
    }

    property1 match {
      case Property("test") => //not matching throws exception
    }

    val item = Item('id1 -> "value1", 'id2 -> 42)
    val result = item.filterProperties(x => x match {
      case Property(42) => true
      case _ => false
    })
    assert(1 === result.size)
    assert(Some(42) == result.head.value)
  }

  test("implicit type") {
    val property = Property(new java.sql.Date(0L))
    assert(classOf[java.sql.Date] === property.getType)
  }

  test("explicit type") {
    val property = Property[java.util.Date](new java.sql.Date(0L))
    assert(classOf[java.util.Date] === property.getType)
  }
}