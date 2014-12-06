package vaadin.scala.tests

import com.vaadin.server.Sizeable
import vaadin.scala.Layout.Margin
import vaadin.scala._

/**
 *
 * @author Henri Kerola / Vaadin
 */
class VerticalLayoutTests extends ScaladinTestSuite {

  var layout: VerticalLayout = _

  before {
    layout = new VerticalLayout
  }

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

  test("defaultComponentAlignment") {
    assert(Alignment.TopLeft == layout.defaultComponentAlignment)

    layout.defaultComponentAlignment = Alignment.BottomCenter
    assert(Alignment.BottomCenter == layout.defaultComponentAlignment)
  }

}
