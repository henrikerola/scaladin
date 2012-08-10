package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala.Container
import vaadin.scala.Container.Indexed
import vaadin.scala.Container.Ordered

trait ContainerTest extends FunSuite {

  def container: Container

  def addItem: Option[Any] = container.addItem

}

trait ContainerTestAddItem extends ContainerTest {
  test("addItem without an id") {

    val item1Id = container.addItem
    assert(None != item1Id)
    assert(None != container.item(item1Id.get))

  }
}

trait ContainerTestAddItemWithId extends ContainerTest {
  test("addItem with and id") {
    val item2 = container.addItem('id)
    assert(None != item2)
  }
}

trait ContainerTestItemIdsAndContainsId extends ContainerTest {
  test("itemIds and containsId") {
    val itemId = addItem get

    assert(container.containsId(itemId), "Container didn't contain item %s".format(itemId))
    assert(container.itemIds.exists(_ == itemId))
    assert(!container.containsId('id2))
  }
}

trait ContainerTestOrdered extends ContainerTest {

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