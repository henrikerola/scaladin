package vaadin.scala

import vaadin.scala.UI.PollEvent
import vaadin.scala.event.{ ComponentEvent, ClickNotifier }
import scala.collection.mutable
import vaadin.scala.internal.{ PollListener, ListenersTrait, WrapperUtil, WrappedVaadinUI }
import vaadin.scala.server.{ ScaladinSession, ScaladinRequest, Page }

object UI {
  def current: UI = WrapperUtil.wrapperFor[UI](com.vaadin.ui.UI.getCurrent).orNull
  def current_=(ui: Option[UI]): Unit = com.vaadin.ui.UI.setCurrent(if (ui.isDefined) ui.get.p else null)
  def current_=(ui: UI): Unit = com.vaadin.ui.UI.setCurrent(ui.p)

  case class PollEvent(ui: UI) extends ComponentEvent(ui)
}

/**
 * @see com.vaadin.ui.UI
 * @author Henri Kerola / Vaadin
 */
abstract class UI(override val p: WrappedVaadinUI)
    extends AbstractSingleComponentContainer(p) with DelayedInit with ClickNotifier {

  private[this] var initCode: Option[() => Unit] = None

  private[this] var _title: Option[String] = None
  private[this] var _theme: Option[String] = None
  private[this] var _widgetset: Option[String] = None
  private[this] var _preserveOnRefresh: Boolean = false
  private[this] var _pushMode: PushMode.Value = PushMode.Disabled

  def this(
    title: String = null,
    theme: String = null,
    widgetset: String = null,
    preserveOnRefresh: Boolean = false,
    pushMode: PushMode.Value = PushMode.Disabled,
    p: WrappedVaadinUI = new WrappedVaadinUI) {
    this(p)
    this._title = Option(title)
    this._theme = Option(theme)
    this._widgetset = Option(widgetset)
    this._preserveOnRefresh = preserveOnRefresh
    this._pushMode = pushMode
  }

  def delayedInit(body: => Unit) {
    content = new VerticalLayout {
      margin = true
    }
    initCode = Some(() => body)
  }

  def doInit(request: ScaladinRequest) {
    for (proc <- initCode) proc()
    init(request)
  }

  def init(request: ScaladinRequest) {
    // This can be overridden in subclass if access to ScaladinRequest is needed in initialization code  
  }

  /**
   * When preserveOnRefresh is true for the UI, this method can be
   * overridden to refresh the UI on a browser refresh.
   */
  def refresh(request: ScaladinRequest): Unit = {}

  def uiId: Int = p.getUIId

  lazy val windows: mutable.Set[Window] = new mutable.Set[Window] {
    import scala.collection.JavaConversions.asScalaIterator

    def contains(key: Window) = {
      p.getWindows.contains(key.p)
    }
    def iterator: Iterator[Window] = {
      p.getWindows.iterator.map(WrapperUtil.wrapperFor[Window](_).get) // TODO
    }
    def +=(elem: Window) = { p.addWindow(elem.p); this }
    def -=(elem: Window) = { p.removeWindow(elem.p); this }
  }

  def scrollIntoView(component: Component): Unit = p.scrollIntoView(component.p)

  def resizeLazy: Boolean = p.isResizeLazy
  def resizeLazy_=(resizeLazy: Boolean): Unit = p.setResizeLazy(resizeLazy)

  // TODO: setScrollTop

  def page: Page = new Page {
    val p = UI.this.p.getPage
  }

  def navigator: Navigator = wrapperFor[Navigator](p.getNavigator).orNull

  // Can be used in init if subclasses override
  protected def navigator_=(n: Navigator) {
    navigator_=(Option(n))
  }

  protected def navigator_=(n: Option[Navigator]) {
    p.setNavigator(n.map(_.p).orNull)
  }

  def lastHeartbeatTimestamp: Long = p.getLastHeartbeatTimestamp

  def close(): Unit = p.close()

  def isClosing: Boolean = p.isClosing

  def theme: String = Option(p.getTheme).getOrElse(_theme.orNull)
  def theme_=(theme: String): Unit = p.setTheme(theme)

  def title: Option[String] = _title

  def widgetset: Option[String] = _widgetset

  def preserveOnRefresh: Boolean = _preserveOnRefresh

  def session: ScaladinSession = wrapperFor(p.getSession) orNull

  def pushMode: PushMode.Value = _pushMode

  // TODO should return a Future
  def access(runnable: => Unit): Unit =
    p.access(new Runnable {
      def run(): Unit = runnable
    })

  def pollInterval: Int = p.getPollInterval
  def pollInterval_=(intervalInMillis: Int): Unit = p.setPollInterval(intervalInMillis)

  lazy val pollListeners: ListenersSet[PollEvent => Unit] =
    new ListenersTrait[PollEvent, PollListener] {
      override def listeners = p.getListeners(classOf[com.vaadin.event.UIEvents.PollEvent])
      override def addListener(elem: PollEvent => Unit) = p.addPollListener(new PollListener(elem))
      override def removeListener(elem: PollListener) = p.removePollListener(elem)
    }

  def push(): Unit = p.push()

  def pushConfiguration: PushConfiguration = new PushConfiguration {
    val p = UI.this.p.getPushConfiguration
  }
}