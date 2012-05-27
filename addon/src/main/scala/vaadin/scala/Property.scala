package vaadin.scala

object Property {
  def apply[T](value: T): Property = new ObjectProperty[T](value)

  def unapply(property: Property): Option[Any] = {
    if (property != null) property.value
    else None
  }
}

trait Property extends Wrapper {
  def p: com.vaadin.data.Property

  def value: Option[Any] = Option(p.getValue())
  def value_=(value: Option[Any]): Unit = value_=(value.getOrElse(null))
  def value_=(value: Any): Unit = p.setValue(value)
  def getType: Class[_] = p.getType()
  def readOnly: Boolean = p.isReadOnly()
  def readOnly_=(ro: Boolean): Unit = p.setReadOnly(ro)
  override def toString: String = p.toString
}

trait PropertyViewer extends Wrapper {

  def p: com.vaadin.data.Property.Viewer

  def property: Option[Property] = p.getPropertyDataSource() match {
    case p: com.vaadin.data.Property => Some(new BasicProperty(p))
    case null => None
  }

  def property_=(property: Option[Property]) = property match {
    case Some(prop) => p.setPropertyDataSource(prop.p)
    case None => p.setPropertyDataSource(null)
  }

  def property_=(property: Property) = p.setPropertyDataSource(property.p)
}

trait PropertyEditor extends PropertyViewer {
  def p: com.vaadin.data.Property.Editor
}

case class ValueChangeEvent(property: Property)

/**
 * Basic property wrapper, wraps any instance of com.vaadin.data.Property
 */
class BasicProperty(override val p: com.vaadin.data.Property) extends Property

class ObjectProperty[T](value: T) extends Property {
  val p = new com.vaadin.data.util.ObjectProperty[T](value)
}

class VaadinPropertyDelegator(scaladinProperty: Property) extends com.vaadin.data.Property {
  def getValue = scaladinProperty.value.get.asInstanceOf[AnyRef]
  def setValue(v: Any) = scaladinProperty.value = v
  def getType = scaladinProperty.getType
  def isReadOnly = scaladinProperty.readOnly
  def setReadOnly(ro: Boolean) = scaladinProperty.readOnly = ro
}

class FunctionProperty[T](getter: Unit => T, setter: T => Unit = null)(implicit m: Manifest[T]) extends Property {
  //delegate
  val p = new VaadinPropertyDelegator(this)

  override def value: Option[Any] = Option(getter())

  override def value_=(value: Any) = setter(value.asInstanceOf[T])

  override def getType: Class[T] = m.erasure.asInstanceOf[Class[T]]

  override def readOnly: Boolean = setter != null

  override def readOnly_=(ro: Boolean): Unit = () // NOOP

  override def toString = "Value: " + value
}