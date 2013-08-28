package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import vaadin.scala.converter.Converter
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.xml.NodeBuffer
import java.util.Locale

@RunWith(classOf[JUnitRunner])
class LabelTests extends FunSuite {

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
    //assert(label.property == None) // FIXME
    assert(label.contentMode === Label.ContentMode.Text)
    assert(label.p.getStyleName === "")
  }

  ignore("value: None") { // FIXME
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

  test("value: XML Node") {
    val label = new Label
    label.value = <b>Hello World!</b>
    assert(label.value === Some("<b>Hello World!</b>"))
  }

  test("value: XML NodeBuffer") {
    val label = new Label

    val nodeBuffer = new NodeBuffer
    nodeBuffer += <b>Hello World!</b>
    label.value = nodeBuffer
    assert(label.value === Some("<b>Hello World!</b>"))
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

  ignore("property: None") { // This setting propertyDataSource to null throws NPE in Vaadin 7?
    val label = new Label
    label.property = None
    assert(label.property === None)
  }

  ignore("property: Property") {
    val property = new ObjectProperty("test")
    val label = new Label
    label.property = property
    assert(label.property === Some(property))
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