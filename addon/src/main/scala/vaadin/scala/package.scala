package vaadin

package object scala {
  
  implicit def intToMeasureOption(value: Int): MeasureExtent = new MeasureExtent(value)

  implicit def doubleToMeasureOption(value: Double): MeasureExtent = new MeasureExtent(value)
  
  implicit def fullSizeToUnitExtend(value: Size): Tuple2[String, String] = value.toSizes
}