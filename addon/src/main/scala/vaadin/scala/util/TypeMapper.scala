package vaadin.scala.util

/**
 * Maps primitive Scala types to corresponding non-primitive Java types.
 *
 * @author Henri Kerola / Vaadin
 */
object TypeMapper {

  private val map = Map[Class[_], Class[_]](
    classOf[Boolean] -> classOf[java.lang.Boolean],
    classOf[Byte] -> classOf[java.lang.Byte],
    classOf[Short] -> classOf[java.lang.Short],
    classOf[Char] -> classOf[java.lang.Character],
    classOf[Int] -> classOf[java.lang.Integer],
    classOf[Long] -> classOf[java.lang.Long],
    classOf[Float] -> classOf[java.lang.Float],
    classOf[Double] -> classOf[java.lang.Double]
  )

  def toJavaType(clazz: Class[_]): Class[_] = map.getOrElse(clazz, clazz)

}
