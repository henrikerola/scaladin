package vaadin.scala

import vaadin.scala.mixins.PanelMixin

package mixins {
  trait PanelMixin extends AbstractComponentContainerMixin
}

class Panel(override val p: com.vaadin.ui.Panel with PanelMixin = new com.vaadin.ui.Panel with PanelMixin) extends AbstractComponentContainer(p) with Focusable {

  /*-
  def this(caption: String = null, width: Option[Measure] = 100 percent, height: Option[Measure] = None, style: String = null) {
    this()
    this.caption = caption
    this.width = width
    this.height = height
    p.setStyleName(style)
  }*/
  
  // Vaadin Panel sets a default content (VerticalLayout) for Panel but 
  // we must to reset a default content because Panel with PaneMixin is used.
  content = new VerticalLayout {
    margin = true
  }

  def content: ComponentContainer = wrapperFor[ComponentContainer](p.getContent).get
  def content_=(content: ComponentContainer) = p.setContent(content.p)

  def scrollable = p.isScrollable
  def scrollable_=(scrollable: Boolean) = p.setScrollable(scrollable)

  def scrollLeft = p.getScrollLeft
  def scrollLeft_=(scrollLeft: Int) = p.setScrollLeft(scrollLeft)

  def scrollTop = p.getScrollTop
  def scrollTop_=(scrollTop: Int) = p.setScrollTop(scrollTop)

  lazy val clickListeners = new ListenersTrait[ClickEvent, ClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.MouseEvents.ClickListener])
    override def addListener(elem: ClickEvent => Unit) = p.addListener(new ClickListener(elem))
    override def removeListener(elem: ClickListener) = p.removeListener(elem)
  }

  // TODO: actions

  //  def getComponents(): TraversableOnce[VaadinComponent] = getComponentIterator.asScala.toSeq
  //
  //  def add[C <: VaadinComponent](component: C = null): C = {
  //    addComponent(component)
  //    component
  //  }
}
