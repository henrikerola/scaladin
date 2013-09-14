package vaadin.scala

/**
 * @author Henri Kerola / Vaadin
 */
object PushMode extends Enumeration {
  import com.vaadin.shared.communication.PushMode._

  val Disabled = Value(DISABLED.ordinal)
  val Manual = Value(MANUAL.ordinal)
  val Automatic = Value(AUTOMATIC.ordinal)
}
