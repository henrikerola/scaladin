package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import vaadin.scala.Layout.Margin

@RunWith(classOf[JUnitRunner])
class PanelTests extends FunSuite {

  test("content, VerticalLayout is Panel's default content") {
    val panel = new Panel

    assert(panel.content.get.isInstanceOf[VerticalLayout])
    assert(panel.content.get.asInstanceOf[VerticalLayout].margin === Margin(true, true, true, true))
  }

  test("content") {
    val panel = new Panel

    val layout = new VerticalLayout
    panel.content = layout
    assert(panel.content === Some(layout))
  }

  test("scrollLeft") {
    val panel = new Panel

    assert(panel.scrollLeft === 0)

    panel.scrollLeft = 126
    assert(panel.scrollLeft === 126)
  }

  test("scrollTop") {
    val panel = new Panel

    assert(panel.scrollTop === 0)

    panel.scrollTop = 37
    assert(panel.scrollTop === 37)
  }

}