package vaadin.scala

import com.vaadin.ui.{ PopupView => VaadinPopupView }
import event.Event
import vaadin.scala.mixins.PopupViewMixin
import vaadin.scala.internal.ListenersTrait
import vaadin.scala.internal.PopupVisibilityListener

package mixins {
  trait PopupViewMixin extends AbstractComponentMixin with HasComponentsMixin { self: com.vaadin.ui.PopupView => }
}

object PopupView {
  case class PopupVisibilityEvent(popupView: PopupView, popupVisible: Boolean) extends Event
}

/**
 * @see com.vaadin.ui.PopupView
 * @author Henri Kerola / Vaadin
 */
class PopupView(
  override val p: VaadinPopupView with PopupViewMixin = new VaadinPopupView("", null) with PopupViewMixin)
    extends AbstractComponent(p) with HasComponents {

  def minimizedHtmlValue: String = p.getContent.getMinimizedValueAsHTML
  def minimizedHtmlValue_=(minimizedHtmlValue: String) {
    val c = p.getContent.getPopupComponent
    p.setContent(new com.vaadin.ui.PopupView.Content {
      def getMinimizedValueAsHTML = minimizedHtmlValue
      def getPopupComponent = c
    })
  }

  def popupContent: Option[Component] = wrapperFor(p.getContent.getPopupComponent)
  // TODO is setter for Option[Component] needed?
  def popupContent_=(popupContent: Component) { this.popupContent = () => popupContent }
  def popupContent_=(popupContent: () => Component) {
    val html = p.getContent.getMinimizedValueAsHTML
    p.setContent(new com.vaadin.ui.PopupView.Content {
      def getMinimizedValueAsHTML = html
      def getPopupComponent = popupContent().p
    })
  }

  def popupVisible: Boolean = p.isPopupVisible
  def popupVisible_=(popupVisible: Boolean) { p.setPopupVisible(popupVisible) }

  def hideOnMouseOut: Boolean = p.isHideOnMouseOut
  def hideOnMouseOut_=(hideOnMouseOut: Boolean) { p.setHideOnMouseOut(hideOnMouseOut) }

  lazy val popupVisibilityListeners = new ListenersTrait[PopupView.PopupVisibilityEvent, PopupVisibilityListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.PopupView.PopupVisibilityEvent])
    override def addListener(elem: PopupView.PopupVisibilityEvent => Unit) =
      p.addPopupVisibilityListener(new PopupVisibilityListener(elem))
    override def removeListener(elem: PopupVisibilityListener) = p.removePopupVisibilityListener(elem)
  }

}