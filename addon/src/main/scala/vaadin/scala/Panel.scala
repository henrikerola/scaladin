package vaadin.scala

import vaadin.scala.event.{ ClickNotifier, ClickEvent }
import vaadin.scala.internal.ListenersTrait
import vaadin.scala.internal.ClickListener
import vaadin.scala.mixins.PanelMixin

package mixins {
  trait PanelMixin extends AbstractSingleComponentContainerMixin { self: com.vaadin.ui.Panel => }
}

class Panel(override val p: com.vaadin.ui.Panel with PanelMixin = new com.vaadin.ui.Panel with PanelMixin)
    extends AbstractSingleComponentContainer(p) with Component.Focusable with ClickNotifier {

  // Vaadin Panel sets a default content (VerticalLayout) for Panel but 
  // we must to reset a default content because Panel with PanelMixin is used.
  content = new VerticalLayout {
    margin = true
  }

  def scrollLeft: Int = p.getScrollLeft
  def scrollLeft_=(scrollLeft: Int) { p.setScrollLeft(scrollLeft) }

  def scrollTop: Int = p.getScrollTop
  def scrollTop_=(scrollTop: Int) { p.setScrollTop(scrollTop) }

  // TODO: actions
}
