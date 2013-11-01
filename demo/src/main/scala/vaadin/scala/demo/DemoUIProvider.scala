package vaadin.scala.demo

import vaadin.scala.server.ScaladinUIProvider
import com.vaadin.server.UIProviderEvent

class DemoUIProvider extends ScaladinUIProvider {

  protected def createScaladinUiInstance(e: UIProviderEvent): vaadin.scala.UI = {
    e.getRequest().getParameter("ticket") match {
      case "27" => new Ticket27_ErrorMarkerUI
      case _ => new DemoUI
    }
  }
}