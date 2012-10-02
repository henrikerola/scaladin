package vaadin.scala

import com.vaadin.server.VaadinServlet
import javax.servlet.ServletConfig
import vaadin.scala.internal.ScaladinUIProvider
import com.vaadin.server.SessionInitListener
import com.vaadin.server.SessionInitEvent

class ScaladinServlet extends VaadinServlet {

  override def init(servletConfig: ServletConfig) {
    super.init(servletConfig)
    registerUIProvider()
  }

  private def registerUIProvider() {
    getService.addSessionInitListener(new SessionInitListener {
      def sessionInit(e: SessionInitEvent) {
        e.getSession.addUIProvider(new ScaladinUIProvider)
      }
    })
  }

}