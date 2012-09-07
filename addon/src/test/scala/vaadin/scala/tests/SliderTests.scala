package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SliderTests extends FunSuite {

  test("VerticalSlider, default constructor") {
    val slider = new VerticalSlider
    assert(slider.p.getOrientation == com.vaadin.shared.ui.slider.SliderOrientation.VERTICAL)
  }

  test("HorizontalSlider, default constructor") {
    val slider = new HorizontalSlider
    assert(slider.p.getOrientation == com.vaadin.shared.ui.slider.SliderOrientation.HORIZONTAL)
  }
}