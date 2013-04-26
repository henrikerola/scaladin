package vaadin.scala

import com.vaadin.data.util.VaadinPropertyDescriptor
import vaadin.scala.mixins.{ DelegatingVaadinPropertyDescriptor }

package mixins {

  trait DelegatingVaadinPropertyDescriptor[T] extends ScaladinMixin {
    self: VaadinPropertyDescriptor[T] =>

    override def wrapper = super.wrapper.asInstanceOf[ScaladinPropertyDescriptor[T]]

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
  val mirror: scala.reflect.runtime.universe.type#FieldMirror)
    extends Wrapper {

  val p = new VaadinPropertyDescriptor[T] with DelegatingVaadinPropertyDescriptor[T]
  p.wrapper = this

  // TODO should be bean: T
  def createProperty(bean: Any): Property[_] = {
    new ScaladinProperty[T](propertyType.asInstanceOf[Class[_ <: T]], bean.asInstanceOf[T], mirror)
  }
}
