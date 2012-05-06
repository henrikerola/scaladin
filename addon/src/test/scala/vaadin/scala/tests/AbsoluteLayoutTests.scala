package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import com.vaadin.terminal.Sizeable
import org.scalatest.BeforeAndAfter
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class AbsoluteLayoutTests extends FunSuite with BeforeAndAfter {

  var layout: AbsoluteLayout = _
  val label = new Label

  before {
    layout = new AbsoluteLayout
    layout.add(label)
  }

  test("position throws RuntimeException for non-contained Component") {
    intercept[RuntimeException] {
      layout.position(new Label)
    }
  }

  test("ComponentPosition.cssString") {
    layout.position(label).cssString = "top: 10px; left: 100em";
    assert(layout.position(label).cssString === "top:10.0px;left:100.0em;")
  }

  test("ComponentPosition.zIndex") {
    layout.position(label).zIndex = 23;
    assert(layout.position(label).zIndex === 23)
  }

  test("ComponentPosition.top, Option[Measure]") {
    layout.position(label).top = 10 px;
    assert(layout.position(label).top === (10 px))
  }

  test("ComponentPosition.top, Measure") {
    layout.position(label).top = Measure(10, Units.px)
    assert(layout.position(label).top === (10 px))
  }

  test("ComponentPosition.top, None") {
    layout.position(label).top = None
    assert(layout.position(label).top === None)
  }

  test("ComponentPosition.right, Option[Measure]") {
    layout.position(label).right = 10 px;
    assert(layout.position(label).right === (10 px))
  }

  test("ComponentPosition.right, Measure") {
    layout.position(label).right = Measure(10, Units.px)
    assert(layout.position(label).right === (10 px))
  }

  test("ComponentPosition.right, None") {
    layout.position(label).right = None
    assert(layout.position(label).right === None)
  }

  test("ComponentPosition.bottom, Option[Measure]") {
    layout.position(label).bottom = 30 em;
    assert(layout.position(label).bottom === (30 em))
  }

  test("ComponentPosition.bottom, Measure") {
    layout.position(label).bottom = Measure(30, Units.em);
    assert(layout.position(label).bottom === (30 em))
  }

  test("ComponentPosition.bottom, None") {
    layout.position(label).bottom = None
    assert(layout.position(label).bottom === None)
  }

  test("ComponentPosition.left, Option[Measure]") {
    layout.position(label).left = 100 pct;
    assert(layout.position(label).left === (100 pct))
  }

  test("ComponentPosition.left, Measure") {
    layout.position(label).left = Measure(100, Units.pct);
    assert(layout.position(label).left === (100 pct))
  }

  test("ComponentPosition.left, None") {
    layout.position(label).left = None
    assert(layout.position(label).left === None)
  }

}