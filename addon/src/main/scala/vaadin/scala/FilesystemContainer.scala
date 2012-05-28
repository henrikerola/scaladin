package vaadin.scala

import java.io.File
import java.io.FilenameFilter
import java.util.Date
import vaadin.scala.mixins.ContainerHierarchicalMixin
import vaadin.scala.mixins.FilesystemContainerMixin

package mixins {
  trait FilesystemContainerMixin extends ContainerHierarchicalMixin
}

object FilesystemContainer {

  val PropertyName: String = com.vaadin.data.util.FilesystemContainer.PROPERTY_NAME

  val PropertySize: String = com.vaadin.data.util.FilesystemContainer.PROPERTY_SIZE

  val PropertyIcon: String = com.vaadin.data.util.FilesystemContainer.PROPERTY_ICON

  val PropertyLastModified: String = com.vaadin.data.util.FilesystemContainer.PROPERTY_LASTMODIFIED

  import scala.collection.JavaConverters._
  val FileProperties: Iterable[String] = com.vaadin.data.util.FilesystemContainer.FILE_PROPERTIES.asScala

  def wrapProperty(unwrapped: com.vaadin.data.Property): Property = new FileProperty(unwrapped)
}

class FilesystemContainer(override val p: com.vaadin.data.util.FilesystemContainer with FilesystemContainerMixin) extends ContainerHierarchical {

  def this(root: File, extension: String = null, filenameFilter: FilenameFilter = null, recursiveFromRoot: Boolean = true) {
    this(new com.vaadin.data.util.FilesystemContainer(root) with FilesystemContainerMixin)
    recursive = recursiveFromRoot
    if (extension != null)
      filter = extension
    else
      filter = filenameFilter
  }

  def addRoot(root: File): Unit = p.addRoot(root)

  def filter: Option[FilenameFilter] = Option(p.getFilter)

  def filter_=(filter: FilenameFilter): Unit = p.setFilter(filter)

  def filter_=(filter: Option[FilenameFilter]): Unit = filter match {
    case Some(f: FilenameFilter) => p.setFilter(f)
    case None => p.setFilter(null: FilenameFilter)
  }

  def filter_=(filter: String): Unit = { require(filter != null); p.setFilter(filter) }

  def recursive: Boolean = p.isRecursive
  def recursive_=(isRecursive: Boolean): Unit = p.setRecursive(isRecursive)

  def wrapItem(unwrapped: com.vaadin.data.Item): Item = new FileItem(unwrapped.asInstanceOf[com.vaadin.data.util.FilesystemContainer#FileItem])

  def wrapProperty(unwrapped: com.vaadin.data.Property): Property = FilesystemContainer.wrapProperty(unwrapped)
}

class FileItem(override val p: com.vaadin.data.util.FilesystemContainer#FileItem) extends Item {
  def lastModified: Date = p.lastModified
  def name: String = p.getName()
  // FIXME
  //def icon: Resource = new ThemeResource(p.getIcon().asInstanceOf[com.vaadin.terminal.ThemeResource])
  def size: Long = p.getSize()

  protected override def wrapProperty(unwrapped: com.vaadin.data.Property): Property = FilesystemContainer.wrapProperty(unwrapped)
}

class FileProperty(override val p: com.vaadin.data.Property) extends BasicProperty(p) {

  override def value = super.value match {
    case Some(value) => value match {
      case themeResource: com.vaadin.terminal.ThemeResource => Some(new ThemeResource(themeResource.getResourceId))
      case other => Some(other)
    }
    case None => None
  }
}