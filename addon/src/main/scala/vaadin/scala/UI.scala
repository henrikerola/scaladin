package vaadin.scala

import scala.collection.mutable
import vaadin.scala.internal.WrapperUtil

object UI {
  def current: UI = com.vaadin.ui.UI.getCurrent.asInstanceOf[UI]
  def current_=(root: Option[UI]): Unit = com.vaadin.ui.UI.setCurrent(root.getOrElse(null))
  def current_=(root: UI): Unit = com.vaadin.ui.UI.setCurrent(root)
}

/**
 * @see com.vaadin.ui.UI
 * @author Henri Kerola / Vaadin
 */
abstract class UI extends com.vaadin.ui.UI { root =>

  protected def init(request: com.vaadin.server.WrappedRequest): Unit = {
    init(new WrappedRequest {
      val p = request
    })
  }

  protected def init(request: WrappedRequest): Unit

  def uiId: Int = getUIId
  //def rootId_=(rootId: Int): Unit = setUIId(rootId)

  def windows: mutable.Set[Window] = new mutable.Set[Window] {
    import scala.collection.JavaConversions.asScalaIterator

    def contains(key: Window) = {
      getWindows.contains(key.p)
    }
    def iterator: Iterator[Window] = {
      getWindows.iterator.map(WrapperUtil.wrapperFor[Window](_).get) // TODO
    }
    def +=(elem: Window) = { addWindow(elem.p); this }
    def -=(elem: Window) = { removeWindow(elem.p); this }
  }

  def scrollIntoView(component: Component): Unit = scrollIntoView(component.p)

  content = new VerticalLayout {
    margin = true
  }

  def content: ComponentContainer = WrapperUtil.wrapperFor[ComponentContainer](getContent).get
  def content_=(content: ComponentContainer) = setContent(content.p)

  def resizeLazy: Boolean = isResizeLazy
  def resizeLazy_=(resizeLazy: Boolean): Unit = setResizeLazy(resizeLazy)

  // TODO: the same clickListeners can be found from Panel and Embedded, use a trait instead of copy-pasting? 
  lazy val clickListeners = new ListenersTrait[ClickEvent, ClickListener] {
    override def listeners = getListeners(classOf[com.vaadin.event.MouseEvents.ClickListener])
    override def addListener(elem: ClickEvent => Unit) = root.addListener(new ClickListener(elem))
    override def removeListener(elem: ClickListener) = removeListener(elem)
  }

  // TODO: setScrollTop

  // TODO: page

}