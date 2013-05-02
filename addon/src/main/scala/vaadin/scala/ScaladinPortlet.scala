package vaadin.scala

import com.vaadin.server.{ SessionInitEvent, SessionInitListener, VaadinPortlet }
import internal.ScaladinUIProvider
import javax.portlet.PortletConfig

/**
 * @see com.vaadin.server.VaadinPortlet
 * @author Henri Kerola / Vaadin
 */
class ScaladinPortlet extends VaadinPortlet {

  override def init(config: PortletConfig) {
    super.init(config)
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
