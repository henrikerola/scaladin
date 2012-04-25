package vaadin.scala

object Property {
  def apply[T](value: T): Property = new ObjectProperty[T](value)

  def unapply(property: Property): Option[Any] = {
    if (property != null) Some(property.value)
    else None
  }
}

trait Property extends Wrapper {
  def p: com.vaadin.data.Property

  def value(): Any = p.getValue()
  def value_=(value: Any): Unit = p.setValue(value)
  def getType: Class[_] = p.getType()
  def readOnly(): Boolean = p.isReadOnly()
  def readOnly_=(readOnly: Boolean): Unit = p.setReadOnly(readOnly)
  override def toString: String = p.toString
}

/**
 * Basic property wrapper, wraps any instance of com.vaadin.data.Property
 */
class BasicProperty(override val p: com.vaadin.data.Property) extends Property

class ObjectProperty[T](value: T) extends Property {
  val p = new com.vaadin.data.util.ObjectProperty[T](value)
}

class FunctionProperty[T](getter: Unit => T, setter: T => Unit = null)(implicit m: Manifest[T]) extends Property {
  //delegate
  val p = new com.vaadin.data.Property() {
    def getValue = value.asInstanceOf[AnyRef]
    def setValue(v: Any) = value_=(v)
    def getType = FunctionProperty.this.getType
    def isReadOnly = readOnly
    def setReadOnly(ro: Boolean) = readOnly_=(ro)
  }

  override def value: Any = getter()

  override def value_=(value: Any) = setter(value.asInstanceOf[T])

  override def getType: Class[T] = m.erasure.asInstanceOf[Class[T]]

  override def readOnly = setter != null

  override def readOnly_=(readOnly: Boolean): Unit = () // NOOP

  override def toString = "Value: " + value
}