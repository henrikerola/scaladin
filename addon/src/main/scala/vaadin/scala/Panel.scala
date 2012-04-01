package vaadin.scala

class Panel(implicit wrapper: WrapperRegistry) extends AbstractComponentContainer with Focusable {

  val p = new com.vaadin.ui.Panel()

  /*-
  def this(caption: String = null, width: Option[Measure] = 100 percent, height: Option[Measure] = None, style: String = null) {
    this()
    this.caption = caption
    this.width = width
    this.height = height
    p.setStyleName(style)
  }*/

  def content: ComponentContainer = wrapper.get[ComponentContainer](p.getContent).get
  def content_=(content: ComponentContainer) = p.setContent(content.p)

  def scrollable = p.isScrollable
  def scrollable_=(scrollable: Boolean) = p.setScrollable(scrollable)

  def scrollLeft = p.getScrollLeft
  def scrollLeft_=(scrollLeft: Int) = p.setScrollLeft(scrollLeft)

  def scrollTop = p.getScrollTop
  def scrollTop_=(scrollTop: Int) = p.setScrollTop(scrollTop)

  lazy val clickListeners = new ListenersTrait[ClickEvent => Unit, ClickListener] {
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
