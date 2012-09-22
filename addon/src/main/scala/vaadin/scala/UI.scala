package vaadin.scala

import scala.collection.mutable
import vaadin.scala.internal.WrapperUtil
import vaadin.scala.internal.ClickListener
import vaadin.scala.internal.ListenersTrait
import vaadin.scala.mixins.AbstractComponentContainerMixin
import vaadin.scala.internal.WrappedVaadinUI

object UI {
  //def current: UI = com.vaadin.ui.UI.getCurrent
  //def current_=(root: Option[UI]): Unit = com.vaadin.ui.UI.setCurrent(root.getOrElse(null))
  //def current_=(root: UI): Unit = com.vaadin.ui.UI.setCurrent(root.p)
}

/**
 * @see com.vaadin.ui.UI
 * @author Henri Kerola / Vaadin
 */
abstract class UI(override val p: WrappedVaadinUI = new WrappedVaadinUI) extends AbstractComponentContainer(p) {

  def init(request: ScaladinRequest): Unit

  def uiId: Int = p.getUIId
  //def rootId_=(rootId: Int): Unit = setUIId(rootId)

  def windows: mutable.Set[Window] = new mutable.Set[Window] {
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

  content = new VerticalLayout {
    margin = true
  }

  def content: ComponentContainer = WrapperUtil.wrapperFor[ComponentContainer](p.getContent).get
  def content_=(content: ComponentContainer) = p.setContent(content.p)

  def resizeLazy: Boolean = p.isResizeLazy
  def resizeLazy_=(resizeLazy: Boolean): Unit = p.setResizeLazy(resizeLazy)
  // TODO: the same clickListeners can be found from Panel and Embedded, use a trait instead of copy-pasting? 
  lazy val clickListeners = new ListenersTrait[ClickEvent, ClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.MouseEvents.ClickListener])
    override def addListener(elem: ClickEvent => Unit) = p.addListener(new ClickListener(elem))
    override def removeListener(elem: ClickListener) = p.removeListener(elem)
  }

  // TODO: setScrollTop

  // TODO: page

}