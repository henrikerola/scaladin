package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import com.vaadin.terminal.Sizeable
import vaadin.scala.Property
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.xml.NodeBuffer

@RunWith(classOf[JUnitRunner])
class LabelTests extends FunSuite {

  test("ContentModes") {
    assert(Label.ContentMode.Text.id === com.vaadin.ui.Label.CONTENT_TEXT)
    assert(Label.ContentMode.Preformatted.id === com.vaadin.ui.Label.CONTENT_PREFORMATTED)
    assert(Label.ContentMode.Xhtml.id === com.vaadin.ui.Label.CONTENT_XHTML)
    assert(Label.ContentMode.Xml.id === com.vaadin.ui.Label.CONTENT_XML)
    assert(Label.ContentMode.Raw.id === com.vaadin.ui.Label.CONTENT_RAW)
  }

  test("constructor") {
    val label = new Label
    assert(label.value === Some(""))
    assert(label.width === (100 pct))
    assert(label.height === None)
    assert(label.property != None)
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
  
  test("value: XML Node") {
    val label = new Label
    label.value = <b>Hello World!</b>
    assert(label.value === Some("<b>Hello World!</b>"))
  }
  
  test("value: XML NodeBuffer") {
    val label = new Label

    val nodeBuffer = new NodeBuffer
    nodeBuffer + <b>Hello World!</b>
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
    label.contentMode = Label.ContentMode.Xml
    assert(label.contentMode === Label.ContentMode.Xml)
  }

  test("property: None") {
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
}