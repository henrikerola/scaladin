package vaadin.scala

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
  override def toString = value + unit.toString
}

class MeasureExtent(value: Number) {
  def px: Option[Measure] = Option(Measure(value, Units.px))
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

trait Sizeable extends Component {

  def width: Option[Measure] = if (p.getWidth < 0) None else Option(Measure(p.getWidth, Units(p.getWidthUnits)))
  def width_=(width: Option[Measure]) = p.setWidth(if (width.isDefined) width.get.toString else null)
  def width_=(width: Measure) = p.setWidth(if (width != null) width.toString else null)

  def height: Option[Measure] = if (p.getHeight() < 0) None else Option(Measure(p.getHeight(), Units(p.getHeightUnits)))
  def height_=(height: Option[Measure]) = p.setHeight(if (height.isDefined) height.get.toString else null)
  def height_=(height: Measure) = p.setHeight(if (height != null) height.toString else null)

  def sizeFull() = p.setSizeFull
  def sizeUndefined() = p.setSizeUndefined

  def size(width: Measure, height: Measure) = {
    this.width = width
    this.height = height
  }

  def size(width: Option[Measure], height: Option[Measure]) = {
    this.width = width
    this.height = height
  }
}