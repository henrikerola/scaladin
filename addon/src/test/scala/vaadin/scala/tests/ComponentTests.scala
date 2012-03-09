package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import com.vaadin.terminal.Sizeable
import com.vaadin.terminal.ExternalResource

class ComponentTests extends FunSuite {

  test("Link, constructor with all params but without names") {
    val resource = new ExternalResource("http://www.example.com")

    val link = new Link("Caption", resource, "targetname", 200, 300, com.vaadin.ui.Link.TARGET_BORDER_NONE)
    assert(link.getCaption === "Caption")
    assert(link.getResource === resource)
    assert(link.getTargetName === "targetname")
    assert(link.getTargetWidth === 200)
    assert(link.getTargetHeight === 300)
    assert(link.getTargetBorder === com.vaadin.ui.Link.TARGET_BORDER_NONE)
  }

  test("Link, default constructor") {
    val link = new Link
    assert(link.getCaption === null)
    assert(link.getResource === null)
    assert(link.getTargetName === null)
    assert(link.getTargetWidth === -1)
    assert(link.getTargetHeight === -1)
    assert(link.getTargetBorder === com.vaadin.ui.Link.TARGET_BORDER_DEFAULT)
  }

  test("Label, constructor with all params but without names") {
    val property = Property("Test");
    val label = new Label("Content", 10 px, 100 em, property, com.vaadin.ui.Label.CONTENT_PREFORMATTED, "Style")
    assert(label.getValue == "Content")
    assert(label.getWidth == 10)
    assert(label.getWidthUnits == Sizeable.UNITS_PIXELS)
    assert(label.getHeight == 100)
    assert(label.getHeightUnits == Sizeable.UNITS_EM)
    assert(label.getPropertyDataSource == property)
    assert(label.getContentMode == com.vaadin.ui.Label.CONTENT_PREFORMATTED)
    assert(label.getStyleName == "Style")
  }

  test("Label, default constructor") {
    val label = new Label
    assert(label.getValue == "")
    assert(label.getWidth == 100)
    assert(label.getWidthUnits == Sizeable.UNITS_PERCENTAGE)
    assert(label.getHeight == -1)
    assert(label.getHeightUnits == Sizeable.UNITS_PIXELS)
    assert(label.getPropertyDataSource != null)
    assert(label.getContentMode == com.vaadin.ui.Label.CONTENT_DEFAULT)
    assert(label.getStyleName == "")
  }

  test("HtmlLabel, constructor with all params but without names") {
    val property = Property("Test");
    val label = new HtmlLabel(<span>Content</span>, 10 px, 100 em, property, "Style")
    assert(label.getValue == "<span>Content</span>")
    assert(label.getWidth == 10)
    assert(label.getWidthUnits == Sizeable.UNITS_PIXELS)
    assert(label.getHeight == 100)
    assert(label.getHeightUnits == Sizeable.UNITS_EM)
    assert(label.getPropertyDataSource == property)
    assert(label.getContentMode == com.vaadin.ui.Label.CONTENT_XHTML)
    assert(label.getStyleName == "Style")
  }

  test("HtmlLabel, default constructor") {
    val label = new HtmlLabel
    assert(label.getValue == "")
    assert(label.getWidth == 100)
    assert(label.getWidthUnits == Sizeable.UNITS_PERCENTAGE)
    assert(label.getHeight == -1)
    assert(label.getHeightUnits == Sizeable.UNITS_PIXELS)
    assert(label.getPropertyDataSource != null)
    assert(label.getContentMode == com.vaadin.ui.Label.CONTENT_XHTML)
    assert(label.getStyleName == "")
  }

  test("VerticalSlider, constructor with all params but without names") {
    val property = Property(1);
    val slider = new VerticalSlider("Caption", 10 px, 100 em, property, 5, -10, 10, 1, "Style")
    assert(slider.getOrientation() == com.vaadin.ui.Slider.ORIENTATION_VERTICAL)
    assert(slider.getCaption == "Caption")
    assert(slider.getWidth == 10)
    assert(slider.getWidthUnits == Sizeable.UNITS_PIXELS)
    assert(slider.getHeight == 100)
    assert(slider.getHeightUnits == Sizeable.UNITS_EM)
    assert(slider.getPropertyDataSource == property)
    assert(slider.getValue == 5)
    assert(slider.getMin == -10)
    assert(slider.getMax == 10)
    assert(slider.getResolution == 1)
    assert(slider.getStyleName == "Style")
  }

  test("VerticalSlider, default constructor") {
    val slider = new VerticalSlider()
    assert(slider.getOrientation() == com.vaadin.ui.Slider.ORIENTATION_VERTICAL)
    assert(slider.getCaption == null)
    assert(slider.getWidth == -1)
    assert(slider.getWidthUnits == Sizeable.UNITS_PIXELS)
    assert(slider.getHeight == -1)
    assert(slider.getHeightUnits == Sizeable.UNITS_PIXELS)
    assert(slider.getPropertyDataSource == null)
    assert(slider.getValue == 0)
    assert(slider.getMin == 0)
    assert(slider.getMax == 100)
    assert(slider.getResolution == 0)
    assert(slider.getStyleName == "")
  }

  test("HorizontalSlider, constructor with all params but without names") {
    val property = Property(1);
    val slider = new HorizontalSlider("Caption", 10 px, 100 em, property, 5, -10, 10, 1, "Style")
    assert(slider.getOrientation() == com.vaadin.ui.Slider.ORIENTATION_HORIZONTAL)
    assert(slider.getCaption == "Caption")
    assert(slider.getWidth == 10)
    assert(slider.getWidthUnits == Sizeable.UNITS_PIXELS)
    assert(slider.getHeight == 100)
    assert(slider.getHeightUnits == Sizeable.UNITS_EM)
    assert(slider.getPropertyDataSource == property)
    assert(slider.getValue == 5)
    assert(slider.getMin == -10)
    assert(slider.getMax == 10)
    assert(slider.getResolution() == 1)
    assert(slider.getStyleName == "Style")
  }

  test("HorizontalSlider, default constructor") {
    val slider = new HorizontalSlider()
    assert(slider.getOrientation() == com.vaadin.ui.Slider.ORIENTATION_HORIZONTAL)
    assert(slider.getCaption == null)
    assert(slider.getWidth == -1)
    assert(slider.getWidthUnits == Sizeable.UNITS_PIXELS)
    assert(slider.getHeight == -1)
    assert(slider.getHeightUnits == Sizeable.UNITS_PIXELS)
    assert(slider.getPropertyDataSource == null)
    assert(slider.getValue == 0)
    assert(slider.getMin == 0)
    assert(slider.getMax == 100)
    assert(slider.getResolution == 0)
    assert(slider.getStyleName == "")
  }

  test("TextField, default constructor") {
    val textField = new TextField
    assert(textField.getCaption === null)
    assert(textField.getWidth === -1)
    assert(textField.getHeight === -1)
    assert(textField.getPropertyDataSource === null)
    assert(textField.getValue === "")
    assert(textField.getStyleName === "")
    assert(textField.getInputPrompt === null)
  }

  test("TextField, constructor with all params") {
    val property = Property("value1");
    val textField = new TextField(caption = "caption", width = 55 px, height = 22 px, property = property, value = "value2", style = "style", prompt = "prompt")
    assert(textField.getCaption === "caption")
    assert(textField.getWidth === 55)
    assert(textField.getHeight === 22)
    assert(textField.getPropertyDataSource === property)
    assert(textField.getValue === "value2")
    assert(textField.getStyleName === "style")
    assert(textField.getInputPrompt === "prompt")
  }
}