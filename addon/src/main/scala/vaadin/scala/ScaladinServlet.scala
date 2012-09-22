package vaadin.scala

import com.vaadin.server.VaadinServlet
import javax.servlet.ServletConfig
import com.vaadin.server.VaadinSessionInitializationListener
import com.vaadin.server.VaadinSessionInitializeEvent
import vaadin.scala.internal.ScaladinUIProvider

class ScaladinServlet extends VaadinServlet {

  override def init(servletConfig: ServletConfig) {
    super.init(servletConfig)
    registerUIProvider()
  }

  private def registerUIProvider() {
    getVaadinService.addVaadinSessionInitializationListener(new VaadinSessionInitializationListener {
      def vaadinSessionInitialized(e: VaadinSessionInitializeEvent) {
        e.getVaadinSession.addUIProvider(new ScaladinUIProvider)
      }
    })
  }

}