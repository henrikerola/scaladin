package vaadin.scala
import vaadin.scala.mixins.SliderMixin

package mixins {
  trait SliderMixin extends AbstractFieldMixin { self: com.vaadin.ui.Slider => }
}

abstract class AbstractSlider(override val p: com.vaadin.ui.Slider with SliderMixin) extends AbstractField[java.lang.Double](p) {

  def min: Double = p.getMin
  def min_=(min: Double) = p.setMin(min)

  def max: Double = p.getMax
  def max_=(max: Double): Unit = p.setMax(max)

  def resolution: Int = p.getResolution
  def resolution_=(resolution: Int): Unit = p.setResolution(resolution)

  def value_=(value: Double): Unit = p.setValue(value)

}

class HorizontalSlider(override val p: com.vaadin.ui.Slider with SliderMixin = new com.vaadin.ui.Slider with SliderMixin) extends AbstractSlider(p) {

  p.setOrientation(com.vaadin.shared.ui.slider.SliderOrientation.HORIZONTAL)
}

class VerticalSlider(override val p: com.vaadin.ui.Slider with SliderMixin = new com.vaadin.ui.Slider with SliderMixin) extends AbstractSlider(p) {

  p.setOrientation(com.vaadin.shared.ui.slider.SliderOrientation.VERTICAL)
}