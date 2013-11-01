package vaadin.scala.internal

import com.vaadin.server.ErrorEvent
import vaadin.scala.server.ScaladinSession

/**
 * @author Henri Kerola / Vaadin
 */
class ErrorHandler(val action: ScaladinSession.ErrorEvent => Unit) extends com.vaadin.server.ErrorHandler with Listener {
  def error(e: ErrorEvent) = action(ScaladinSession.ErrorEvent(e))
}
