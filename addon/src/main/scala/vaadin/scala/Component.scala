package vaadin.scala

import scala.collection.mutable

trait Component extends Wrapper {
  def p: com.vaadin.ui.Component

  // TODO: add methods styleName, addStyleName, removeStyleName?

  def styles(): mutable.Set[String] = new mutable.Set[String] {
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
  def parent: Option[Component] = WrapperRegistry.get[Component](p.getParent())

  def readOnly = p.isReadOnly
  def readOnly_=(readOnly: Boolean) = p.setReadOnly(readOnly)

  def caption = Option(p.getCaption)
  def caption_=(caption: Option[String]) = p.setCaption(caption.getOrElse(null))
  def caption_=(caption: String) = p.setCaption(caption)

  def icon: Option[Resource] = WrapperRegistry.get[Resource](p.getIcon)
  def icon_=(icon: Option[Resource]) = if (icon.isDefined) p.setIcon(icon.get.p) else p.setIcon(null)
  def icon_=(icon: Resource) = if (icon == null) p.setIcon(null) else p.setIcon(icon.p)

  // TODO return wrapped window and use Option?
  def window = p.getWindow()

  //TODO return wrapped application and use Option?
  def application = p.getApplication()

  override def toString = p.toString

  // TODO: ..
}

trait ScaladinWrapper extends com.vaadin.ui.Component with Component {
  def p: this.type = this
  WrapperRegistry.put(this)
}

trait Sizeable extends Component {

  // TODO: use UnitExtent instead of String?
  // TODO: width and height getters
  def width(width: String) = p.setWidth(width)
  def height(height: String) = p.setHeight(height)

  def sizeFull() = p.setSizeFull
  def sizeUndefined() = p.setSizeUndefined
}

trait AbstractComponent extends Component with Sizeable {

  override def p: com.vaadin.ui.AbstractComponent

  def description = Option(p.getDescription)
  def description_=(description: Option[String]) = p.setDescription(description.getOrElse(null))
  def description_=(description: String) = p.setDescription(description)
}

trait AbstractField extends AbstractComponent {

  override def p: com.vaadin.ui.AbstractField

  def focus() = p.focus()

  def property: Option[com.vaadin.data.Property] = Option(p.getPropertyDataSource())
  def property_=(property: Option[com.vaadin.data.Property]) = p.setPropertyDataSource(property.getOrElse(null))
  def property_=(property: com.vaadin.data.Property) = p.setPropertyDataSource(property)

  def value: Option[String] = Option(if (p.getValue() != null) p.getValue().toString else null)
  def value_=(value: Option[String]): Unit = p.setValue(value.getOrElse(null))
  def value_=(value: String): Unit = p.setValue(value)

  def invalidCommitted = p.isInvalidCommitted
  def invalidCommitted_=(invalidCommitted: Boolean) = p.setInvalidCommitted(invalidCommitted)

  def writeThrough = p.isWriteThrough
  def writeThrough_=(writeThrough: Boolean) = p.setWriteThrough(writeThrough)

  def readThrough = p.isReadThrough
  def readThrough_=(readThrough: Boolean) = p.setReadThrough(readThrough)

  //TODO
}