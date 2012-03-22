package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite
import scala.reflect.Method

class AbstractSplitPanelTests extends FunSuite {

  implicit val testWr = new WrapperRegistry

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

  val splitClickListener1 = (e: SplitterClickEvent) => println(e.component)
  val splitClickListener2 = (e: SplitterClickEvent) => println

  test("splitterClickListeners, add and remove of listeners") {
    val splitPanel = new VerticalSplitPanel

    assert(splitPanel.splitterClickListeners.size == 0)

    splitPanel.splitterClickListeners += splitClickListener1
    assert(splitPanel.splitterClickListeners.size == 1)

    splitPanel.splitterClickListeners += splitClickListener2
    assert(splitPanel.splitterClickListeners.size == 2)

    splitPanel.splitterClickListeners -= splitClickListener1
    assert(splitPanel.splitterClickListeners.size == 1)

    splitPanel.splitterClickListeners.remove(splitClickListener2)
    assert(splitPanel.splitterClickListeners.size == 0)
  }

  test("splitterClickListeners, iterator") {
    val splitPanel = new VerticalSplitPanel

    splitPanel.splitterClickListeners += splitClickListener1
    splitPanel.splitterClickListeners += println
    splitPanel.splitterClickListeners += splitClickListener2

    val iter = splitPanel.splitterClickListeners.iterator

    assert(iter.next === splitClickListener1)
    iter.next
    assert(iter.next === splitClickListener2)
    assert(!iter.hasNext)
  }

  /*-
  test("splitterClickListeners, fire click") {
    val splitPanel = new VerticalSplitPanel

    var event: Option[SplitterClickEvent] = None
    var invokes = 0;
    splitPanel.splitterClickListeners += { (e: SplitterClickEvent) =>
      invokes = invokes + 1;
      event = Some(e)
    }

    val method = classOf[com.vaadin.ui.AbstractComponent].getDeclaredMethod("fireEvent", classOf[com.vaadin.ui.Component.Event]);
    method.setAccessible(true)
    method.invoke(splitPanel.p, new splitPanel.p.SplitterClickEvent(splitPanel.p, null));

    assert(invokes === 1)
    assert(event.component === splitPanel)
  }*/

}