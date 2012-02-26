package vaadin

import com.vaadin.ui.Component

package object scala {

  abstract sealed class Size { def toSizes: Tuple2[String, String]}
  
  object Full extends Size {def toSizes = Tuple2("100%","100%")}
  object Undefined extends Size {def toSizes = Tuple2(null, null)}
  
  class UnitExtent(value: Number) {
    def px: String = value + "px"
    def percent: String = value + "%"
    def pct: String = value + "%"
    def em: String = value + "em"
    def ex: String = value + "ex"
    def in: String = value + "in"
    def cm: String = value + "cm"
    def mm: String = value + "mm"
    def pt: String = value + "pt"
    def pc: String = value + "pc"
  }

  implicit def intToUnitExtent(value: Int): UnitExtent = new UnitExtent(value)

  implicit def doubleToUnitExtent(value: Double): UnitExtent = new UnitExtent(value)
  
  implicit def fullSizeToUnitExtend(value: Size): Tuple2[String,String] = value.toSizes
}