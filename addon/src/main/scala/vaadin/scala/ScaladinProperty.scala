package vaadin.scala

import vaadin.scala.mixins.DelegatingPropertyMixin
import scala.reflect.runtime.universe.MethodMirror

package mixins {
  trait DelegatingPropertyMixin[T] extends ScaladinMixin {
    self: com.vaadin.data.Property[T] =>

    override def wrapper = super.wrapper.asInstanceOf[ScaladinProperty[T]]

    def getValue: T = {
      wrapper.value.getOrElse(null).asInstanceOf[T]
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
  val getterMirror: MethodMirror,
  val setterMirror: Option[MethodMirror])
    extends Property[T] {

  val p = new com.vaadin.data.Property[T] with DelegatingPropertyMixin[T]
  p.wrapper = this

  override def value: Option[Any] = Option(getterMirror())

  override def value_=(value: Option[Any]) { this.value = value.orNull }

  override def value_=(value: Any) { setterMirror foreach { setter => setter(value) } }

  override def getType: Class[_ <: T] = propertyType

  private var _readOnly = false

  override def readOnly: Boolean = _readOnly || setterMirror.isEmpty

  override def readOnly_=(ro: Boolean) { _readOnly = ro }
}

class ImmutableScaladinProperty[T](
    val propertyType: Class[_ <: T],
    val instance: T,
    val getterMirror: MethodMirror) extends Property[T] {

  val p = new com.vaadin.data.Property[T] with DelegatingPropertyMixin[T]
  p.wrapper = this

}
