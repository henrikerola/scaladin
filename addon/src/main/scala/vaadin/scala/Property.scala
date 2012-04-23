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

  def value() = p.getValue()
  def value_=(value: Any) = p.setValue(value)
  def getType = p.getType()
  def readOnly() = p.isReadOnly()
  def readOnly_=(readOnly: Boolean) = p.setReadOnly(readOnly)
  override def toString = p.toString
}

/**
 * Basic property wrapper, wraps any instance of com.vaadin.data.Property
 */
class BasicProperty(override val p: com.vaadin.data.Property) extends Property

class ObjectProperty[T](value: T) extends Property with Wrapper {
  val p = new com.vaadin.data.util.ObjectProperty[T](value)
}

class FunctionProperty[T](getter: () => T, setter: T => Unit = null) extends com.vaadin.data.Property {
  def getValue = getter().asInstanceOf[AnyRef]

  def setValue(value: Any) = {
    setter(value.asInstanceOf[T])
  }

  def getType = getter.getClass //dirty tricks

  def isReadOnly = setter != null

  def setReadOnly(readOnly: Boolean): Unit = {
    //NOOP
  }
}

class MethodProperty[T](override val p: com.vaadin.data.util.MethodProperty[T]) extends Property {

}