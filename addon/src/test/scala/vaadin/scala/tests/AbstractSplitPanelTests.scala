package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite

class AbstractSplitPanelTests extends FunSuite {

  test("default splitPosition") {
    val splitPanel = new VerticalSplitPanel

    assert(splitPanel.splitPosition === Measure(50, Units.pct))
    assert(splitPanel.p.getSplitPosition === 50)
    assert(splitPanel.p.getSplitPositionUnit() === com.vaadin.terminal.Sizeable.UNITS_PERCENTAGE)
  }

  test("set splitPosition to 100px") {
    val splitPanel = new VerticalSplitPanel

    splitPanel.splitPosition = 100 px;
    assert(splitPanel.splitPosition === Measure(100, Units.px))
    assert(splitPanel.p.getSplitPosition === 100)
    assert(splitPanel.p.getSplitPositionUnit() === com.vaadin.terminal.Sizeable.UNITS_PIXELS)
  }

  test("set splitPosition to None") {
    val splitPanel = new VerticalSplitPanel

    splitPanel.splitPosition = None;
    assert(splitPanel.splitPosition === Measure(50, Units.pct))
    assert(splitPanel.p.getSplitPosition === 50)
    assert(splitPanel.p.getSplitPositionUnit() === com.vaadin.terminal.Sizeable.UNITS_PERCENTAGE)
  }

}