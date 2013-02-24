package vaadin.scala

import vaadin.scala.mixins.CustomFieldMixin
import com.vaadin.ui.{ CustomField => VaadinCustomField }

package mixins {

  trait CustomFieldSuperCalls {
    def getContent: com.vaadin.ui.Component
  }

  trait CustomFieldMixin[T] extends AbstractFieldMixin[T] with HasComponentsMixin with CustomFieldSuperCalls {

    self: com.vaadin.ui.CustomField[T] =>

    override def wrapper = super.wrapper.asInstanceOf[CustomField[T]]

    override def getType: Class[_ <: T] = wrapper.getType

    abstract override def getContent = super.getContent

    override def initContent() = wrapper.initContent.p
  }
}

/**
 * @see com.vaadin.ui.CustomField
 * @author Henri Kerola / Vaadin
 */
abstract class CustomField[T: Manifest](
  override val p: VaadinCustomField[T] with CustomFieldMixin[T] = new VaadinCustomField[T] with CustomFieldMixin[T])
    extends AbstractField[T](p) with HasComponents {

  protected def content: Component = wrapperFor[Component](p.getContent).get

  override def getType: Class[_ <: T] = manifest[T].runtimeClass.asInstanceOf[Class[_ <: T]]

  // should be protected
  def initContent(): Component
}
