package vaadin.scala.tests
import org.scalatest._
import vaadin.scala._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ItemTests extends FunSuite with BeforeAndAfter {

  case class TestBean(name: String)
  var viewer: Item.Viewer = _

  before {
    viewer = new Form
  }

  test("Item instance stays the same between set and get") {

    val item = new BeanItem(new TestBean("foo"))
    assert(viewer.item === None, "Item wasn't None after creation")

    viewer.item = item

    assert(viewer.item != None, "Item was None after setting")
    assert(viewer.item.get.isInstanceOf[BeanItem[_]], "Item was of type %s instead of BeanItem".format(viewer.item))
  }
}