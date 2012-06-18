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

  test("position returns None for non-contained Component") {
    assert(layout.position(new Label) === None)
  }

  test("ComponentPosition.cssString") {
    layout.position(label).get.cssString = "top: 10px; left: 100em";
    assert(layout.position(label).get.cssString === "top:10.0px;left:100.0em;")
  }

  test("ComponentPosition.zIndex") {
    layout.position(label).get.zIndex = 23;
    assert(layout.position(label).get.zIndex === 23)
  }

  test("ComponentPosition.top, Option[Measure]") {
    layout.position(label).get.top = 10 px;
    assert(layout.position(label).get.top === (10 px))
  }

  test("ComponentPosition.top, Measure") {
    layout.position(label).get.top = Measure(10, Units.px)
    assert(layout.position(label).get.top === (10 px))
  }

  test("ComponentPosition.top, None") {
    layout.position(label).get.top = None
    assert(layout.position(label).get.top === None)
  }

  test("ComponentPosition.right, Option[Measure]") {
    layout.position(label).get.right = 10 px;
    assert(layout.position(label).get.right === (10 px))
  }

  test("ComponentPosition.right, Measure") {
    layout.position(label).get.right = Measure(10, Units.px)
    assert(layout.position(label).get.right === (10 px))
  }

  test("ComponentPosition.right, None") {
    layout.position(label).get.right = None
    assert(layout.position(label).get.right === None)
  }

  test("ComponentPosition.bottom, Option[Measure]") {
    layout.position(label).get.bottom = 30 em;
    assert(layout.position(label).get.bottom === (30 em))
  }

  test("ComponentPosition.bottom, Measure") {
    layout.position(label).get.bottom = Measure(30, Units.em);
    assert(layout.position(label).get.bottom === (30 em))
  }

  test("ComponentPosition.bottom, None") {
    layout.position(label).get.bottom = None
    assert(layout.position(label).get.bottom === None)
  }

  test("ComponentPosition.left, Option[Measure]") {
    layout.position(label).get.left = 100 pct;
    assert(layout.position(label).get.left === (100 pct))
  }

  test("ComponentPosition.left, Measure") {
    layout.position(label).get.left = Measure(100, Units.pct);
    assert(layout.position(label).get.left === (100 pct))
  }

  test("ComponentPosition.left, None") {
    layout.position(label).get.left = None
    assert(layout.position(label).get.left === None)
  }

}