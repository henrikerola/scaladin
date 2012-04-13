package vaadin.scala

abstract class AbstractSlider(override val p: com.vaadin.ui.Slider) extends AbstractField(p) {

  def min = p.getMin
  def min_=(min: Double) = p.setMin(min)

  def max = p.getMax
  def max_=(max: Double) = p.setMax(max)

  def resolution = p.getResolution
  def resolution_=(resolution: Int) = p.setResolution(resolution)

  def value_=(value: Double) = p.setValue(value)

}

class HorizontalSlider(override val p: com.vaadin.ui.Slider = new com.vaadin.ui.Slider) extends AbstractSlider(p) {
  
  p.setOrientation(com.vaadin.ui.Slider.ORIENTATION_HORIZONTAL)
}

class VerticalSlider(override val p: com.vaadin.ui.Slider = new com.vaadin.ui.Slider) extends AbstractSlider(p) {
  
  p.setOrientation(com.vaadin.ui.Slider.ORIENTATION_VERTICAL)
}
