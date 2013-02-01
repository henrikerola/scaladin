package vaadin.scala

import scala.collection.mutable
import vaadin.scala.mixins.ComponentMixin
import vaadin.scala.mixins.AbstractComponentMixin
import vaadin.scala.mixins.ScaladinMixin
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

  trait TypedScaladinMixin[C <: Wrapper] extends ScaladinMixin {
    override def wrapper: C = super.wrapper.asInstanceOf[C]
  }

  trait ComponentMixin extends ScaladinMixin { self: com.vaadin.ui.Component =>
    def wrapperComponent: Component = wrapper.asInstanceOf[Component]
  }

  trait AbstractComponentSuperCalls {
    def attach(): Unit
    def detach(): Unit
  }

  trait AbstractComponentMixin extends ComponentMixin with AbstractComponentSuperCalls { self: com.vaadin.ui.AbstractComponent =>

    override def wrapper = super.wrapper.asInstanceOf[AbstractComponent]

    abstract override def attach(): Unit = wrapper.attach()
    def __super__attach(): Unit = super.attach()

    abstract override def detach(): Unit = wrapper.detach()
    def __super__detach(): Unit = super.detach()
  }
}

trait Component extends Wrapper {
  def p: com.vaadin.ui.Component with ComponentMixin

  // TODO: add methods styleName, addStyleName, removeStyleName?

  def styleName: String = p.getStyleName // TODO: should be Option[String]?
  def styleName_=(styleName: String): Unit = p.setStyleName(styleName)

  lazy val styleNames: mutable.Set[String] = new mutable.Set[String] with Serializable {
    def contains(key: String) = p.getStyleName().split(" ").iterator.contains(key)
    def iterator: Iterator[String] = p.getStyleName().split(" ").iterator
    def +=(elem: String) = { elem.split(" ").foreach(p.addStyleName(_)); this }
    def -=(elem: String) = { p.removeStyleName(elem); this }
  }

  def enabled: Boolean = p.isEnabled
  def enabled_=(enabled: Boolean): Unit = p.setEnabled(enabled)

  def visible: Boolean = p.isVisible
  def visible_=(visible: Boolean): Unit = p.setVisible(visible)

  // TODO parent setter?
  def parent: Option[Component] = wrapperFor[Component](p.getParent())

  def readOnly: Boolean = p.isReadOnly
  def readOnly_=(readOnly: Boolean): Unit = p.setReadOnly(readOnly)

  def caption: Option[String] = Option(p.getCaption)
  def caption_=(caption: Option[String]): Unit = p.setCaption(caption.orNull)
  def caption_=(caption: String): Unit = p.setCaption(caption)

  def icon: Option[Resource] = wrapperFor[Resource](p.getIcon)
  def icon_=(icon: Option[Resource]): Unit = if (icon.isDefined) p.setIcon(icon.get.p) else p.setIcon(null)
  def icon_=(icon: Resource): Unit = p.setIcon(icon.p)

  // TODO: return UI instead of Option[UI]?
  def ui = wrapperFor[UI](p.getUI)

  def locale: Option[Locale] = Option(p.getLocale)

  def id: Option[String] = Option(p.getId)
  def id_=(id: Option[String]): Unit = p.setId(id.orNull)
  def id_=(id: String): Unit = p.setId(id)

  //override def toString = p.toString

  // TODO: ..
}

abstract class AbstractComponent(val p: com.vaadin.ui.AbstractComponent with AbstractComponentMixin) extends Component with Sizeable {

  p.wrapper = this

  def description: Option[String] = Option(p.getDescription)
  def description_=(description: Option[String]): Unit = p.setDescription(description.orNull)
  def description_=(description: String): Unit = p.setDescription(description)

  def immediate: Boolean = p.isImmediate
  def immediate_=(immediate: Boolean): Unit = p.setImmediate(immediate)

  def data_=(data: Any): Unit = p.setData(data)
  def data: Any = p.getData

  def markAsDirty() = p.markAsDirty()

  def attach(): Unit = p.__super__attach()

  def detach(): Unit = p.__super__detach()

}

trait Focusable extends Component {

  def p: com.vaadin.ui.Component.Focusable with ComponentMixin

  def focus(): Unit = p.focus()

  def tabIndex: Int = p.getTabIndex
  def tabIndex_=(tabIndex: Int): Unit = p.setTabIndex(tabIndex)
}

