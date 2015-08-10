package vaadin.scala.demo

import vaadin.scala.server.ScaladinUIProvider
import com.vaadin.server.UIProviderEvent

class DemoUIProvider extends ScaladinUIProvider {

  override protected def getScaladinUIClass(e: UIProviderEvent): Class[_] = {
    e.getRequest().getParameter("ticket") match {
      case "27" => classOf[Ticket27_ErrorMarkerUI]
      case _ => classOf[DemoUI]
    }
  }
}

