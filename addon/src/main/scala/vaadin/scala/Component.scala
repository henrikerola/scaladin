package vaadin.scala

import scala.collection.mutable
import vaadin.scala.mixins.ComponentMixin
import java.util.Locale

package mixins {

  trait ScaladinMixin {
    private[this] var _wrapper: Wrapper = _
    def wrapper: Wrapper = _wrapper
    def wrapper_=(wrapper: Wrapper) = {
      if (_wrapper != null)
        throw new RuntimeException("wrapper cannot be reset once set")
      else
        _wrapper = wrapper
    }
  }

  trait ScaladinInterfaceMixin {
    private[this] var _wrapper: InterfaceWrapper = _
    def wrapper: InterfaceWrapper = _wrapper
    def wrapper_=(wrapper: InterfaceWrapper) = {
      if (_wrapper != null)
        throw new RuntimeException("wrapper cannot be reset once set")
      else
        _wrapper = wrapper
    }
  }

  trait TypedScaladinMixin[C <: Wrapper] extends ScaladinMixin {
    override def wrapper: C = super.wrapper.asInstanceOf[C]
  }

  trait ComponentMixin extends ScaladinMixin { self: com.vaadin.ui.Component =>
    def wrapperComponent: Component = wrapper.asInstanceOf[Component]
  }
}

trait Component extends Wrapper {
  def p: com.vaadin.ui.Component with ComponentMixin

  // TODO: add methods styleName, addStyleName, removeStyleName?

  def styleName: String = p.getStyleName // TODO: should be Option[String]?
  def styleName_=(styleName: String) { p.setStyleName(styleName) }

  lazy val styleNames: mutable.Set[String] = new mutable.Set[String] with Serializable {
    def contains(key: String) = p.getStyleName().split(" ").iterator.contains(key)
    def iterator: Iterator[String] = p.getStyleName().split(" ").iterator
    def +=(elem: String) = { elem.split(" ").foreach(p.addStyleName(_)); this }
    def -=(elem: String) = { p.removeStyleName(elem); this }
  }

  def enabled: Boolean = p.isEnabled
  def enabled_=(enabled: Boolean) { p.setEnabled(enabled) }

  def visible: Boolean = p.isVisible
  def visible_=(visible: Boolean) { p.setVisible(visible) }

  // TODO parent setter?
  def parent: Option[Component] = wrapperFor(p.getParent())

  def readOnly: Boolean = p.isReadOnly
  def readOnly_=(readOnly: Boolean) { p.setReadOnly(readOnly) }

  def caption: Option[String] = Option(p.getCaption)
  def caption_=(caption: Option[String]) { p.setCaption(caption.orNull) }
  def caption_=(caption: String) { p.setCaption(caption) }

  def icon: Option[Resource] = wrapperFor[Resource](p.getIcon)
  def icon_=(icon: Option[Resource]) { p.setIcon(peerFor(icon)) }
  def icon_=(icon: Resource) { p.setIcon(icon.p) }

  def ui: UI = wrapperFor(p.getUI).orNull

  def locale: Option[Locale] = Option(p.getLocale)

  def id: Option[String] = Option(p.getId)
  def id_=(id: Option[String]) { p.setId(id.orNull) }
  def id_=(id: String) { p.setId(id) }

  //override def toString = p.toString

  // TODO: ..
}