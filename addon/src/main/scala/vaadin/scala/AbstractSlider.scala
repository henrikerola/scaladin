package vaadin.scala
import vaadin.scala.mixins.SliderMixin

package mixins {
  trait SliderMixin extends AbstractFieldMixin[java.lang.Double] { self: com.vaadin.ui.Slider => }
}

abstract class AbstractSlider(override val p: com.vaadin.ui.Slider with SliderMixin)
    extends AbstractField[java.lang.Double](p) {

  def min: Double = p.getMin
  def min_=(min: Double) { p.setMin(min) }

  def max: Double = p.getMax
  def max_=(max: Double) { p.setMax(max) }

  def resolution: Int = p.getResolution
  def resolution_=(resolution: Int) { p.setResolution(resolution) }

  def value_=(value: Double) { p.setValue(value) }
}