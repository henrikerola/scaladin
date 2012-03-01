package vaadin.scala

trait Component[C <: com.vaadin.ui.Component] {
  def p: C

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

trait Sizeable[C <: com.vaadin.ui.Component] extends Component[C] {

  //def width(width: UnitExtent) = component.setWidth(width)

  def sizeFull() = p.setSizeFull
  def sizeUndefined() = p.setSizeUndefined
}

trait AbstractComponent[C <: com.vaadin.ui.AbstractComponent] extends Component[C] with Sizeable[C] {
  def description = Option(p.getDescription)
  def description_=(description: Option[String]) = p.setDescription(description.getOrElse(null))
  def description_=(description: String) = p.setDescription(description)
}

trait AbstractField[C <: com.vaadin.ui.AbstractField] extends AbstractComponent[C] {

}