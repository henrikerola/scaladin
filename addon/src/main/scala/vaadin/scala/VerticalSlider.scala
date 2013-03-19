package vaadin.scala

import vaadin.scala.mixins.SliderMixin

class VerticalSlider(
  override val p: com.vaadin.ui.Slider with SliderMixin = new com.vaadin.ui.Slider with SliderMixin)
    extends AbstractSlider(p) {

  p.setOrientation(com.vaadin.shared.ui.slider.SliderOrientation.VERTICAL)
}
