package vaadin.scala

import vaadin.scala.mixins.PopupViewMixin
import vaadin.scala.internal.ListenersTrait
import vaadin.scala.internal.PopupVisibilityListener

package mixins {
  trait PopupViewMixin extends AbstractComponentContainerMixin
}

object PopupView {
  case class PopupVisibilityEvent(popupView: PopupView, popupVisible: Boolean) extends Event
}

/**
 * @see com.vaadin.ui.PopupView
 * @author Henri Kerola / Vaadin
 */
class PopupView(override val p: com.vaadin.ui.PopupView with PopupViewMixin = new com.vaadin.ui.PopupView("", null) with PopupViewMixin) extends AbstractComponentContainer(p) {

  def minimizedHtmlValue: String = p.getContent.getMinimizedValueAsHTML
  def minimizedHtmlValue_=(minimizedHtmlValue: String): Unit = {
    val c = p.getContent.getPopupComponent
    p.setContent(new com.vaadin.ui.PopupView.Content {
      def getMinimizedValueAsHTML = minimizedHtmlValue
      def getPopupComponent = c
    })
  }

  def popupContent: Option[Component] = wrapperFor(p.getContent.getPopupComponent)
  // TODO is setter for Option[Component] needed?
  def popupContent_=(popupContent: Component): Unit = this.popupContent = () => popupContent
  def popupContent_=(popupContent: () => Component): Unit = {
    val html = p.getContent.getMinimizedValueAsHTML
    p.setContent(new com.vaadin.ui.PopupView.Content {
      def getMinimizedValueAsHTML = html
      def getPopupComponent = popupContent().p
    })
  }

  def popupVisible: Boolean = p.isPopupVisible
  def popupVisible_=(popupVisible: Boolean): Unit = p.setPopupVisible(popupVisible)

  def hideOnMouseOut: Boolean = p.isHideOnMouseOut
  def hideOnMouseOut_=(hideOnMouseOut: Boolean): Unit = p.setHideOnMouseOut(hideOnMouseOut)

  lazy val popupVisibilityListeners = new ListenersTrait[PopupView.PopupVisibilityEvent, PopupVisibilityListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.PopupView#PopupVisibilityEvent])
    override def addListener(elem: PopupView.PopupVisibilityEvent => Unit) = p.addListener(new PopupVisibilityListener(elem))
    override def removeListener(elem: PopupVisibilityListener) = p.removeListener(elem)
  }

}