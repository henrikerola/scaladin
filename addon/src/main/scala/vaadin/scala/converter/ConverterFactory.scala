package vaadin.scala.converter

import scala.reflect.ClassTag
import vaadin.scala.InterfaceWrapper
import vaadin.scala.converter.mixins.ConverterFactoryMixin
import vaadin.scala.internal.WrapperUtil
import com.vaadin.data.util.converter.{ Converter => VaadinConverter }

import vaadin.scala.mixins.ScaladinInterfaceMixin

  /**
 * @author Henri Kerola / Vaadin
 */
trait ConverterFactory extends InterfaceWrapper {

  val pConverterFactory = new com.vaadin.data.util.converter.ConverterFactory with ConverterFactoryMixin
  pConverterFactory.wrapper = this

  def createConverter[Presentation, Model](implicit presentationType: ClassTag[Presentation],
                                           modelType: ClassTag[Model]): Option[Converter[Presentation, Model]]
}

trait ConverterFactoryMixin extends ScaladinInterfaceMixin {
    self: com.vaadin.data.util.converter.ConverterFactory =>

    override def wrapper = super.wrapper.asInstanceOf[ConverterFactory]

    override def createConverter[Presentation, Model](presentationType: Class[Presentation],
                                                      modelType: Class[Model]): VaadinConverter[Presentation, Model] = {
      WrapperUtil.peerFor(wrapper.createConverter[Presentation, Model](ClassTag(presentationType), ClassTag(modelType)))
    }
  }
