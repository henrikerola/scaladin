package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala.Container
import vaadin.scala.IndexedContainer
import vaadin.scala.Item
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.BeforeAndAfter

@RunWith(classOf[JUnitRunner])
class IndexedContainerTests extends ContainerTestIndexed with BeforeAndAfter {

  var container: IndexedContainer = _

  before {
    container = new IndexedContainer()
  }

  test("addItem for an empty container") {
    assert(true === addItem.isDefined)
    assert(true === addItem.isDefined)
    assert(2 === container.size)

  }

  test("addItem with an existing id") {
    val newItem = container.addItem('someId)
    assert(newItem.get.isInstanceOf[Item])

    val existingId = addItem.get
    assert(None == container.addItem(existingId))

    assert(true === container.containsId('someId))
    assert(true === container.containsId(existingId))
  }

  test("removeItem") {
    assert(!container.removeItem('id))

    container.addItem('id)

    assert(container.removeItem('id))
  }

  test("container properties") {
    container.addContainerProperty('propertyId, classOf[String], Some(""))
    val item = container.addItem('itemId).get
    assert(1 === item.propertyIds.size)
    val property = item.getPropertyOption('propertyId).get
    assert(Some("") === property.value)
  }
}