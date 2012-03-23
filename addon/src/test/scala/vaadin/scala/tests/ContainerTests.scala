package scala.vaadin.scala.tests

import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit
import org.scalatest.FunSuite
import vaadin.scala._
import vaadin.scala.implicits._
import scala.collection.JavaConversions._
import com.vaadin.data.util.IndexedContainer

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

  test("container creation with one item") {
    val result = Container('itemId -> List())
    assert(1 === result.size)
    val item = result.getItem('itemId)
    assert(0 === item.getItemPropertyIds.size)
  }

  test("container item id filter with one item") {
    val containerWithOneItem = Container('itemId -> List())
    val result = containerWithOneItem \ 'itemId

    assert(null != result)
    assert(result.isInstanceOf[com.vaadin.data.Item])
  }

  test("container item filter with one item") {
    val containerWithOneItem = Container('itemId -> List('propertyId -> "value"))
    val result = containerWithOneItem filterItems (_.getItemPropertyIds.contains('propertyId))

    assert(1 === result.size)
  }

  test("container property id filter with one item and property") {
    val containerWithOneItem = Container('itemId -> List('propertyId -> "value"))
    val result = containerWithOneItem \\ 'propertyId

    assert(1 === result.size)
    assert("value" === result.head.getValue)
  }

  test("container property filter with one item and property") {
    val containerWithOneItem = Container('itemId -> List('propertyId -> "value"))
    val result = containerWithOneItem filterProperties (_.getValue == "value")

    assert(1 === result.size)
    assert("value" === result.head.getValue)
  }

  test("item property id filter with two properties") {
    val itemWithOneProperty = Item('propertyId1 -> "value1", 'propertyId2 -> "value2")
    val result = itemWithOneProperty \ 'propertyId1

    assert("value1" === result.getValue)
  }

  test("item property filter with two properties") {
    val itemWithOneProperty = Item('propertyId1 -> "value1", 'propertyId2 -> "value2")
    val result = itemWithOneProperty filterProperties (_.getValue == "value1")

    assert(1 === result.size)
    assert("value1" === result.head.getValue)
  }

  test("item property filter with two property values ") {
    val itemWithOneProperty = Item('propertyId1 -> "value1", 'propertyId2 -> "value2")
    val result = itemWithOneProperty filterProperties (_.getValue.asInstanceOf[String].startsWith("value"))
    val propertyValues = result values

    assert(2 === propertyValues.size)
    assert("value1" === propertyValues.head)
    assert("value2" === propertyValues.tail.head)
  }

  test("data filter return types") {
    val property = Property("propertyValue")
    val item = Item('propertyId1 -> "value1", 'propertyId2 -> "value2")
    val container = Container('itemId1 -> List('propertyId1 -> "value1", 'propertyId2 -> "value2"), 'itemId2 -> List())

    val itemProperty: com.vaadin.data.Property = item \ 'propertyId1
    val itemProperties: List[com.vaadin.data.Property] = item filterProperties (_.getValue.asInstanceOf[String].startsWith("value"))
    val property2: com.vaadin.data.Property = container \ 'itemId1 \ 'propertyId1
    val containerProperties: List[com.vaadin.data.Property] = container \\ 'propertyId1

    val itemPropertyValues: List[Any] = item filterProperties (_.getValue.asInstanceOf[String].startsWith("value")) values
    val containerPropertyValues: List[Any] = container \\ 'propertyId1 values
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
	  assert(42 == result.head.getValue)
  }
  
  test("Item property matching") {
	  import scala.collection.JavaConverters._

	  val item = Item('id1 -> "value2", 'id2 -> "value1")
	  item match {
	    case Item(Property("value1"), _*) => //not matching throws exception
	  }
  }
}