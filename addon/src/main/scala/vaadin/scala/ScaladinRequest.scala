package vaadin.scala

import java.util.Locale
import java.io.InputStream
import java.util.Date
import scala.collection.JavaConverters._
import scala.collection.mutable

object ScaladinRequest {

  trait DeploymentConfiguration {

    val p: com.vaadin.server.DeploymentConfiguration

    def isProductionMode: Boolean = p.isProductionMode

    def isXsrfProtectionEnabled: Boolean = p.isXsrfProtectionEnabled

    def resourceCacheTime: Int = p.getResourceCacheTime

    def heartbeatInterval: Int = p.getHeartbeatInterval

    def isIdleUICleanupEnabled: Boolean = p.isIdleUICleanupEnabled

    def initParameters: java.util.Properties = p.getInitParameters

    def getApplicationOrSystemProperty(propertyName: String, defaultValue: String): String = p.getApplicationOrSystemProperty(propertyName, defaultValue)
  }
}

trait ScaladinRequest { scaladinRequest =>

  val p: com.vaadin.server.VaadinRequest

  def getParameter(parameterName: String): Option[String] = Option(p.getParameter(parameterName))

  def parameterMap: mutable.Map[String, Array[String]] = p.getParameterMap.asScala

  def contentLength: Int = p.getContentLength

  // TODO: getInputStream can throw IOException handle somehow more Scala-way?
  def inputStream: InputStream = p.getInputStream

  def getAttribute(name: String): Option[Any] = Option(p.getAttribute(name))
  def setAttribute(name: String, value: Any): Unit = p.setAttribute(name, value)
  def setAttribute(name: String, value: Option[Any]): Unit = p.setAttribute(name, value.orNull)

  def requestPathInfo: Option[String] = Option(p.getRequestPathInfo)

  def wrappedSession: com.vaadin.server.WrappedSession = p.getWrappedSession // TODO: return wrapped WrappedSession
  def wrappedSession(allowSessionCreation: Boolean): Option[com.vaadin.server.WrappedSession] = Option(p.getWrappedSession) // TODO: return wrapped WrappedSession

  def contentType: Option[String] = Option(p.getContentType)

  def locale: Locale = p.getLocale

  def remoteAddr: Option[String] = Option(p.getRemoteAddr)

  def isSecure: Boolean = p.isSecure

  def getHeader(headerName: String): Option[String] = Option(p.getHeader(headerName))

  def service: com.vaadin.server.VaadinService = p.getService

}