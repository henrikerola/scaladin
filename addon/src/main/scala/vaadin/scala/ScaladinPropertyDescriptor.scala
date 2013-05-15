package vaadin.scala

import com.vaadin.data.util.VaadinPropertyDescriptor
import vaadin.scala.mixins.{ DelegatingVaadinPropertyDescriptor }
import scala.reflect.runtime.universe.MethodMirror

package mixins {

  trait DelegatingVaadinPropertyDescriptor[T] extends TypedScaladinMixin[ScaladinPropertyDescriptor[T]] {
    self: VaadinPropertyDescriptor[T] =>

    def getName: String = wrapper.name

    def getPropertyType: Class[_] = wrapper.propertyType

    def createProperty(bean: T): com.vaadin.data.Property[_] = wrapper.createProperty(bean).p
  }
}

/**
 *
 * @author Henri Kerola / Vaadin
 */
class ScaladinPropertyDescriptor[T](
  val name: String,
  val propertyType: Class[_],
  val getterMirror: MethodMirror,
  val setterMirror: Option[MethodMirror])
    extends Wrapper {

  val p = new VaadinPropertyDescriptor[T] with DelegatingVaadinPropertyDescriptor[T]
  p.wrapper = this

  def createProperty[T](bean: T): Property[_] = {
    new ScaladinProperty[T](propertyType.asInstanceOf[Class[_ <: T]], bean, getterMirror, setterMirror)
  }
}
