package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import com.vaadin.server.Sizeable
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import vaadin.scala.Layout.Margin

@RunWith(classOf[JUnitRunner])
class LayoutTests extends FunSuite {

  test("VerticalLayout, default constructor") {
    val layout = new VerticalLayout()
    assert(layout.p.getWidth === 100)
    assert(layout.p.getWidthUnits === Sizeable.Unit.PERCENTAGE)
    assert(layout.p.getHeight === -1)
    assert(layout.p.getHeightUnits === Sizeable.Unit.PIXELS)
    assert(layout.margin === Margin(false, false, false, false))
    assert(layout.spacing === false)
    assert(layout.caption === None)
    assert(layout.p.getStyleName === "")
  }

  test("VerticalLayout, add") {
    val layout = new VerticalLayout

    val label = new Label
    layout.add(label, 2, Alignment.BottomCenter)

    assert(layout.p.getComponent(0) === label.p)
    assert(layout.p.getExpandRatio(label.p) === 2)
    assert(layout.getAlignment(label) === Alignment.BottomCenter)
    assert(layout.p.getComponentAlignment(label.p) === com.vaadin.ui.Alignment.BOTTOM_CENTER)

    assert(layout.p.getComponentIndex(label.p) === 0)
  }

  test("VerticalLayout, add with index") {
    val layout = new VerticalLayout
    layout.add(new Label())
    layout.add(new Label())
    layout.add(new Label())

    val button = new Button
    layout.add(button, index = 2)

    assert(layout.p.getComponentIndex(button.p) === 2)
  }

  test("HorizontalLayout, default constructor") {
    val layout = new HorizontalLayout()
    assert(layout.p.getWidth === -1)
    assert(layout.p.getWidthUnits === Sizeable.Unit.PIXELS)
    assert(layout.p.getHeight === -1)
    assert(layout.p.getHeightUnits === Sizeable.Unit.PIXELS)
    assert(layout.margin === Margin(false, false, false, false))
    assert(layout.spacing === false)
    assert(layout.caption === None)
    assert(layout.p.getStyleName === "")
  }

  test("FormLayout, default constructor") {
    val layout = new FormLayout()
    assert(layout.width === (100 pct))
    assert(layout.height === None)
    assert(layout.margin === Margin(true, false, true, false))
    assert(layout.spacing === true)
    assert(layout.p.getStyleName === "")
  }

  test("full size object constructor") {
    val layout = VerticalLayout.fullSized()
    assert(100.pct === layout.width)
    assert(100.pct === layout.height)
  }

  test("full size object constructor with components") {
    val component1 = new TextField
    val component2 = new Button
    val layout = VerticalLayout.fullSized(component1, component2)
    assert(100.pct === layout.width)
    assert(100.pct === layout.height)
    assert(component1 === layout.components.head)
    assert(component2 === layout.components.tail.head)
  }

  test("undefined size object constructor") {
    val layout = VerticalLayout.undefinedSized()
    assert(None === layout.width)
    assert(None === layout.height)
  }

  test("undefined size object constructor with components") {
    val component1 = new TextField
    val component2 = new Button
    val layout = VerticalLayout.undefinedSized(component1, component2)
    assert(None === layout.width)
    assert(None === layout.height)
    assert(component1 === layout.components.head)
    assert(component2 === layout.components.tail.head)
  }
}