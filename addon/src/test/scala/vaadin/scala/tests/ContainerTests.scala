package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala.Container
import vaadin.scala.Container.Indexed
import vaadin.scala.Container.Ordered
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ContainerTests extends FunSuite {

  test("container creation with one item and one property") {
    val result = Container('itemId -> List('propertyId -> "foobar"))

    assert(1 === result.size)
    val item = result.getItem('itemId)
    assert(1 === item.propertyIds.size)
    val property = item.getProperty('propertyId)
    assert(classOf[String] === property.getType)
    assert(Some("foobar") === property.value)
  }

  test("container creation with one item") {
    val result = Container('itemId -> List())
    assert(1 === result.size)
    val item = result.getItem('itemId)
    assert(0 === item.propertyIds.size)
  }
}

trait ContainerTestBase extends FunSuite {

  def container: Container

  def addItem(): Option[Any] = container.addItemOption

}

trait ContainerTestAddItem extends ContainerTestBase {
  test("addItem without an id") {

    val item1Id = addItem()
    assert(None != item1Id)
    assert(None != container.getItemOption(item1Id.get))

  }
}

trait ContainerTestAddItemWithId extends ContainerTestBase {
  test("addItem with and id") {
    val item2 = container.addItem('id)
    assert(None != item2)
  }
}

trait ContainerTestItemIdsAndContainsId extends ContainerTestBase {
  test("itemIds and containsId") {
    val itemId = addItem get

    assert(container.containsId(itemId), "Container didn't contain item %s".format(itemId))
    assert(container.itemIds.exists(_ == itemId))
    assert(!container.containsId('id2))
  }
}

trait ContainerTestOrdered extends ContainerTestBase {

  override def container: Container.Ordered

  test("next/prev itemid") {

    val itemId1 = addItem get
    val itemId2 = addItem get

    assert(container.nextItemId(itemId1) === itemId2)
    assert(container.prevItemId(itemId2) === itemId1)
  }

  test("last/first itemid") {

    val itemId1 = addItem get
    val itemId2 = addItem get

    assert(container.firstItemId === itemId1)
    assert(container.lastItemId === itemId2)
    assert(container.isFirstId(itemId1))
    assert(container.isLastId(itemId2))
  }
}

trait ContainerTestIndexed extends ContainerTestOrdered {

  override def container: Container.Indexed

  test("indexOf/getByIndex") {

    val itemId1 = addItem get
    val itemId2 = addItem get

    assert(container.indexOfId(itemId1) === 0)
    assert(container.indexOfId(itemId2) === 1)
    assert(container.getIdByIndex(0) === itemId1)
    assert(container.getIdByIndex(1) === itemId2)
  }
}

trait ContainerTestHierarchical extends ContainerTestBase {

  override def container: Container.Hierarchical

  test("setParent / children checks") {
    val parent = container.addItem
    val child = container.addItem

    container.setParent(child -> parent)
    assert(container.getChildren(parent).size === 1)
    assert(container.getChildren(parent).head === child)

    assert(container.hasChildren(parent))
    assert(container.isRoot(parent))

    container.setParent(child -> null)
    assert(container.getChildren(parent).isEmpty)
  }

  test("childrenAllowed") {
    val parent = container.addItem
    val child = container.addItem

    container.setChildrenAllowed(parent -> true)
    assert(container.isChildrenAllowed(parent) === true)

    container.setChildrenAllowed(parent -> false)
    assert(container.isChildrenAllowed(parent) === false)
  }
}