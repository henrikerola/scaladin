package vaadin.scala.server

import com.vaadin.server.VaadinServlet
import javax.servlet.ServletConfig
import vaadin.scala.internal.{ DefaultScaladinUIProvider, WrapperUtil }
import vaadin.scala.server.mixins.ScaladinServletServiceMixin

class ScaladinServlet extends VaadinServlet {

  override def init(servletConfig: ServletConfig) {
    super.init(servletConfig)
    registerSessionInitListener()
  }

  private def registerSessionInitListener() {
    service.sessionInitListeners += { event: ScaladinService.SessionInitEvent =>
      event.session.init()

      event.session.p.addUIProvider(new DefaultScaladinUIProvider) // FIXME .p.

      val customUIProvider = service.deploymentConfiguration.getInitParameters.getProperty("ScaladinUIProvider")
      if (customUIProvider != null)
        event.session.p.addUIProvider(createScaladinUiProviderInstance(customUIProvider)) // FIXME .p.
    }
  }

  override def createServletService(c: com.vaadin.server.DeploymentConfiguration) =
    new ScaladinServletService(new com.vaadin.server.VaadinServletService(this, c) with ScaladinServletServiceMixin).init().p

  def service: ScaladinServletService = WrapperUtil.wrapperFor(getService).get

  protected def createScaladinUiProviderInstance(className: String): vaadin.scala.server.ScaladinUIProvider = {
    val classLoader = Some(service.classLoader).getOrElse(getClass.getClassLoader)
    Class.forName(className, true, classLoader).newInstance.asInstanceOf[vaadin.scala.server.ScaladinUIProvider]
  }
}