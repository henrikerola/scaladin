package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import com.vaadin.terminal.Sizeable
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class LayoutTests extends FunSuite {

  test("VerticalLayout, default constructor") {
    val layout = new VerticalLayout()
    assert(layout.p.getWidth === 100)
    assert(layout.p.getWidthUnits === Sizeable.UNITS_PERCENTAGE)
    assert(layout.p.getHeight === -1)
    assert(layout.p.getHeightUnits === Sizeable.UNITS_PIXELS)
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
    assert(layout.alignment(label) === Alignment.BottomCenter)
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
    assert(layout.p.getWidthUnits === Sizeable.UNITS_PIXELS)
    assert(layout.p.getHeight === -1)
    assert(layout.p.getHeightUnits === Sizeable.UNITS_PIXELS)
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

  test("CssLayout, default constructor") {
    val layout = new CssLayout()
    assert(layout.p.getWidth === -1)
    assert(layout.p.getWidthUnits === Sizeable.UNITS_PIXELS)
    assert(layout.p.getHeight === -1)
    assert(layout.p.getHeightUnits === Sizeable.UNITS_PIXELS)
    assert(layout.margin === Margin(false, false, false, false))
    assert(layout.caption === None)
    assert(layout.p.getStyleName === "")
  }

  test("CssLayout, add") {
    val layout = new CssLayout
    val label = new Label
    
    layout.add(label, "my invalid css")
    assert(layout.components(label))
    
    assert(layout.p.getCss(label.p) === "my invalid css")
    assert(layout.css(label) === Some("my invalid css"))
    
    val label2 = new Label
    layout.add(label2)
    assert(layout.p.getCss(label2.p) === null)
    assert(layout.css(label2) === None)
  }
  
  test("CssLayout, function literal for css") {
    val layout = new CssLayout
    val label = new Label
    var cnt = 0;
    layout.add(label, {
      cnt = cnt + 1
      "" + cnt
    })
    assert(layout.css(label) === Some("2"))
    assert(layout.css(label) === Some("2"))
  }
  
  test("CssLayout, CSS must be removed from cssMap when component is removed from the layout") {
    val layout = new CssLayout
    val label = new Label
    val label2 = new Label
    
    layout.add(label, "my invalid css")
    assert(layout.cssMap.size === 1)
    
    layout.add(label2, "my invalid css")
    assert(layout.cssMap.size === 2)
    
    layout.add(new Label)
    assert(layout.cssMap.size === 2)
    
    
    layout.removeComponent(label)
    assert(layout.cssMap.size === 1)
    
    layout.components -= label2
    assert(layout.cssMap.size === 0)
  }
  
}