package vaadin.scala

trait AbstractField extends AbstractComponent with PropertyViewer with Focusable {

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