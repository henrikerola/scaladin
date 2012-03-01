package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import com.vaadin.terminal.Sizeable

class ComponentTests extends FunSuite {
  
  test("HtmlLabel, constructor with all params but without names") {
    val property = Property("Test");
    val label = new HtmlLabel(<span>Content</span>, 10 px, 100 em, property, "Style")
    assert(label.value.get == "<span>Content</span>")
    assert(label.p.getWidth == 10)
    assert(label.p.getWidthUnits == Sizeable.UNITS_PIXELS)
    assert(label.p.getHeight == 100)
    assert(label.p.getHeightUnits == Sizeable.UNITS_EM)
    assert(label.p.getPropertyDataSource == property)
    assert(label.contentMode == Label.ContentMode.xhtml)
    assert(label.p.getStyleName == "Style")
  }
  
  test("HtmlLabel, default constructor") {
    val label = new HtmlLabel
    assert(label.value.get == "")
    assert(label.p.getWidth == 100)
    assert(label.p.getWidthUnits == Sizeable.UNITS_PERCENTAGE)
    assert(label.p.getHeight == -1)
    assert(label.p.getHeightUnits == Sizeable.UNITS_PIXELS)
    assert(label.p.getPropertyDataSource != null)
    assert(label.contentMode == Label.ContentMode.xhtml)
    assert(label.p.getStyleName == "")
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

}