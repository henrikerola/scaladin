package vaadin.scala

import scala.reflect.runtime.universe._
import scala.reflect.Manifest
import com.vaadin.data.util

/**
 * @author Henri Kerola / Vaadin
 */
class ScaladinItem[T: Manifest](bean: T)
    extends PropertysetItem(new util.PropertysetItem) {

  getPropertyDescriptors(bean) foreach { pd =>
    addItemProperty(pd.name, pd.createProperty(pd))
  }

  def getPropertyDescriptors(b: T): Iterable[ScaladinPropertyDescriptor[_]] = {
    lazy val rm = runtimeMirror(b.getClass.getClassLoader)
    val getters = typeOf[T].members filter { m =>
      m.typeSignature match {
        case NullaryMethodType(tpe) if !m.isSynthetic => true
        case _ => false
      }
    }
    getters.map { m =>
      val clazz = rm.runtimeClass(m.asMethod.returnType.typeSymbol.asClass)
      val symbol = typeOf[T].declaration(m.name).asTerm
      val mirror = rm.reflect(b).reflectField(symbol)

      new ScaladinPropertyDescriptor[T](m.name.decoded, clazz, mirror)
    }
  }
}
