package vaadin.scala.util

import scala.reflect.ClassTag
import scala.reflect.runtime._

/**
 *
 * @author Henri Kerola / Vaadin
 */
object ReflectUtil {

  /**
   * Returns true of the given class instance is a case class, otherwise false.
   */
  def isCaseClass[T](bean: T)(implicit classTag: ClassTag[T]): Boolean = {
    val mirror = universe.runtimeMirror(bean.getClass.getClassLoader)
    val instanceMirror = mirror.reflect(bean)
    instanceMirror.symbol.isCaseClass
  }

}
