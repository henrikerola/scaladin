package vaadin.scala

abstract sealed class Size { def toSizes: Tuple2[String, String] }

object Full extends Size { def toSizes = Tuple2("100%", "100%") }
object Undefined extends Size { def toSizes = Tuple2(null, null) }

object Units extends Enumeration {
  import com.vaadin.terminal.Sizeable._

  val px = Value(UNITS_PIXELS, "px")
  val pt = Value(UNITS_POINTS, "pt")
  val pc = Value(UNITS_PICAS, "pc")
  val em = Value(UNITS_EM, "em")
  val ex = Value(UNITS_EX, "ex")
  val mm = Value(UNITS_MM, "mm")
  val cm = Value(UNITS_CM, "cm")
  val in = Value(UNITS_INCH, "in")
  val pct = Value(UNITS_PERCENTAGE, "%")
}

case class Measure(value: Number, unit: Units.Value) {

  def apply(m: Measure) = {}

  override def toString = value + unit.toString
}

class MeasureExtent(value: Number) {
  def px: Option[Measure] = Option(new Measure(value, Units.px))
  def percent: Option[Measure] = Option(new Measure(value, Units.pct))
  def pct: Option[Measure] = Option(new Measure(value, Units.pct))
  def em: Option[Measure] = Option(new Measure(value, Units.em))
  def ex: Option[Measure] = Option(new Measure(value, Units.ex))
  def in: Option[Measure] = Option(new Measure(value, Units.in))
  def cm: Option[Measure] = Option(new Measure(value, Units.cm))
  def mm: Option[Measure] = Option(new Measure(value, Units.mm))
  def pt: Option[Measure] = Option(new Measure(value, Units.pt))
  def pc: Option[Measure] = Option(new Measure(value, Units.pc))
}
