package vaadin.scala

trait Buffered extends Wrapper {

  override val p: com.vaadin.data.Buffered
  def commit: Unit = p.commit

  def discard: Unit = p.discard

  def modified: Boolean = p.isModified

  def readThrough: Boolean = p.isReadThrough
  def readThrough_=(readThrough: Boolean) = p.setReadThrough(readThrough)

  def writeThrough: Boolean = p.isWriteThrough
  def writeThrough_=(writeThrough: Boolean): Unit = p.setWriteThrough(writeThrough)
}

trait BufferedValitable extends Buffered with Validatable {

}