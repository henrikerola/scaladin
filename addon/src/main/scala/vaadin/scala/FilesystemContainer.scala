package vaadin.scala

import java.io.File
import java.io.FilenameFilter

object FilesystemContainer {

  val PropertyName = com.vaadin.data.util.FilesystemContainer.PROPERTY_NAME

  val PropertySize = com.vaadin.data.util.FilesystemContainer.PROPERTY_SIZE

  val PropertyIcon = com.vaadin.data.util.FilesystemContainer.PROPERTY_ICON

  val PropertyLastModified = com.vaadin.data.util.FilesystemContainer.PROPERTY_LASTMODIFIED

  val FileProperties = com.vaadin.data.util.FilesystemContainer.FILE_PROPERTIES

  def wrapProperty(unwrapped: com.vaadin.data.Property): Property = new FunctionProperty(_ => unwrapped.getValue, (x: Any) => unwrapped.setValue(x))

}

class FilesystemContainer(root: File) extends Container.Hierarchical {

  // p cannot be used as a constructor parameter because of the required file parameter
  val p = new com.vaadin.data.util.FilesystemContainer(root)
  WrapperRegistry.put(this)

  def addRoot(root: File) = p.addRoot(root)

  def getFilter = p.getFilter

  def setFilter(filter: FilenameFilter) = p.setFilter(filter)
  def setFilter(filter: String) = p.setFilter(filter)

  def recursive = p.isRecursive
  def recursive_=(isRecursive: Boolean) = p.setRecursive(isRecursive)

  def wrapItem(unwrapped: com.vaadin.data.Item): Item = new FileItem(unwrapped.asInstanceOf[com.vaadin.data.util.FilesystemContainer#FileItem])

  def wrapProperty(unwrapped: com.vaadin.data.Property): Property = FilesystemContainer.wrapProperty(unwrapped)
}

class FileItem(override val p: com.vaadin.data.util.FilesystemContainer#FileItem) extends Item {
  def lastModified = p.lastModified
  def name = p.getName()
  def icon = p.getIcon()
  def size = p.getSize()

  protected override def wrapProperty(unwrapped: com.vaadin.data.Property): Property = FilesystemContainer.wrapProperty(unwrapped)
}