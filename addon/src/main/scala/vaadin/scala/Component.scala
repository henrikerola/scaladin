package vaadin.scala

import scala.collection.mutable

trait Component extends Wrapper {
  def p: com.vaadin.ui.Component
  def wr: WrapperRegistry

  // TODO: add methods styleName, addStyleName, removeStyleName?

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
  def parent: Option[Component] = wr.get[Component](p.getParent())

  def readOnly = p.isReadOnly
  def readOnly_=(readOnly: Boolean) = p.setReadOnly(readOnly)

  def caption = Option(p.getCaption)
  def caption_=(caption: Option[String]) = p.setCaption(caption.getOrElse(null))
  def caption_=(caption: String) = p.setCaption(caption)

  def icon: Option[Resource] = wr.get[Resource](p.getIcon)
  def icon_=(icon: Option[Resource]) = if (icon.isDefined) p.setIcon(icon.get.p) else p.setIcon(null)
  def icon_=(icon: Resource) = if (icon == null) p.setIcon(null) else p.setIcon(icon.p)

  // TODO return wrapped window and use Option?
  def window = p.getWindow()

  //TODO return wrapped application and use Option?
  def application = p.getApplication()

  //override def toString = p.toString

  // TODO: ..
}

/**
 * Wrapper trait for existing Vaadin components.
 */
trait ScaladinWrapper extends com.vaadin.ui.Component with Component {
  var _wrapper: Option[WrapperRegistry] = None

  def p: this.type = this

  def init(implicit wrapper: WrapperRegistry): Unit = _wrapper = Some(wrapper)

  def wr: WrapperRegistry = _wrapper match {
    case Some(wr: WrapperRegistry) => wr
    case _                         => throw new IllegalStateException("Wrappers must be inited before use")
  }
}

trait Sizeable extends Component {

  def width: Option[Measure] = if (p.getWidth < 0) None else Option(new Measure(p.getWidth, Units(p.getWidthUnits)))
  def width_=(width: Option[Measure]) = p.setWidth(if (width.isDefined) width.get.toString else null);
  def width_=(width: Measure) = p.setWidth(if (width != null) width.toString else null)

  def height: Option[Measure] = if (p.getHeight() < 0) None else Option(new Measure(p.getHeight(), Units(p.getHeightUnits)))
  def height_=(height: Option[Measure]) = p.setHeight(if (height.isDefined) height.get.toString else null)
  def height_=(height: Measure) = p.setHeight(if (height != null) height.toString else null)

  def sizeFull() = p.setSizeFull
  def sizeUndefined() = p.setSizeUndefined
}

abstract class AbstractComponent(implicit wrapper: WrapperRegistry) extends Component with Sizeable {

  override val wr = wrapper
  override def p: com.vaadin.ui.AbstractComponent

  def description = Option(p.getDescription)
  def description_=(description: Option[String]) = p.setDescription(description.getOrElse(null))
  def description_=(description: String) = p.setDescription(description)

  def immediate = p.isImmediate();
  def immediate_=(immediate: Boolean) = p.setImmediate(immediate);

  def data_=(data: Any) = p.setData(data)
  def data = p.getData
}

trait Focusable extends Component {

  def p: com.vaadin.ui.Component.Focusable

  def focus() = p.focus()

  def tabIndex = p.getTabIndex()
  def tabIndex_=(tabIndex: Int) = p.setTabIndex(tabIndex)
}

abstract class AbstractField(implicit wrapper: WrapperRegistry) extends AbstractComponent with PropertyViewer with Focusable {

  override def p: com.vaadin.ui.AbstractField

  def value: Option[Any] = Option(p.getValue())
  def value_=(value: Option[Any]): Unit = p.setValue(value.getOrElse(null))
  def value_=(value: Any): Unit = p.setValue(value)

  def invalidCommitted = p.isInvalidCommitted
  def invalidCommitted_=(invalidCommitted: Boolean) = p.setInvalidCommitted(invalidCommitted)

  def writeThrough = p.isWriteThrough
  def writeThrough_=(writeThrough: Boolean) = p.setWriteThrough(writeThrough)

  def readThrough = p.isReadThrough
  def readThrough_=(readThrough: Boolean) = p.setReadThrough(readThrough)

  //TODO
}

trait PropertyViewer {

  def p: com.vaadin.data.Property.Viewer

  def property: Option[com.vaadin.data.Property] = Option(p.getPropertyDataSource())
  def property_=(property: Option[com.vaadin.data.Property]) = p.setPropertyDataSource(property.getOrElse(null))
  def property_=(property: com.vaadin.data.Property) = p.setPropertyDataSource(property)
}