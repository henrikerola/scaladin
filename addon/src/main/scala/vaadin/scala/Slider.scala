package vaadin.scala

trait AbstractSlider extends AbstractField {

  val p = new com.vaadin.ui.Slider
  WrapperRegistry.put(this)

  def min = p.getMin
  def min_=(min: Double) = p.setMin(min)

  def max = p.getMax
  def max_=(max: Double) = p.setMax(max)

  def resolution = p.getResolution
  def resolution_=(resolution: Int) = p.setResolution(resolution)

  def value_=(value: Double) = p.setValue(value)

}

class HorizontalSlider extends AbstractSlider {

  p.setOrientation(com.vaadin.ui.Slider.ORIENTATION_HORIZONTAL)

  def this(caption: String = null, width: Option[Measure] = None, height: Option[Measure] = None, property: Property = null, value: Any = null, min: Double = 0, max: Double = 100, resolution: Int = 0, style: String = null) = {
    this()

    this.caption = caption
    this.width = width
    this.height = height
    if (property != null) p.setPropertyDataSource(property.p)
    if (value != null) p.setValue(value)
    this.min = min
    this.max = max
    this.resolution = resolution
    p.setStyleName(style)
  }
}

class VerticalSlider extends AbstractSlider {

  p.setOrientation(com.vaadin.ui.Slider.ORIENTATION_VERTICAL)

  def this(caption: String = null, width: Option[Measure] = None, height: Option[Measure] = None, property: Property = null, value: Any = null, min: Double = 0, max: Double = 100, resolution: Int = 0, style: String = null) = {
    this()

    this.caption = caption
    this.width = width
    this.height = height
    if (property != null) p.setPropertyDataSource(property.p)
    if (value != null) p.setValue(value)
    this.min = min
    this.max = max
    this.resolution = resolution
    p.setStyleName(style)
  }
}
