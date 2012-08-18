package vaadin.scala

import vaadin.scala.internal.WrapperUtil

object Page {

  object Border extends Enumeration {
    import com.vaadin.terminal.Page._
    val None = Value(BORDER_NONE)
    val Minimal = Value(BORDER_MINIMAL)
    val Default = Value(BORDER_DEFAULT)
  }

  def current: Page = WrapperUtil.wrapperFor[Page](com.vaadin.terminal.Page.getCurrent).get

  case class BrowserWindowResizeEvent(page: Page, width: Int, height: Int) extends Event
  case class FragmentChangedEvent(page: Page, fragment: Option[String]) extends Event
}

trait Page extends Wrapper { page =>

  val p: com.vaadin.terminal.Page

  def setFragment(fragment: String, fireEvent: Boolean) = p.setFragment(fragment, fireEvent)

  def fragment: Option[String] = Option(p.getFragment)
  def fragment_=(fragment: String) = p.setFragment(fragment)

  def webBrowser: WebBrowser = new WebBrowser {
    val p = page.p.getWebBrowser
  }

  // setBrowserWindowSize

  def browserWindowHeight: Int = p.getBrowserWindowHeight
  def browserWindowHeight_=(browserWindowHeight: Int): Unit = p.setBrowserWindowSize(null, browserWindowHeight)

  def browserWindowWidth: Int = p.getBrowserWindowWidth
  def browserWindowWidth_=(browserWindowWidth: Int): Unit = p.setBrowserWindowSize(browserWindowWidth, null)

  def javaScript: JavaScript = new JavaScript {
    val p = page.p.getJavaScript
  }

  def open(resource: Resource): Unit = p.open(resource.p)

  def open(resource: Resource, windowName: String): Unit = p.open(resource.p, windowName)

  def open(resource: Resource, windowName: String, width: Int, height: Int, border: Page.Border.Value): Unit = p.open(resource.p, windowName, width, height, border.id)

  // setTitle

  //  lazy val fragmentChangedListeners = new ListenersTrait[Page.FragmentChangedEvent, FragmentChangedListener] {
  //    override def listeners = p.getListeners(classOf[com.vaadin.ui.UriFragmentUtility#FragmentChangedEvent])
  //    override def addListener(elem: Page.FragmentChangedEvent => Unit) = p.addListener(new FragmentChangedListener(elem))
  //    override def removeListener(elem: FragmentChangedListener) = p.removeListener(elem)
  //  }

}