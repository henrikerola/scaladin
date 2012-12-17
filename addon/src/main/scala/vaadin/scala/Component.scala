package vaadin.scala

import scala.collection.mutable
import vaadin.scala.mixins.ComponentMixin
import vaadin.scala.mixins.AbstractComponentMixin
import vaadin.scala.mixins.ScaladinMixin

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

  def styleName = p.getStyleName
  def styleName_=(styleName: String) = p.setStyleName(styleName)

  val styleNames = new mutable.Set[String] {
    def contains(key: String) = p.getStyleName().split(" ").iterator.contains(key)
    def iterator: Iterator[String] = p.getStyleName().split(" ").iterator
    def +=(elem: String) = { elem.split(" ").foreach(p.addStyleName(_)); this }
    def -=(elem: String) = { p.removeStyleName(elem); this }
  }

  def enabled = p.isEnabled
  def enabled_=(enabled: Boolean) = p.setEnabled(enabled)

  def visible = p.isVisible
  def visible_=(visible: Boolean) = p.setVisible(visible)

  // TODO parent setter?
  def parent: Option[Component] = wrapperFor[Component](p.getParent())

  def readOnly = p.isReadOnly
  def readOnly_=(readOnly: Boolean) = p.setReadOnly(readOnly)

  def caption = Option(p.getCaption)
  def caption_=(caption: Option[String]) = p.setCaption(caption.orNull)
  def caption_=(caption: String) = p.setCaption(caption)

  def icon: Option[Resource] = wrapperFor[Resource](p.getIcon)
  def icon_=(icon: Option[Resource]) = if (icon.isDefined) p.setIcon(icon.get.p) else p.setIcon(null)
  def icon_=(icon: Resource) = p.setIcon(icon.p)

  // TODO: return UI instead of Option[UI]?
  def ui = wrapperFor[UI](p.getUI)

  def locale = Option(p.getLocale)

  def id = Option(p.getId)
  def id_=(id: Option[String]) = p.setId(id.orNull)
  def id_=(id: String) = p.setId(id)

  //override def toString = p.toString

  // TODO: ..
}

abstract class AbstractComponent(val p: com.vaadin.ui.AbstractComponent with AbstractComponentMixin) extends Component with Sizeable {

  p.wrapper = this

  def description = Option(p.getDescription)
  def description_=(description: Option[String]) = p.setDescription(description.orNull)
  def description_=(description: String) = p.setDescription(description)

  def immediate = p.isImmediate();
  def immediate_=(immediate: Boolean) = p.setImmediate(immediate);

  def data_=(data: Any) = p.setData(data)
  def data = p.getData

  def markAsDirty() = p.markAsDirty();

  def attach(): Unit = p.__super__attach()

  def detach(): Unit = p.__super__detach()

}

trait Focusable extends Component {

  def p: com.vaadin.ui.Component.Focusable with ComponentMixin

  def focus(): Unit = p.focus()

  def tabIndex: Int = p.getTabIndex()
  def tabIndex_=(tabIndex: Int): Unit = p.setTabIndex(tabIndex)
}

