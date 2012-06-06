package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala.Container
import vaadin.scala.ContainerIndexed
import vaadin.scala.ContainerOrdered

trait ContainerTest extends FunSuite {

  def container: Container

  def addItem: Option[Any] = container.addItem

  test("addItem with and without id") {
    try {

      val item1Id = container.addItem
      val item2 = container.addItem('id)

      assert(None != item1Id)
      assert(None != container.item(item1Id.get))

      assert(None != item2)
    } catch {
      case uo: UnsupportedOperationException => Console.println("Operation not supported by this container", uo)
    }
  }

  test("itemIds and containsId") {
    try {

      val itemId = addItem get

      assert(container.containsId(itemId), "Container didn't contain item %s".format(itemId))
      assert(container.itemIds.exists(_ == itemId))
      assert(!container.containsId('id2))
    } catch {
      case uo: UnsupportedOperationException => Console.println("Operation not supported by this container", uo)
    }
  }
}

trait ContainerOrderedTest extends ContainerTest {

  override def container: ContainerOrdered

  test("next/prev itemid") {
    try {

      val itemId1 = addItem get
      val itemId2 = addItem get

      assert(container.nextItemId(itemId1) === itemId2)
      assert(container.prevItemId(itemId2) === itemId1)
    } catch {
      case uo: UnsupportedOperationException => Console.println("Operation not supported by this container", uo)
    }
  }

  test("last/first itemid") {
    try {

      val itemId1 = addItem get
      val itemId2 = addItem get

      assert(container.firstItemId === itemId1)
      assert(container.lastItemId === itemId2)
      assert(container.isFirstId(itemId1))
      assert(container.isLastId(itemId2))
    } catch {
      case uo: UnsupportedOperationException => Console.println("Operation not supported by this container", uo)
    }
  }
}

trait ContainerIndexedTest extends ContainerOrderedTest {

  override def container: ContainerIndexed

  test("indexOf/getByIndex") {
    try {

      val itemId1 = addItem get
      val itemId2 = addItem get

      assert(container.indexOfId(itemId1) === 0)
      assert(container.indexOfId(itemId2) === 1)
      assert(container.getIdByIndex(0) === itemId1)
      assert(container.getIdByIndex(1) === itemId2)
    } catch {
      case uo: UnsupportedOperationException => Console.println("Operation not supported by this container", uo)
    }
  }
}