package vaadin.scala

object Alignment extends Enumeration {
  import com.vaadin.ui.Alignment._

  val TopRight = Value(TOP_RIGHT.getBitMask())
  val TopLeft = Value(TOP_LEFT.getBitMask())
  val TopCenter = Value(TOP_CENTER.getBitMask())
  val MiddleRight = Value(MIDDLE_RIGHT.getBitMask())
  val MiddleLeft = Value(MIDDLE_LEFT.getBitMask())
  val MiddleCenter = Value(MIDDLE_CENTER.getBitMask())
  val BottomRight = Value(BOTTOM_RIGHT.getBitMask())
  val BottomLeft = Value(BOTTOM_LEFT.getBitMask())
  val BottomCenter = Value(BOTTOM_CENTER.getBitMask())
}