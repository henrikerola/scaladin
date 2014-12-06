package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import com.vaadin.server.Sizeable
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import vaadin.scala.Layout.Margin

@RunWith(classOf[JUnitRunner])
class LayoutTests extends FunSuite {

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