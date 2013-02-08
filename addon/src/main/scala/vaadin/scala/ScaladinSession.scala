package vaadin.scala

import internal.{ WrapperUtil, ErrorHandler }
import java.util.Locale
import java.util.concurrent.locks.Lock
import collection.JavaConverters._
import collection.mutable
import vaadin.scala.mixins.VaadinSessionMixin

package mixins {
  trait VaadinSessionMixin extends ScaladinMixin
}

object ScaladinSession {
  def current: ScaladinSession = WrapperUtil.wrapperFor[ScaladinSession](com.vaadin.server.VaadinSession.getCurrent).orNull
  def current_=(session: Option[ScaladinSession]): Unit = com.vaadin.server.VaadinSession.setCurrent(if (session.isDefined) session.get.p else null)
  def current_=(session: ScaladinSession): Unit = com.vaadin.server.VaadinSession.setCurrent(session.p)

  case class ErrorEvent(throwable: Throwable) extends Event

  val DefaultErrorHandler: (ScaladinSession.ErrorEvent => Unit) = e =>
    com.vaadin.server.DefaultErrorHandler.doDefault(new com.vaadin.server.ErrorEvent(e.throwable))
}

/**
 * @see com.vaadin.server.VaadinSession
 * @author Henri Kerola / Vaadin
 */
class ScaladinSession(val p: com.vaadin.server.VaadinSession with VaadinSessionMixin) extends Wrapper { vaadinSession =>

  p.wrapper = this

  errorHandler = ScaladinSession.DefaultErrorHandler

  def cumulativeRequestDuration: Long = p.getCumulativeRequestDuration

  // TODO: p.setLastRequestTime
  def lastRequestDuration: Long = p.getLastRequestDuration

  //TODO: setter ?
  def lastRequestTimestamp: Long = p.getLastRequestTimestamp

  // TODO: getSession: WrappedSession

  def configuration: DeploymentConfiguration = new DeploymentConfiguration {
    val p = vaadinSession.p.getConfiguration
  }

  def locale: Locale = p.getLocale
  def locale_=(locale: Option[Locale]): Unit = p.setLocale(locale.orNull)
  def locale_=(locale: Locale): Unit = p.setLocale(locale)

  def errorHandler: ScaladinSession.ErrorEvent => Unit =
    p.getErrorHandler.asInstanceOf[ErrorHandler].action
  def errorHandler_=(errorHandler: ScaladinSession.ErrorEvent => Unit): Unit =
    p.setErrorHandler(new ErrorHandler(errorHandler))

  // TODO ConverterFactory, bootstrapListener, getGlobalResourceHandler

  // TODO better name than uIs
  def uIs: Iterable[UI] = p.getUIs.asScala.map(wrapperFor[UI](_).get)

  def lockInstance: Lock = p.getLockInstance

  def lock(): Unit = p.lock()

  def unlock(): Unit = p.unlock()

  def setAttribute(name: String, value: Any): Unit = p.setAttribute(name, value)
  def getAttribute(name: String): Any = p.getAttribute(name)

  def setAttribute[T](valueType: Class[T], value: T): Unit = p.setAttribute(valueType, value)
  def getAttribute[T](valueType: Class[T]): T = p.getAttribute(valueType)

  def nextUiId(): Int = p.getNextUIid

  def preserveOnRefreshUIs: mutable.Map[String, Integer] = p.getPreserveOnRefreshUIs.asScala

  def addUI(ui: UI): Unit = p.addUI(ui.p)

  // TODO: UI Providers

  def service: ScaladinService = wrapperFor[ScaladinService](p.getService).get

  // TODO: this should be overridable?
  def close(): Unit = p.close()

  def isClosing: Boolean = p.isClosing

}
