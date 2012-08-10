package vaadin.scala.tests

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest._
import vaadin.scala._

@RunWith(classOf[JUnitRunner])
class AbstractSelectTests extends FunSuite with BeforeAndAfter {

  var select: AbstractSelect = _
  before {
    select = new ComboBox
  }

  test("NewItemHandler default set correctly") {
    val newItemHandler = select.newItemHandler
    assert(newItemHandler.get.isInstanceOf[DefaultNewItemHandler], "Default NewItemHandler was %s instead".format(newItemHandler))
  }

  test("NewItemHandler delegatates calls to Scala layer") {
    var called = false
    val testCaption = "test"
    select.newItemHandler = new NewItemHandler {
      def addNewItem(newItemCaption: String) = { called = true; assert(testCaption === newItemCaption, "Wrong caption: %s".format(newItemCaption)) }
    }

    select.newItemHandler.get.p.addNewItem(testCaption)
    assert(called, "Scaladin newItemHandler never called")
  }
}