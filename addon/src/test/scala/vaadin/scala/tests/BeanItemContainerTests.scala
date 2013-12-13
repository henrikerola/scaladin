package vaadin.scala.tests

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import vaadin.scala._
import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import scala.beans.BeanProperty

@RunWith(classOf[JUnitRunner])
class BeanItemContainerTests extends FunSuite
    with BeforeAndAfter
    with ContainerTestAddItemWithId
    with ContainerTestItemIdsAndContainsId
    with ContainerTestIndexed {

  case class Foo(@BeanProperty var bar: String, @BeanProperty var baz: Int)

  var container: BeanItemContainer[Any] = _

  before {
    container = new BeanItemContainer[Any]
  }

  override def addItem = {
    val id = new AnyRef
    container.addItem(id)
    Option(id)
  }

  test("bean is set correctly") {
    val bean = new AnyRef()
    val item = new BeanItem(bean)

    assert(bean === item.bean)
  }

  test("propertyIds should return correct field names") {
    val bean = new Foo("test", 123)
    val container = new BeanItemContainer(bean :: Nil)
    assert(container.getItem(bean).propertyIds.toSeq === List("bar", "baz").toSeq)
  }

  test("getting item from container should return a bean item with correct bean") {
    val b1 = Foo("first", 1)
    val b2 = Foo("second", 2)

    val container = new BeanItemContainer[Foo](b1 :: b2 :: Nil)

    assert(container.getItem(b1).bean === b1)
    assert(container.getItem(b2).bean === b2)
  }

  test("adding a bean to container should return a bean item") {
    val bean = Foo("bar", 99)

    val container = new BeanItemContainer[Foo]()
    val beanItem = container.addBean(bean)

    assert(beanItem.bean === bean)
    assert(container.getItem(bean).bean === bean)
  }
}