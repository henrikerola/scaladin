package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import com.vaadin.terminal.Sizeable
import com.vaadin.ui.Layout.MarginInfo
import com.vaadin.ui.Alignment

class LayoutTests extends FunSuite {

  test("VerticalLayout, constructor with all params but without names") {
    val layout = new VerticalLayout(10 px, 100 em, true, true, "Caption", "Style")
    assert(layout.getWidth === 10)
    assert(layout.getWidthUnits === Sizeable.UNITS_PIXELS)
    assert(layout.getHeight === 100)
    assert(layout.getHeightUnits === Sizeable.UNITS_EM)
    assert(layout.getMargin === new MarginInfo(true, true, true, true))
    assert(layout.isSpacing === true)
    assert(layout.getCaption === "Caption")
    assert(layout.getStyleName === "Style")

  }

  test("VerticalLayout, default constructor") {
    val layout = new VerticalLayout()
    assert(layout.getWidth === 100)
    assert(layout.getWidthUnits === Sizeable.UNITS_PERCENTAGE)
    assert(layout.getHeight === -1)
    assert(layout.getHeightUnits === Sizeable.UNITS_PIXELS)
    assert(layout.getMargin === new MarginInfo(false, false, false, false))
    assert(layout.isSpacing === false)
    assert(layout.getCaption === null)
    assert(layout.getStyleName === "")
  }
  
  test("VerticalLayout, add") {
    val layout = new VerticalLayout
    
    val button = new Button
    layout.add(button, 2, Alignment.BOTTOM_CENTER)
    
    assert(layout.getComponent(0) === button)
    assert(layout.getExpandRatio(button) === 2)
    assert(layout.getComponentAlignment(button) === Alignment.BOTTOM_CENTER)

    assert(layout.getComponentIndex(button) === 0)
  }
  
  test("VerticalLayout, add with index") {
    val layout = new VerticalLayout
    layout.add(new Label)
    layout.add(new Label)
    layout.add(new Label)
    
    val button = new Button
    layout.add(button, index = 2)

    assert(layout.getComponentIndex(button) === 2)
  }

}