package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import org.scalatest.BeforeAndAfter
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CustomLayoutTests extends FunSuite with BeforeAndAfter {

  var layout: CustomLayout = _

  before {
    layout = new CustomLayout
  }

  test("templateName") {
    assert(layout.templateName === None)
    layout.templateName = "test"
    assert(layout.templateName === Some("test"))
  }

  test("templateContents, String") {
    assert(layout.templateContents === None)
    layout.templateContents = "<p><div location=\"mylocation\"></div></p>"
    assert(layout.templateContents === Some("<p><div location=\"mylocation\"></div></p>"))
  }

  test("add/component") {
    val label = new Label
    assert(layout.component("location") === None)
    layout.add(label, "location")
    assert(layout.component("location") === Some(label))
  }
}