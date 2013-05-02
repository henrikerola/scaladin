package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
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

  test("getPosition returns None for non-contained Component") {
    assert(layout.getPosition(new Label) === None)
  }

  test("ComponentPosition.cssString") {
    layout.getPosition(label).get.cssString = "top: 10px; left: 100em"
    assert(layout.getPosition(label).get.cssString === "top:10.0px;left:100.0em;")
  }

  test("ComponentPosition.zIndex") {
    layout.getPosition(label).get.zIndex = 23
    assert(layout.getPosition(label).get.zIndex === 23)
  }

  test("ComponentPosition.top, Option[Measure]") {
    layout.getPosition(label).get.top = 10 px

    assert(layout.getPosition(label).get.top === (10 px))
  }

  test("ComponentPosition.top, Measure") {
    layout.getPosition(label).get.top = Measure(10, Units.px)
    assert(layout.getPosition(label).get.top === (10 px))
  }

  test("ComponentPosition.top, None") {
    layout.getPosition(label).get.top = None
    assert(layout.getPosition(label).get.top === None)
  }

  test("ComponentPosition.right, Option[Measure]") {
    layout.getPosition(label).get.right = 10 px;
    assert(layout.getPosition(label).get.right === (10 px))
  }

  test("ComponentPosition.right, Measure") {
    layout.getPosition(label).get.right = Measure(10, Units.px)
    assert(layout.getPosition(label).get.right === (10 px))
  }

  test("ComponentPosition.right, None") {
    layout.getPosition(label).get.right = None
    assert(layout.getPosition(label).get.right === None)
  }

  test("ComponentPosition.bottom, Option[Measure]") {
    layout.getPosition(label).get.bottom = 30 em

    assert(layout.getPosition(label).get.bottom === (30 em))
  }

  test("ComponentPosition.bottom, Measure") {
    layout.getPosition(label).get.bottom = Measure(30, Units.em)
    assert(layout.getPosition(label).get.bottom === (30 em))
  }

  test("ComponentPosition.bottom, None") {
    layout.getPosition(label).get.bottom = None
    assert(layout.getPosition(label).get.bottom === None)
  }

  test("ComponentPosition.left, Option[Measure]") {
    layout.getPosition(label).get.left = 100 pct

    assert(layout.getPosition(label).get.left === (100 pct))
  }

  test("ComponentPosition.left, Measure") {
    layout.getPosition(label).get.left = Measure(100, Units.pct);
    assert(layout.getPosition(label).get.left === (100 pct))
  }

  test("ComponentPosition.left, None") {
    layout.getPosition(label).get.left = None
    assert(layout.getPosition(label).get.left === None)
  }

}