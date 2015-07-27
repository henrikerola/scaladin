package vaadin.scala.tests

import vaadin.scala._
import vaadin.scala.converter.Converter
import java.util.Locale

class LabelTests extends ScaladinTestSuite {

  test("ContentModes") {
    assert(Label.ContentMode.Text.id === com.vaadin.shared.ui.label.ContentMode.TEXT.ordinal)
    assert(Label.ContentMode.Preformatted.id === com.vaadin.shared.ui.label.ContentMode.PREFORMATTED.ordinal)
    assert(Label.ContentMode.Html.id === com.vaadin.shared.ui.label.ContentMode.HTML.ordinal)
  }

  test("constructor") {
    val label = new Label
    assert(label.value === Some(""))
    assert(label.width === (100 pct))
    assert(label.height === None)
    assert(label.property == None)
    assert(label.contentMode === Label.ContentMode.Text)
    assert(label.p.getStyleName === "")
  }

  test("value: None") {
    val label = new Label
    label.value = None
    assert(label.value === None)
  }

  test("value: String") {
    val label = new Label
    label.value = "test value"
    assert(label.value === Some("test value"))
  }

  test("value: Some") {
    val label = new Label
    label.value = Some("test value")
    assert(label.value === Some("test value"))
  }

  test("contentMode: null content mode throws exception") {
    val label = new Label
    val exception = intercept[NullPointerException] {
      label.contentMode = null
    }
  }

  test("contentMode: getter should return set content mode") {
    val label = new Label
    label.contentMode = Label.ContentMode.Html
    assert(label.contentMode === Label.ContentMode.Html)
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
    assert(label.property.get.p === property.p)
  }

  test("property: Some") {
    val option = Some(new ObjectProperty("test"))
    val label = new Label
    label.property = option
    assert(label.property.get.value === option.get.value)
  }

  test("object constructor for undefined size") {
    val label = Label.undefinedSized("testValue")
    assert(Some("testValue") === label.value)
  }

  test("conveter") {
    val label = new Label

    val converter = new Converter[String, Integer]() {

      def convertToPresentation(value: Option[Integer], targetType: Class[_ <: String], locale: Locale) = ???

      def convertToModel(value: Option[String], targetType: Class[_ <: Integer], locale: Locale) = ???
    }

    assert(label.converter === None)
    label.converter = converter

    assert(label.converter === Some(converter))
  }
}