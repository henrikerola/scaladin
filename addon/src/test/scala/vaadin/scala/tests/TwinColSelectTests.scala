package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TwinColSelectTests extends FunSuite {

  test("rows") {
    val twinColSelect = new TwinColSelect

    assert(twinColSelect.rows === 0)

    twinColSelect.rows = 7
    assert(twinColSelect.rows === 7)
  }

  test("columns") {
    val twinColSelect = new TwinColSelect

    assert(twinColSelect.columns === 0)

    twinColSelect.columns = 5
    assert(twinColSelect.columns === 5)
  }

  test("rightColumnCaption, String") {
    val twinColSelect = new TwinColSelect

    twinColSelect.rightColumnCaption = "caption"
    assert(twinColSelect.rightColumnCaption === Some("caption"))
  }

  test("rightColumnCaption, Some") {
    val twinColSelect = new TwinColSelect

    twinColSelect.rightColumnCaption = Some("caption")
    assert(twinColSelect.rightColumnCaption === Some("caption"))
  }

  test("rightColumnCaption, None") {
    val twinColSelect = new TwinColSelect

    twinColSelect.rightColumnCaption = None
    assert(twinColSelect.rightColumnCaption === None)
  }

  test("leftColumnCaption, String") {
    val twinColSelect = new TwinColSelect

    twinColSelect.leftColumnCaption = "caption"
    assert(twinColSelect.leftColumnCaption === Some("caption"))
  }

  test("leftColumnCaption, Some") {
    val twinColSelect = new TwinColSelect

    twinColSelect.leftColumnCaption = Some("caption")
    assert(twinColSelect.leftColumnCaption === Some("caption"))
  }

  test("leftColumnCaption, None") {
    val twinColSelect = new TwinColSelect

    twinColSelect.leftColumnCaption = None
    assert(twinColSelect.leftColumnCaption === None)
  }
  
  test("container") {
    val twinColSelect = new TwinColSelect
    assert(twinColSelect.container.get.isInstanceOf[IndexedContainer])
  }
}