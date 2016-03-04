package vaadin.scala.server

import java.util.Properties

import com.vaadin.server
import com.vaadin.server.Constants._
import com.vaadin.server.DefaultDeploymentConfiguration._
import com.vaadin.server.VaadinServlet
import javax.servlet.ServletConfig
import vaadin.scala.UI
import vaadin.scala.internal.{ DefaultScaladinUIProvider, WrapperUtil }
import vaadin.scala.server.mixins.ScaladinServletServiceMixin

class ScaladinServlet(
    ui: Class[_] = null,
    productionMode: Boolean = false,
    widgetset: String = "com.vaadin.DefaultWidgetSet",
    resourceCacheTime: Int = DEFAULT_RESOURCE_CACHE_TIME,
    heartbeatInterval: Int = DEFAULT_HEARTBEAT_INTERVAL,
    closeIdleSessions: Boolean = DEFAULT_CLOSE_IDLE_SESSIONS) extends VaadinServlet {

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

  override def createDeploymentConfiguration(initParameters: Properties): server.DeploymentConfiguration = {
    applyPropertiesDefinedInConstructor(initParameters)

    super.createDeploymentConfiguration(initParameters)
  }

  private def hasEnclosingUIClass(clazz: Class[_]): Boolean = {
    Option(clazz.getEnclosingClass)
      .map(classOf[UI].isAssignableFrom)
      .getOrElse(false)
  }

  private def applyPropertiesDefinedInConstructor(initParameters: Properties): Unit = {

    def setAbsentProperty(property: String, value: String): Unit =
      if (initParameters.getProperty(property) == null) {
        initParameters.setProperty(property, value)
      }

    if (initParameters.getProperty("ScaladinUI") == null && ui == null) {
      if (hasEnclosingUIClass(getClass)) {
        setAbsentProperty("ScaladinUI", getClass.getEnclosingClass.getName)
      } else {
        throw new IllegalStateException("No ScaladinUI defined")
      }
    } else {
      setAbsentProperty("ScaladinUI", ui.getName)
    }

    setAbsentProperty(SERVLET_PARAMETER_PRODUCTION_MODE, productionMode.toString)
    setAbsentProperty(PARAMETER_WIDGETSET, widgetset)
    setAbsentProperty(SERVLET_PARAMETER_RESOURCE_CACHE_TIME, resourceCacheTime.toString)
    setAbsentProperty(SERVLET_PARAMETER_HEARTBEAT_INTERVAL, heartbeatInterval.toString)
    setAbsentProperty(SERVLET_PARAMETER_CLOSE_IDLE_SESSIONS, closeIdleSessions.toString)
  }
}