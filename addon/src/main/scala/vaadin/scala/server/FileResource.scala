package vaadin.scala.server

import java.io.File
import vaadin.scala.server.mixins.ResourceMixin

object FileResource {
  def apply(sourceFile: File): FileResource = new FileResource(sourceFile)
}

class FileResource(override val pResource: com.vaadin.server.FileResource with ResourceMixin)
    extends ConnectorResource {

  def this(sourceFile: File) {
    this(new com.vaadin.server.FileResource(sourceFile) with ResourceMixin)
  }

  def sourceFile = pResource.getSourceFile

  def bufferSize: Int = pResource.getBufferSize
  def bufferSize_=(bufferSize: Int): Unit = pResource.setBufferSize(bufferSize)

  def cacheTime: Long = pResource.getCacheTime
  def cacheTime_=(cacheTime: Long): Unit = pResource.setCacheTime(cacheTime)
}
