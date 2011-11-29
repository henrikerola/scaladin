package scala.vaadin.scala.tests
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit
import org.scalatest.FunSuite
import vaadin.scala._
import scala.collection.JavaConversions._

class ContainerTests extends FunSuite {

  test("property creation with a string") {
    val result = Property("foobar")
    assert(classOf[String] === result.getType)
    assert("foobar" === result.getValue)
  }

  test("property creation with a symbol") {
    val result = Property('foobar)
    assert(classOf[Symbol] === result.getType)
    assert('foobar === result.getValue)
  }

  test("item creation with one property") {
    val result = Item('testId -> "foobar")
    assert(1 === result.getItemPropertyIds.size)
    val property = result.getItemProperty('testId)
    assert(classOf[String] === property.getType)
    assert("foobar" === property.getValue)
  }

  test("item creation with three properties") {
    val result = Item('testId1 -> "foobar1", 'testId2 -> "foobar2", 'testId3 -> "foobar3")
    assert(3 === result.getItemPropertyIds.size)
    for (propertyId <- result.getItemPropertyIds) {
      val property = result.getItemProperty(propertyId)
      assert(classOf[String] === property.getType)
      assert(true === property.getValue.asInstanceOf[String].startsWith("foobar"))
    }
  }

  test("container creation with one item and one property") {
    val result = Container('itemId -> List('propertyId -> "foobar"))

    assert(1 === result.size)
    val item = result.getItem('itemId)
    assert(1 === item.getItemPropertyIds.size)
    val property = item.getItemProperty('propertyId)
    assert(classOf[String] === property.getType)
    assert("foobar" === property.getValue)
  }
}