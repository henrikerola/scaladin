package vaadin.scala.tests
import org.scalatest._
import vaadin.scala._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ItemTests extends FunSuite with BeforeAndAfter {

  case class TestBean(name: String)

  //TODO Vaadin FieldGroup doesn't implement Item.Viewer yet 
  var viewer: FieldGroup = _

  before {
    viewer = new FieldGroup
  }

  test("Item instance stays the same between set and get") {

    val item = new BeanItem(new TestBean("foo"))
    assert(viewer.item === None, "Item wasn't None after creation")

    viewer.item = item

    assert(viewer.item != None, "Item was None after setting")
    assert(viewer.item.get.isInstanceOf[BeanItem[_]], "Item was of type %s instead of BeanItem".format(viewer.item))
  }

  test("item creation with one property") {
    val result = Item('testId -> "foobar")
    assert(1 === result.propertyIds.size)
    val property = result.getPropertyOption('testId).get
    assert(classOf[String] === property.getType)
    assert(Some("foobar") === property.value)
  }

  test("item creation with three properties") {
    val result = Item('testId1 -> "foobar1", 'testId2 -> "foobar2", 'testId3 -> "foobar3")
    assert(3 === result.propertyIds.size)
    for (propertyId <- result.propertyIds) {
      val property = result.getPropertyOption(propertyId).get
      assert(classOf[String] === property.getType)
      assert(true === property.value.get.asInstanceOf[String].startsWith("foobar"))
    }
  }
}