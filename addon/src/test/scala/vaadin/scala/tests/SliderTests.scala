package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite

class SliderTests extends FunSuite {

  test("VerticalSlider, constructor with all params but without names") {
    val property = Property(1);
    val slider = new VerticalSlider("Caption", 10 px, 100 em, property, 5, -10, 10, 1, "Style")
    assert(slider.p.getOrientation === com.vaadin.ui.Slider.ORIENTATION_VERTICAL)
    assert(slider.caption.get === "Caption")
    assert(slider.width.get === Measure(10, Units.px))
    assert(slider.height.get === Measure(100, Units.em))
    assert(slider.p.getPropertyDataSource === property.p)
    assert(slider.p.getValue === 5)
    assert(slider.min === -10)
    assert(slider.max === 10)
    assert(slider.resolution === 1)
    assert(slider.p.getStyleName === "Style")
  }

  test("VerticalSlider, default constructor") {
    val slider = new VerticalSlider()
    assert(slider.p.getOrientation == com.vaadin.ui.Slider.ORIENTATION_VERTICAL)
    assert(slider.caption === None)
    assert(slider.width === None)
    assert(slider.height === None)
    assert(slider.p.getPropertyDataSource == null)
    assert(slider.p.getValue === 0)
    assert(slider.min === 0)
    assert(slider.max === 100)
    assert(slider.resolution == 0)
    assert(slider.p.getStyleName === "")
  }

  test("HorizontalSlider, constructor with all params but without names") {
    val property = Property(1);
    val slider = new HorizontalSlider("Caption", 10 px, 100 em, property, 5, -10, 10, 1, "Style")
    assert(slider.p.getOrientation === com.vaadin.ui.Slider.ORIENTATION_HORIZONTAL)
    assert(slider.caption.get === "Caption")
    assert(slider.width.get === Measure(10, Units.px))
    assert(slider.height.get === Measure(100, Units.em))
    assert(slider.p.getPropertyDataSource === property.p)
    assert(slider.p.getValue === 5)
    assert(slider.min === -10)
    assert(slider.max === 10)
    assert(slider.resolution == 1)
    assert(slider.p.getStyleName == "Style")
  }

  test("HorizontalSlider, default constructor") {
    val slider = new HorizontalSlider()
    assert(slider.p.getOrientation == com.vaadin.ui.Slider.ORIENTATION_HORIZONTAL)
    assert(slider.caption === None)
    assert(slider.width === None)
    assert(slider.height === None)
    assert(slider.p.getPropertyDataSource === null)
    assert(slider.p.getValue == 0)
    assert(slider.min === 0)
    assert(slider.max === 100)
    assert(slider.resolution === 0)
    assert(slider.p.getStyleName === "")
  }
}