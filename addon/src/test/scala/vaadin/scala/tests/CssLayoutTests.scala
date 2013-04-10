package vaadin.scala.tests

import vaadin.scala._
import com.vaadin.server.Sizeable

class CssLayoutTests extends ScaladinTestSuite {

  test("CssLayout, default constructor") {
    val layout = new CssLayout()
    assert(layout.p.getWidth === -1)
    assert(layout.p.getWidthUnits === Sizeable.Unit.PIXELS)
    assert(layout.p.getHeight === -1)
    assert(layout.p.getHeightUnits === Sizeable.Unit.PIXELS)
    assert(layout.caption === None)
    assert(layout.p.getStyleName === "")
  }

  test("CssLayout, add") {
    val layout = new CssLayout
    val label = new Label

    layout.add(label, "my invalid css")
    assert(layout.components(label))

    assert(layout.p.getCss(label.p) === "my invalid css")
    assert(layout.getCss(label) === Some("my invalid css"))

    val label2 = new Label
    layout.add(label2)
    assert(layout.p.getCss(label2.p) === null)
    assert(layout.getCss(label2) === None)
  }

  test("CssLayout, function literal for css") {
    val layout = new CssLayout
    val label = new Label
    var cnt = 0
    layout.add(label, {
      cnt = cnt + 1
      "" + cnt
    })
    assert(layout.getCss(label) === Some("2"))
    assert(layout.getCss(label) === Some("2"))
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
