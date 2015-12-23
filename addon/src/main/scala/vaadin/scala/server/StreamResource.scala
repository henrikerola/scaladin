package vaadin.scala.server

import vaadin.scala.server.mixins.ResourceMixin

object StreamResource {

  type StreamSource = com.vaadin.server.StreamResource.StreamSource

  def apply(streamSource: StreamSource, fileName: String): StreamResource = new StreamResource(streamSource, fileName)
}

class StreamResource(override val pResource: com.vaadin.server.StreamResource with ResourceMixin) extends Resource {

  def this(streamSource: StreamResource.StreamSource, fileName: String) {
    this(new com.vaadin.server.StreamResource(streamSource, fileName) with ResourceMixin)
  }

  def filename = pResource.getFilename
  def filename_=(filename: String) = pResource.setFilename(filename)

  def mimeType_=(mimeType: String) = pResource.setMIMEType(mimeType)

  def bufferSize: Int = pResource.getBufferSize
  def bufferSize_=(bufferSize: Int): Unit = pResource.setBufferSize(bufferSize)

  def cacheTime: Long = pResource.getCacheTime
  def cacheTime_=(cacheTime: Long): Unit = pResource.setCacheTime(cacheTime)
}
