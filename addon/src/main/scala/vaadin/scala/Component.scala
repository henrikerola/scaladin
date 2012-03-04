package vaadin.scala

trait Component {
  def p: com.vaadin.ui.Component

  // TODO: style name related methods...

  def enabled = p.isEnabled
  def enabled_=(enabled: Boolean) = p.setEnabled(enabled)

  def visible = p.isVisible
  def visible_=(visible: Boolean) = p.setVisible(visible)

  // TODO: parent

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