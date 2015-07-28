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
}
