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

  test("NewItemHandler default") {
    val newItemHandler = select.newItemHandler
    assert(newItemHandler.get.isInstanceOf[DefaultNewItemHandler], "Default NewItemHandler was %s instead".format(newItemHandler))
  }

  //TODO newitemhandler tests
}