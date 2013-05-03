package vaadin.scala

import com.vaadin.server.{ VaadinServlet, SessionInitListener, SessionInitEvent }
import javax.servlet.ServletConfig
import vaadin.scala.internal.ScaladinUIProvider
import vaadin.scala.mixins.ScaladinServletServiceMixin

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

  override def createServletService(c: com.vaadin.server.DeploymentConfiguration) = {
    val ser = new ScaladinServletService(new com.vaadin.server.VaadinServletService(this, c) with ScaladinServletServiceMixin).p
    ser.init()
  }
}
