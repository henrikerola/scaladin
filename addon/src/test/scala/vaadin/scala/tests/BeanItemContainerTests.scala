package vaadin.scala.tests

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import vaadin.scala._
import org.scalatest.BeforeAndAfter

@RunWith(classOf[JUnitRunner])
class BeanItemContainerTests extends ContainerIndexedTest with BeforeAndAfter {

  var container: BeanItemContainer[Any] = _

  before {
    container = new BeanItemContainer[Any](classOf[Any])
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