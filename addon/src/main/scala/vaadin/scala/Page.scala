package vaadin.scala

import vaadin.scala.internal.WrapperUtil
import vaadin.scala.internal.ListenersTrait
import vaadin.scala.internal.FragmentChangedListener
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

  def current: Page = WrapperUtil.wrapperFor[Page](com.vaadin.server.Page.getCurrent).get

  case class BrowserWindowResizeEvent(page: Page, width: Int, height: Int) extends Event
  case class FragmentChangedEvent(page: Page, fragment: Option[String]) extends Event
}

trait Page extends Wrapper { page =>

  val p: com.vaadin.server.Page

  def setFragment(fragment: Option[String], fireEvent: Boolean): Unit = p.setFragment(fragment.orNull, fireEvent)
  def setFragment(fragment: String, fireEvent: Boolean): Unit = p.setFragment(fragment, fireEvent)

  def fragment: Option[String] = Option(p.getFragment)
  def fragment_=(fragment: Option[String]): Unit = p.setFragment(fragment.orNull)
  def fragment_=(fragment: String): Unit = p.setFragment(fragment)

  //  def webBrowser: WebBrowser = new WebBrowser {
  //    val p = page.p.getWebBrowser
  //  }

  def browserWindowSize: (Int, Int) = (browserWindowWidth, browserWindowHeight)
  def browserWindowSize_=(size: (Int, Int)): Unit = p.setBrowserWindowSize(size._1, size._2)

  def browserWindowHeight: Int = p.getBrowserWindowHeight
  def browserWindowHeight_=(browserWindowHeight: Int): Unit = p.setBrowserWindowSize(browserWindowWidth, browserWindowHeight)

  def browserWindowWidth: Int = p.getBrowserWindowWidth
  def browserWindowWidth_=(browserWindowWidth: Int): Unit = p.setBrowserWindowSize(browserWindowWidth, browserWindowHeight)

  //  def javaScript: JavaScript = new JavaScript {
  //    val p = page.p.getJavaScript
  //  }

  def location: URI = p.getLocation
  def location_=(url: URI): Unit = p.setLocation(url)
  def location_=(url: String): Unit = p.setLocation(url)

  def open(url: String, windowName: String): Unit = p.open(url, windowName)

  def open(url: String, windowName: String, width: Int, height: Int, border: Page.Border.Value): Unit = p.open(url, windowName, width, height, BorderStyle.values.apply(border.id))

  def setTitle(title: String): Unit = p.setTitle(title)

  lazy val fragmentChangedListeners = new ListenersTrait[Page.FragmentChangedEvent, FragmentChangedListener] {
    override def listeners = null // TODO
    override def addListener(elem: Page.FragmentChangedEvent => Unit) = p.addFragmentChangedListener(new FragmentChangedListener(elem))
    override def removeListener(elem: FragmentChangedListener) = p.removeFragmentChangedListener(elem)
  }

  lazy val browserWindowResizeListeners = new ListenersTrait[Page.BrowserWindowResizeEvent, BrowserWindowResizeListener] {
    override def listeners = null // TODO
    override def addListener(elem: Page.BrowserWindowResizeEvent => Unit) = p.addBrowserWindowResizeListener(new BrowserWindowResizeListener(elem))
    override def removeListener(elem: BrowserWindowResizeListener) = p.removeBrowserWindowResizeListener(elem)
  }
}