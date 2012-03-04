package vaadin.scala

object Alignment extends Enumeration {
  import com.vaadin.ui.Alignment._
  
  val topRight = Value(TOP_RIGHT.getBitMask())
  val topLeft = Value(TOP_LEFT.getBitMask())
  val topCenter = Value(TOP_CENTER	.getBitMask())
  val middleRight = Value(MIDDLE_RIGHT.getBitMask())
  val middleLeft = Value(MIDDLE_LEFT.getBitMask())
  val middleCenter = Value(MIDDLE_CENTER.getBitMask())
  val bottomRight = Value(BOTTOM_RIGHT.getBitMask())
  val bottomLeft = Value(BOTTOM_LEFT.getBitMask())
  val bottomCenter = Value(BOTTOM_CENTER.getBitMask())
}