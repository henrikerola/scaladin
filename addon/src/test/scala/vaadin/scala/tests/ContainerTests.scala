package vaadin.scala.tests

import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit
import org.scalatest.FunSuite
import vaadin.scala._
import vaadin.scala.implicits._
import scala.collection.JavaConversions._
import com.vaadin.data.util.IndexedContainer
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ContainerTests extends FunSuite {

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

  test("item creation with one property") {
    val result = Item('testId -> "foobar")
    assert(1 === result.propertyIds.size)
    val property = result.property('testId).get
    assert(classOf[String] === property.getType)
    assert(Some("foobar") === property.value)
  }

  test("item creation with three properties") {
    val result = Item('testId1 -> "foobar1", 'testId2 -> "foobar2", 'testId3 -> "foobar3")
    assert(3 === result.propertyIds.size)
    for (propertyId <- result.propertyIds) {
      val property = result.property(propertyId).get
      assert(classOf[String] === property.getType)
      assert(true === property.value.get.asInstanceOf[String].startsWith("foobar"))
    }
  }

  test("container creation with one item and one property") {
    val result = Container('itemId -> List('propertyId -> "foobar"))

    assert(1 === result.size)
    val item = result.item('itemId).get
    assert(1 === item.propertyIds.size)
    val property = item.property('propertyId).get
    assert(classOf[String] === property.getType)
    assert(Some("foobar") === property.value)
  }

  test("container creation with one item") {
    val result = Container('itemId -> List())
    assert(1 === result.size)
    val item = result.item('itemId).get
    assert(0 === item.propertyIds.size)
  }

  test("container item id filter with one item") {
    val containerWithOneItem = Container.filterable('itemId -> List())
    val result = containerWithOneItem \ 'itemId

    assert(None != result)
    assert(result.get.isInstanceOf[Item])
  }

  test("container item filter with one item") {
    val containerWithOneItem = Container.filterable('itemId -> List('propertyId -> "value"))
    val result = containerWithOneItem filterItems (_.propertyIds.contains('propertyId))

    assert(1 === result.size)
  }

  test("container property id filter with one item and property") {
    val containerWithOneItem = Container.filterable('itemId -> List('propertyId -> "value"))
    val result = containerWithOneItem \\ 'propertyId

    assert(1 === result.size)
    assert(Some("value") === result.head.value)
  }

  test("container property filter with one item and property") {
    val containerWithOneItem = Container.filterable('itemId -> List('propertyId -> "value"))
    val result = containerWithOneItem filterProperties (_.value == Some("value"))

    assert(1 === result.size)
    assert(Some("value") === result.head.value)
  }

  test("item property id filter with two properties") {
    val itemWithOneProperty = Item.filterable('propertyId1 -> "value1", 'propertyId2 -> "value2")
    val result = itemWithOneProperty \ 'propertyId1 get

    assert(Some("value1") === result.value)
  }

  test("item property filter with two properties") {
    val itemWithOneProperty = Item.filterable('propertyId1 -> "value1", 'propertyId2 -> "value2")
    val result = itemWithOneProperty filterProperties (_.value == Some("value1"))

    assert(1 === result.size)
    assert(Some("value1") === result.head.value)
  }

  test("item property filter with two property values ") {
    val itemWithOneProperty = Item.filterable('propertyId1 -> "value1", 'propertyId2 -> "value2")
    val result = itemWithOneProperty filterProperties (_.value.get.asInstanceOf[String].startsWith("value"))
    val propertyValues = result values

    assert(2 === propertyValues.size)
    assert(Some("value1") === propertyValues.head)
    assert(Some("value2") === propertyValues.tail.head)
  }

  test("data filter return types") {
    val property = Property("propertyValue")
    val item = Item.filterable('propertyId1 -> "value1", 'propertyId2 -> "value2")
    val container = Container.filterable('itemId1 -> List('propertyId1 -> "value1", 'propertyId2 -> "value2"), 'itemId2 -> List())

    val itemProperty: Property = item \ 'propertyId1 get
    val itemProperties: List[Property] = item filterProperties (_.value.get.asInstanceOf[String].startsWith("value"))
    val property2: Property = container \ 'itemId1 \ 'propertyId1 get
    val containerProperties: List[Property] = container \\ 'propertyId1

    val itemPropertyValues: List[Any] = item filterProperties (_.value.get.asInstanceOf[String].startsWith("value")) values
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
    assert(Some(42) == result.head.value)
  }

  test("Item property matching") {
    import scala.collection.JavaConverters._

    val item = Item('id1 -> "value1", 'id2 -> "value2")
    item match {
      case Item(Property("value1"), _*) => //not matching throws exception
    }
  }
}