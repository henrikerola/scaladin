package vaadin.scala

import vaadin.scala.mixins.DelegatingPropertyMixin

package mixins {
  trait DelegatingPropertyMixin[T] extends ScaladinMixin {
    self: com.vaadin.data.Property[T] =>

    override def wrapper = super.wrapper.asInstanceOf[ScaladinProperty[T]]

    def getValue: T = {
      wrapper.value.orNull.asInstanceOf[T]
    }

    def setValue(newValue: T) { wrapper.value = newValue }

    def getType: Class[_ <: T] = wrapper.getType

    def isReadOnly: Boolean = wrapper.readOnly

    def setReadOnly(readOnly: Boolean) { wrapper.readOnly = readOnly }
  }
}

/**
 *
 * @author Henri Kerola / Vaadin
 */
class ScaladinProperty[T](
  val propertyType: Class[_ <: T],
  val instance: T,
  val mirror: scala.reflect.runtime.universe.type#FieldMirror)
    extends Property[T] {

  val p = new com.vaadin.data.Property[T] with DelegatingPropertyMixin[T]
  p.wrapper = this

  override def value: Option[Any] = Option(mirror.get)

  override def value_=(value: Option[Any]) { this.value = value.orNull }

  override def value_=(value: Any) { mirror.set(value) }

  override def getType: Class[_ <: T] = propertyType

  override def readOnly: Boolean = false // TODO

  override def readOnly_=(ro: Boolean) {
    // TODO
  }
}
