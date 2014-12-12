package vaadin.scala

import java.util.Locale
import scala.collection.mutable
import vaadin.scala.server.Resource

package mixins {
  trait ComponentMixin extends ClientConnectorMixin { self: com.vaadin.ui.Component =>
    def wrapperComponent: Component = wrapper.asInstanceOf[Component]
  }
}

import vaadin.scala.mixins.ComponentMixin

object Component {
  trait Focusable extends Component {

    def p: com.vaadin.ui.Component.Focusable with ComponentMixin

    def focus() { p.focus() }

    def tabIndex: Int = p.getTabIndex
    def tabIndex_=(tabIndex: Int) { p.setTabIndex(tabIndex) }
  }
}

trait Component extends ClientConnector {
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

  def primaryStyleName: String = p.getPrimaryStyleName
  def primaryStyleName_=(primaryStyleName: String): Unit = p.setPrimaryStyleName(primaryStyleName)

  override def parent: Option[HasComponents] = super.parent.map(_.asInstanceOf[HasComponents])
  def parent_=(parent: HasComponents) = p.setParent(parent.p)
  def parent_=(parent: Option[HasComponents]) = p.setParent(parent.map(_.p).orNull)

  def enabled: Boolean = p.isEnabled
  def enabled_=(enabled: Boolean) { p.setEnabled(enabled) }

  def visible: Boolean = p.isVisible
  def visible_=(visible: Boolean) { p.setVisible(visible) }

  def readOnly: Boolean = p.isReadOnly
  def readOnly_=(readOnly: Boolean) { p.setReadOnly(readOnly) }

  def caption: Option[String] = Option(p.getCaption)
  def caption_=(caption: Option[String]) { p.setCaption(caption.orNull) }
  def caption_=(caption: String) { p.setCaption(caption) }

  def icon: Option[Resource] = wrapperFor(p.getIcon)
  def icon_=(icon: Option[Resource]) { p.setIcon(peerFor(icon)) }
  def icon_=(icon: Resource) { p.setIcon(icon.pResource) }

  def ui: UI = wrapperFor(p.getUI).orNull

  def locale: Option[Locale] = Option(p.getLocale)

  def id: Option[String] = Option(p.getId)
  def id_=(id: Option[String]) { p.setId(id.orNull) }
  def id_=(id: String) { p.setId(id) }

  //override def toString = p.toString

  // TODO: ..
}