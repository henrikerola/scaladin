package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import com.vaadin.terminal.Sizeable
import vaadin.scala.Property
import com.vaadin.data.util.ObjectProperty

class LabelTests extends FunSuite {

  implicit val wr = new WrapperRegistry

  test("ContentModes") {
    assert(Label.ContentMode.text.id === com.vaadin.ui.Label.CONTENT_TEXT)
    assert(Label.ContentMode.preformatted.id === com.vaadin.ui.Label.CONTENT_PREFORMATTED)
    assert(Label.ContentMode.xhtml.id === com.vaadin.ui.Label.CONTENT_XHTML)
    assert(Label.ContentMode.xml.id === com.vaadin.ui.Label.CONTENT_XML)
    assert(Label.ContentMode.raw.id === com.vaadin.ui.Label.CONTENT_RAW)
  }

  test("constructor: with all params but without names") {
    val property = Property("Test");
    val label = new Label("Content", 10 px, 100 em, property, Label.ContentMode.preformatted, "Style")
    assert(label.value.get == "Content")
    assert(label.p.getWidth == 10)
    assert(label.p.getWidthUnits == Sizeable.UNITS_PIXELS)
    assert(label.p.getHeight == 100)
    assert(label.p.getHeightUnits == Sizeable.UNITS_EM)
    assert(label.p.getPropertyDataSource == property)
    assert(label.contentMode == Label.ContentMode.preformatted)
    assert(label.p.getStyleName == "Style")
  }

  test("constructor: constructor") {
    val label = new Label
    assert(label.value.get == "")
    assert(label.p.getWidth == 100)
    assert(label.p.getWidthUnits == Sizeable.UNITS_PERCENTAGE)
    assert(label.p.getHeight == -1)
    assert(label.p.getHeightUnits == Sizeable.UNITS_PIXELS)
    assert(label.p.getPropertyDataSource != null)
    assert(label.contentMode == Label.ContentMode.text)
    assert(label.p.getStyleName == "")
  }

  test("value: None") {
    val label = new Label
    label.value = None
    assert(label.value === None)
  }

  test("value: String") {
    val label = new Label
    label.value = "test value"
    assert(label.value.get === "test value")
  }

  test("value: Some") {
    val label = new Label
    label.value = Some("test value")
    assert(label.value.get === "test value")
  }

  test("contentMode: null content mode throws exception") {
    val label = new Label
    val exception = intercept[NullPointerException] {
      label.contentMode = null
    }
  }

  test("contentMode: getter should return set content mode") {
    val label = new Label
    label.contentMode = Label.ContentMode.xml
    assert(label.contentMode === Label.ContentMode.xml)
  }

  test("property: None") {
    val label = new Label
    label.property = None
    assert(label.property === None)
  }

  test("property: Property") {
    val property = new ObjectProperty("test")
    val label = new Label
    label.property = property
    assert(label.property.get === property)
  }

  test("property: Some") {
    val option = Some(new ObjectProperty("test"))
    val label = new Label
    label.property = option
    assert(label.property === option)
  }

}