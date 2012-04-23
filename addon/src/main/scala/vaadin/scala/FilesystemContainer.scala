package vaadin.scala

import java.io.File
import java.io.FilenameFilter

object FilesystemContainer {

  val PropertyName = com.vaadin.data.util.FilesystemContainer.PROPERTY_NAME

  val PropertySize = com.vaadin.data.util.FilesystemContainer.PROPERTY_SIZE

  val PropertyIcon = com.vaadin.data.util.FilesystemContainer.PROPERTY_ICON

  val PropertyLastModified = com.vaadin.data.util.FilesystemContainer.PROPERTY_LASTMODIFIED

  val FileProperties = com.vaadin.data.util.FilesystemContainer.FILE_PROPERTIES
}

class FilesystemContainer(root: File) extends Container.Hierarchical with Wrapper {

  // p cannot be used as a constructor parameter because of the required file parameter
  val p = new com.vaadin.data.util.FilesystemContainer(root)
  WrapperRegistry.put(this)

  def addRoot(root: File) = p.addRoot(root)

  def getFilter = p.getFilter

  def setFilter(filter: FilenameFilter) = p.setFilter(filter)
  def setFilter(filter: String) = p.setFilter(filter)

  def recursive = p.isRecursive
  def recursive_=(isRecursive: Boolean) = p.setRecursive(isRecursive)

  def wrapItem(unwrapped: com.vaadin.data.util.FilesystemContainer#FileItem): FileItem = new FileItem(unwrapped)
  def wrapProperty(unwrapped: com.vaadin.data.util.MethodProperty[Any]) = new MethodProperty[Any](unwrapped)
}

class FileItem(override val p: com.vaadin.data.util.FilesystemContainer#FileItem) extends Item with Wrapper {
  def lastModified = p.lastModified
  def name = p.getName()
  def icon = p.getIcon()
  def size = p.getSize()

  def wrapProperty(unwrapped: com.vaadin.data.util.MethodProperty[Any]): Property = new MethodProperty[Any](unwrapped)
}