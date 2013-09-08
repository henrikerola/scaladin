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
    case class Foo(@BeanProperty var bar: String, @BeanProperty var baz: Int)

    val bean = new Foo("test", 123)
    val container = new BeanItemContainer(bean :: Nil)
    assert(container.getItem(bean).propertyIds.toSeq === List("bar", "baz").toSeq)
  }
}