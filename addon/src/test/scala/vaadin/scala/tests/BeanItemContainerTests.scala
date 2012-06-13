package vaadin.scala.tests

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import vaadin.scala._
import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite

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
}