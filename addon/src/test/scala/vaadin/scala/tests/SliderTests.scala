package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite

class SliderTests extends FunSuite {

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