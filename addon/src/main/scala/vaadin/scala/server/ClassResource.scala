package vaadin.scala.server

import vaadin.scala.server.mixins.ResourceMixin
import scala.reflect.ClassTag

object ClassResource {

  def apply[T](resourceName: String)(implicit tag: ClassTag[T]): ClassResource = {
    if (tag.runtimeClass == classOf[Nothing])
      new ClassResource(resourceName)
    else
      new ClassResource(tag.runtimeClass, resourceName)
  }
}

/**
 *
 * @author Henri Kerola / Vaadin
 */
class ClassResource(override val pResource: com.vaadin.server.ClassResource with ResourceMixin)
    extends ConnectorResource {

  def this(resourceName: String) {
    this(new com.vaadin.server.ClassResource(resourceName) with ResourceMixin)
  }

  def this(associatedClass: Class[_], resourceName: String) {
    this(new com.vaadin.server.ClassResource(associatedClass, resourceName) with ResourceMixin)
  }

  def bufferSize: Int = pResource.getBufferSize
  def bufferSize_=(bufferSize: Int): Unit = pResource.setBufferSize(bufferSize)

  def cacheTime: Long = pResource.getCacheTime
  def cacheTime_=(cacheTime: Long): Unit = pResource.setCacheTime(cacheTime)

}
