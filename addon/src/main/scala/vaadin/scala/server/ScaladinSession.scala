package vaadin.scala.server

import java.util.Locale
import java.util.concurrent.locks.Lock
import vaadin.scala.converter.{ DefaultConverterFactory, ConverterFactory }

import collection.JavaConverters._
import collection.mutable
import org.jsoup.nodes.{ Document, Node }
import vaadin.scala.event.Event
import vaadin.scala.internal._
import vaadin.scala._
import com.vaadin.server.UIProvider
import vaadin.scala.mixins.ScaladinMixin
import vaadin.scala.server.mixins.VaadinSessionMixin
import com.vaadin.server.VaadinSession.State

package mixins {
  trait VaadinSessionMixin extends ScaladinMixin
}

object ScaladinSession {
  def current: ScaladinSession = WrapperUtil.wrapperFor[ScaladinSession](com.vaadin.server.VaadinSession.getCurrent).orNull
  def current_=(session: Option[ScaladinSession]): Unit = com.vaadin.server.VaadinSession.setCurrent(if (session.isDefined) session.get.p else null)
  def current_=(session: ScaladinSession): Unit = com.vaadin.server.VaadinSession.setCurrent(session.p)

  case class ErrorEvent(p: com.vaadin.server.ErrorEvent) extends Event {
    val throwable = p.getThrowable
  }

  sealed class BootstrapResponse(request: ScaladinRequest, session: ScaladinSession, uiClass: Class[_ <: UI], uiProvider: UIProvider) extends Event
  case class BootstrapFragmentResponse(request: ScaladinRequest, session: ScaladinSession, uiClass: Class[_ <: UI], uiProvider: UIProvider, nodes: mutable.Buffer[Node])
    extends BootstrapResponse(request, session, uiClass, uiProvider)
  case class BootstrapPageResponse(request: ScaladinRequest, session: ScaladinSession, uiClass: Class[_ <: UI], uiProvider: UIProvider, document: Document, headers: mutable.Map[String, String])
    extends BootstrapResponse(request, session, uiClass, uiProvider)

  val DefaultErrorHandler: (ScaladinSession.ErrorEvent => Unit) = e =>
    com.vaadin.server.DefaultErrorHandler.doDefault(e.p)
}

/**
 * @see com.vaadin.server.VaadinSession
 * @author Henri Kerola / Vaadin
 */
class ScaladinSession(val p: com.vaadin.server.VaadinSession with VaadinSessionMixin) extends Wrapper { vaadinSession =>

  p.wrapper = this

  def init(): Unit = {
    errorHandler = ScaladinSession.DefaultErrorHandler
    converterFactory = DefaultConverterFactory
  }

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

  def converterFactory: ConverterFactory = wrapperFor(p.getConverterFactory).get
  def converterFactory_=(converterFactory: ConverterFactory): Unit =
    p.setConverterFactory(converterFactory.pConverterFactory)

  // getGlobalResourceHandler

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

  def addUI(ui: UI): Unit = p.addUI(ui.p)

  // TODO: UI Providers

  def service: ScaladinService = wrapperFor[ScaladinService](p.getService).get

  // TODO: this should be overridable?
  def close(): Unit = p.close()

  def isClosing: Boolean = p.getState() != State.OPEN

  lazy val bootstrapFragmentListeners: ListenersSet[ScaladinSession.BootstrapFragmentResponse => Unit] =
    new ListenersTrait[ScaladinSession.BootstrapFragmentResponse, BootstrapFragmentListener] {
      override def listeners = null // FIXME
      override def addListener(elem: ScaladinSession.BootstrapFragmentResponse => Unit) =
        p.addBootstrapListener(new BootstrapFragmentListener(elem))
      override def removeListener(elem: BootstrapFragmentListener) = p.removeBootstrapListener(elem)
    }

  lazy val bootstrapPageListeners: ListenersSet[ScaladinSession.BootstrapPageResponse => Unit] =
    new ListenersTrait[ScaladinSession.BootstrapPageResponse, BootstrapPageListener] {
      override def listeners = null // FIXME
      override def addListener(elem: ScaladinSession.BootstrapPageResponse => Unit) =
        p.addBootstrapListener(new BootstrapPageListener(elem))
      override def removeListener(elem: BootstrapPageListener) = p.removeBootstrapListener(elem)
    }

}
