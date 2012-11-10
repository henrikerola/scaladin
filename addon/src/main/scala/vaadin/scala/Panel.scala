package vaadin.scala

import vaadin.scala.internal.ListenersTrait
import vaadin.scala.internal.ClickListener
import vaadin.scala.mixins.PanelMixin

package mixins {
  trait PanelMixin extends AbstractComponentContainerMixin { self: com.vaadin.ui.Panel => }
}

class Panel(override val p: com.vaadin.ui.Panel with PanelMixin = new com.vaadin.ui.Panel with PanelMixin) extends AbstractComponentContainer(p) with Focusable {

  // Vaadin Panel sets a default content (VerticalLayout) for Panel but 
  // we must to reset a default content because Panel with PaneMixin is used.
  content = new VerticalLayout {
    margin = true
  }

  def content: ComponentContainer = wrapperFor[ComponentContainer](p.getContent).get
  def content_=(content: ComponentContainer) = p.setContent(content.p)

  def scrollLeft = p.getScrollLeft
  def scrollLeft_=(scrollLeft: Int) = p.setScrollLeft(scrollLeft)

  def scrollTop = p.getScrollTop
  def scrollTop_=(scrollTop: Int) = p.setScrollTop(scrollTop)

  lazy val clickListeners = new ListenersTrait[ClickEvent, ClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.MouseEvents.ClickListener])
    override def addListener(elem: ClickEvent => Unit) = p.addClickListener(new ClickListener(elem))
    override def removeListener(elem: ClickListener) = p.removeClickListener(elem)
  }

  // TODO: actions
}
