package vaadin.scala

abstract class AbstractField(override val p: com.vaadin.ui.AbstractField) extends AbstractComponent(p) with PropertyViewer with Focusable {

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