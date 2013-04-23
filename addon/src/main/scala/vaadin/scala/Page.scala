package vaadin.scala

import vaadin.scala.event.{ ClickEvent, Event }
import vaadin.scala.internal.WrapperUtil
import vaadin.scala.internal.ListenersTrait
import vaadin.scala.internal.UriFragmentChangedListener
import vaadin.scala.internal.BrowserWindowResizeListener
import com.vaadin.shared.ui.BorderStyle
import java.net.URI

object Page {

  object Border extends Enumeration {
    import com.vaadin.shared.ui.BorderStyle._
    val None = Value(NONE.ordinal)
    val Minimal = Value(MINIMAL.ordinal)
    val Default = Value(DEFAULT.ordinal)
  }

  def current: Page = Option(com.vaadin.server.Page.getCurrent) match {
    case Some(page) => new Page { val p = page }
    case None => null
  }

  case class BrowserWindowResizeEvent(page: Page, width: Int, height: Int) extends Event
  case class UriFragmentChangedEvent(page: Page, fragment: Option[String]) extends Event
}

trait Page extends Wrapper { page =>

  val p: com.vaadin.server.Page

  def setUriFragment(fragment: Option[String], fireEvent: Boolean) { p.setUriFragment(fragment.orNull, fireEvent) }
  def setUriFragment(fragment: String, fireEvent: Boolean) { p.setUriFragment(fragment, fireEvent) }

  def uriFragment: Option[String] = Option(p.getUriFragment)
  def uriFragment_=(fragment: Option[String]) { p.setUriFragment(fragment.orNull) }
  def uriFragment_=(fragment: String) { p.setUriFragment(fragment) }

  def webBrowser: WebBrowser = new WebBrowser {
    val p = page.p.getWebBrowser
  }

  def browserWindowSize: (Int, Int) = (browserWindowWidth, browserWindowHeight)
  def browserWindowSize_=(size: (Int, Int)) { p.updateBrowserWindowSize(size._1, size._2) }

  def browserWindowHeight: Int = p.getBrowserWindowHeight
  def browserWindowHeight_=(browserWindowHeight: Int) {
    p.updateBrowserWindowSize(browserWindowWidth, browserWindowHeight)
  }

  def browserWindowWidth: Int = p.getBrowserWindowWidth
  def browserWindowWidth_=(browserWindowWidth: Int) {
    p.updateBrowserWindowSize(browserWindowWidth, browserWindowHeight)
  }

  //  def javaScript: JavaScript = new JavaScript {
  //    val p = page.p.getJavaScript
  //  }

  def location: URI = p.getLocation
  def location_=(url: URI) { p.setLocation(url) }
  def location_=(url: String) { p.setLocation(url) }

  def open(url: String, windowName: String) { p.open(url, windowName) }

  def open(url: String, windowName: String, width: Int, height: Int, border: Page.Border.Value) {
    p.open(url, windowName, width, height, BorderStyle.values.apply(border.id))
  }

  def setTitle(title: String) { p.setTitle(title) }

  lazy val uriFragmentChangedListeners: ListenersSet[Page.UriFragmentChangedEvent => Unit] =
    new ListenersTrait[Page.UriFragmentChangedEvent, UriFragmentChangedListener] {
      override def listeners = null // TODO
      override def addListener(elem: Page.UriFragmentChangedEvent => Unit) =
        p.addUriFragmentChangedListener(new UriFragmentChangedListener(elem))
      override def removeListener(elem: UriFragmentChangedListener) = p.removeUriFragmentChangedListener(elem)
    }

  lazy val browserWindowResizeListeners: ListenersSet[Page.BrowserWindowResizeEvent => Unit] =
    new ListenersTrait[Page.BrowserWindowResizeEvent, BrowserWindowResizeListener] {
      override def listeners = null // TODO
      override def addListener(elem: Page.BrowserWindowResizeEvent => Unit) =
        p.addBrowserWindowResizeListener(new BrowserWindowResizeListener(elem))
      override def removeListener(elem: BrowserWindowResizeListener) = p.removeBrowserWindowResizeListener(elem)
    }
}