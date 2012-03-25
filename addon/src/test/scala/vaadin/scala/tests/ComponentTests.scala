package vaadin.scala.tests

import org.scalatest.FunSuite

import com.vaadin.terminal.Sizeable

import vaadin.scala._

class ComponentTests extends FunSuite {

  test("Link, constructor with all params but without names") {
    val resource = new ExternalResource("http://www.example.com")

    val link = new Link("Caption", resource, "targetname", 200, 300, Link.TargetBorder.None)
    assert(link.caption === Some("Caption"))
    assert(link.resource === Some(resource))
    assert(link.targetName === Some("targetname"))
    assert(link.targetWidth === 200)
    assert(link.targetHeight === 300)
    assert(link.targetBorder === Link.TargetBorder.None)
  }

  test("Link, default constructor") {
    val link = new Link
    assert(link.caption === None)
    assert(link.resource === None)
    assert(link.targetName === None)
    assert(link.targetWidth === -1)
    assert(link.targetHeight === -1)
    assert(link.targetBorder === Link.TargetBorder.Default)
  }
  
  test("Component.styles.contains") {
    val label = new Label
    label.styles += "style1"
    label.styles += "styleName2"

    assert(label.styles.contains("style1"))
  }

  test("Component.styles.iterator") {
    val label = new Label
    label.styles += "style1"
    label.styles += "styleName2"
    label.styles += "stylez"

    val iter = label.styles.iterator
    assert(iter.next === "style1")
    assert(iter.next === "styleName2")
    assert(iter.next === "stylez")
    assert(!iter.hasNext)
  }

  test("Label, constructor with all params but without names") {
    val property = Property("Test");
    val label = new Label("Content", 10 px, 100 em, property, Label.ContentMode.Preformatted, "Style")
    assert(label.value === Some("Content"))
    //    assert(label.getWidth == 10)
    //    assert(label.getWidthUnits == Sizeable.UNITS_PIXELS)
    //    assert(label.getHeight == 100)
    //    assert(label.getHeightUnits == Sizeable.UNITS_EM)
    assert(label.property === Some(property))
    assert(label.contentMode === Label.ContentMode.Preformatted)
    assert(label.styles.mkString == "Style")
  }

  test("Label, default constructor") {
    val label = new Label
    label.styles += "style1"
    label.styles += "styleName2"

    assert(label.p.getStyleName === "style1 styleName2")
  }

  test("Component.styles +=, spaces are splitted") {
    val label = new Label
    label.styles += "  style1  "
    label.styles += "  styleName2    foobar  "
    label.styles += " stylez "

    assert(label.p.getStyleName === "style1 styleName2 foobar stylez")
    assert(label.styles.size === 4)
  }

  test("Component.styles -=") {
    val label = new Label
    label.styles += "style1"
    label.styles += "styleName2"
    label.styles += "stylez"

    label.styles -= "styleName2"

    assert(label.p.getStyleName === "style1 stylez")
  }

  test("Component.styles.size") {
    val label = new Label
    label.styles += "style1"
    label.styles += "style2"
    label.styles += "style3"

    assert(label.styles.size === 3)
  }

  test("HtmlLabel, constructor with all params but without names") {
    val property = Property("Test");
    val label = new HtmlLabel(<span>Content</span>, 10 px, 100 em, property, "Style")
    assert(label.value === Some("<span>Content</span>"))
    assert(label.p.getWidth == 10)
    assert(label.p.getWidthUnits == Sizeable.UNITS_PIXELS)
    assert(label.p.getHeight == 100)
    assert(label.p.getHeightUnits == Sizeable.UNITS_EM)
    assert(label.property === Some(property))
    assert(label.contentMode == Label.ContentMode.Xhtml)
    assert(label.p.getStyleName == "Style")
  }

  test("HtmlLabel, default constructor") {
    val label = new HtmlLabel
    assert(label.value === Some(""))
    assert(label.p.getWidth == 100)
    assert(label.p.getWidthUnits == Sizeable.UNITS_PERCENTAGE)
    assert(label.p.getHeight == -1)
    assert(label.p.getHeightUnits == Sizeable.UNITS_PIXELS)
    assert(label.p.getPropertyDataSource != null)
    assert(label.contentMode == Label.ContentMode.Xhtml)
    assert(label.p.getStyleName == "")
  }

  test("TextField, default constructor") {
    val textField = new TextField
    assert(textField.caption === None)
    //    assert(textField.getWidth === -1)
    //    assert(textField.getHeight === -1)
    assert(textField.property === None)
    assert(textField.value === Some(""))
    assert(textField.styles.mkString === "")
    assert(textField.prompt === None)
  }

  test("TextField, constructor with all params") {
    val property = Property("value1");
    val textField = new TextField(caption = "caption", width = 55 px, height = 22 px, property = property, value = "value2", style = "style", prompt = "prompt")
    assert(textField.caption === Some("caption"))
    //    assert(textField.getWidth === 55)
    //    assert(textField.getHeight === 22)
    assert(textField.property === Some(property))
    assert(textField.value === Some("value2"))
    assert(textField.styles.mkString === "style")
    assert(textField.prompt === Some("prompt"))
  }
  
  test("width, defined size") {
    val label = new Label()
    label.width = 25 px;
    assert(label.width.get === Measure(25, Units.px))
  }
  
  test("width, undefined size") {
    val label = new Label()
    label.width = None;
    assert(label.width === None)
  }
  
  test("height, defined size") {
    val label = new Label()
    label.height = 25 px;
    assert(label.height.get === Measure(25, Units.px))
  }
  
  test("height, undefined size") {
    val label = new Label()
    label.height = None;
    assert(label.height === None)
  }
  
  test("sizeFull()") {
    val label = new Label
    label.sizeFull()
    assert(label.width.get === Measure(100, Units.pct))
    assert(label.height.get === Measure(100, Units.pct))
  }
  
  test("sizeUndefined()") {
    val label = new Label
    label.sizeUndefined()
    assert(label.width === None)
    assert(label.height === None)
  }
  
}