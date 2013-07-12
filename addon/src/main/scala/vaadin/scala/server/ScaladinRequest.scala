package vaadin.scala.server

import java.util.Locale
import java.io.InputStream
import scala.collection.JavaConverters._
import scala.collection.mutable

trait ScaladinRequest extends Serializable { scaladinRequest =>

  val p: com.vaadin.server.VaadinRequest

  def getParameter(parameterName: String): Option[String] = Option(p.getParameter(parameterName))

  def parameterMap: mutable.Map[String, Array[String]] = p.getParameterMap.asScala

  def contentLength: Int = p.getContentLength

  // TODO: getInputStream can throw IOException handle somehow more Scala-way?
  def inputStream: InputStream = p.getInputStream

  def getAttribute(name: String): Option[Any] = Option(p.getAttribute(name))
  def setAttribute(name: String, value: Any): Unit = p.setAttribute(name, value)
  def setAttribute(name: String, value: Option[Any]): Unit = p.setAttribute(name, value.orNull)

  def pathInfo: Option[String] = Option(p.getPathInfo)

  def contextPath: Option[String] = Option(p.getContextPath) // TODO can be null or not?

  def wrappedSession: com.vaadin.server.WrappedSession = p.getWrappedSession // TODO: return wrapped WrappedSession
  def wrappedSession(allowSessionCreation: Boolean): Option[com.vaadin.server.WrappedSession] = Option(p.getWrappedSession) // TODO: return wrapped WrappedSession

  def contentType: Option[String] = Option(p.getContentType)

  def locale: Locale = p.getLocale

  def remoteAddr: Option[String] = Option(p.getRemoteAddr)

  def isSecure: Boolean = p.isSecure

  def getHeader(headerName: String): Option[String] = Option(p.getHeader(headerName))

  def service: com.vaadin.server.VaadinService = p.getService

}