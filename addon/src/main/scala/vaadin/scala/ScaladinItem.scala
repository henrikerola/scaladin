package vaadin.scala

import scala.reflect.runtime.universe._
import com.vaadin.data.util.{ PropertysetItem => VaadinPropertysetItem }
import scala.reflect.ClassTag

object ScaladinItem {

  protected def getPropertyDescriptors[T: TypeTag](b: T): Iterable[ScaladinPropertyDescriptor[T]] = {
    implicit val classTag = ClassTag[T](b.getClass)
    lazy val rm = runtimeMirror(b.getClass.getClassLoader)
    val getters = typeOf[T].members filter { m =>
      m.typeSignature match {
        case NullaryMethodType(tpe) if !m.isSynthetic => true
        case _ => false
      }
    }

    getters.map { m =>
      val clazz = rm.runtimeClass(m.asMethod.returnType.typeSymbol.asClass)
      val getterSymbol = typeOf[T].member(m.name).asMethod
      val getterMirror = rm.reflect(b).reflectMethod(getterSymbol)
      val setterSymbol = getterSymbol.setter match {
        case ms: MethodSymbol => Some(ms)
        case _ => {
          typeOf[T].member(newTermName(m.name.encoded + "_$eq")) match {
            case ms: MethodSymbol => Some(ms)
            case _ => None
          }
        }
      }
      val setterMirror = setterSymbol map { s => rm.reflect(b).reflectMethod(s.asMethod) }

      new ScaladinPropertyDescriptor[T](m.name.decoded, clazz, getterMirror, setterMirror)
    }
  }

  def apply[T: TypeTag](bean: T): ScaladinItem[T] = {
    new ScaladinItem(bean, getPropertyDescriptors(bean))
  }

  def apply[T: TypeTag](bean: T, propertyIds: Iterable[String]): ScaladinItem[T] = {
    val propertyDescriptorsMap = getPropertyDescriptors(bean) map { pd => (pd.name, pd) } toMap
    val propertyDescriptors = propertyIds flatMap { propertyDescriptorsMap.get(_) }
    new ScaladinItem(bean, propertyDescriptors)
  }
}

/**
 * @author Henri Kerola / Vaadin
 */
class ScaladinItem[T: TypeTag](bean: T, propertyDescriptors: Iterable[ScaladinPropertyDescriptor[T]])
    extends PropertysetItem(new VaadinPropertysetItem) {

  propertyDescriptors foreach { pd =>
    addItemProperty(pd.name, pd.createProperty(bean))
  }
}
