package vaadin.scala

import scala.collection.mutable
import vaadin.scala.internal.WrapperUtil

object Root {
  def current: Root = com.vaadin.ui.Root.getCurrent.asInstanceOf[Root]
  def current_=(root: Option[Root]): Unit = com.vaadin.ui.Root.setCurrent(root.getOrElse(null))
  def current_=(root: Root): Unit = com.vaadin.ui.Root.setCurrent(root)
}

/**
 * @see com.vaadin.ui.Root
 * @author Henri Kerola / Vaadin
 */
abstract class Root extends com.vaadin.ui.Root { root =>

  protected def init(request: com.vaadin.terminal.WrappedRequest): Unit = {
    init(new WrappedRequest {
      val p = request
    })
  }

  protected def init(request: WrappedRequest): Unit

  def application: Application = getApplication.asInstanceOf[Application]
  def application_=(application: Application): Unit = setApplication(application)

  def rootId: Int = getRootId
  def rootId_=(rootId: Int): Unit = setRootId(rootId)

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