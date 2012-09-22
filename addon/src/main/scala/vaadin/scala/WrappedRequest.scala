package vaadin.scala

import java.util.Locale
import java.io.InputStream
import java.util.Date

object ScaladinRequest {

  trait BrowserDetails { browserDetails =>

    val p: com.vaadin.server.VaadinRequest.BrowserDetails

    def uriFragment: String = p.getUriFragment // TODO: can be null?

    def windowName: String = p.getWindowName // TODO: can be null?

    // TODO: WebBrowser
  }

  trait DeploymentConfiguration {

    val p: com.vaadin.server.DeploymentConfiguration

    //    def staticFileLocation(wrappedRequest: WrappedRequest): String = p.getStaticFileLocation(wrappedRequest.p)
    //
    //    def configuredWidgetset(wrappedRequest: WrappedRequest): String = p.getConfiguredWidgetset(wrappedRequest.p)
    //
    //    def configuredTheme(wrappedRequest: WrappedRequest): String = p.getConfiguredTheme(wrappedRequest.p)
    //
    //    def standalone(wrappedRequest: WrappedRequest): Boolean = p.isStandalone(wrappedRequest.p)
    //
    //    def classLoader: Option[ClassLoader] = Option(p.getClassLoader)

    // TODO: ...
  }
}

trait ScaladinRequest { wrappedRequest =>

  val p: com.vaadin.server.VaadinRequest

  // TODO: getParameter
  // TODO: getParameterMap()

  def contentLength: Int = p.getContentLength

  // TODO: getInputStream can throw IOException handle somehow more Scala-way?
  def inputStream: InputStream = p.getInputStream

  // TODO: get/setAttribute

  def requestPathInfo: Option[String] = Option(p.getRequestPathInfo)

  // TODO: getWrappedSession

  def contentType: Option[String] = Option(p.getContentType)

  def browserDetails: ScaladinRequest.BrowserDetails = new ScaladinRequest.BrowserDetails {
    val p = wrappedRequest.p.getBrowserDetails
  }

  def locale: Option[Locale] = Option(p.getLocale)

  def remoteAddr: Option[String] = Option(p.getRemoteAddr)

  def secure: Boolean = p.isSecure

  // TODO: getHeader

  //  def deploymentConfiguration: WrappedRequest.DeploymentConfiguration = new WrappedRequest.DeploymentConfiguration {
  //    val p = wrappedRequest.p.getDeploymentConfiguration
  //  }

}