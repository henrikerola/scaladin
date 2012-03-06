package vaadin.scala

import scala.collection.mutable

trait Component {
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
  def parent: Option[Component] = if (p.getParent() == null) None else Option(WrapperRegistry.get(p.getParent()))

  def readOnly = p.isReadOnly
  def readOnly_=(readOnly: Boolean) = p.setReadOnly(readOnly)

  def caption = Option(p.getCaption)
  def caption_=(caption: Option[String]) = p.setCaption(caption.getOrElse(null))
  def caption_=(caption: String) = p.setCaption(caption)

  override def toString = p.toString

  // TODO: ..
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

}