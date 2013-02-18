package vaadin.scala

import vaadin.scala.mixins.ScaladinMixin
import vaadin.scala.mixins.ContainerMixin
import vaadin.scala.mixins.ContainerIndexedMixin
import vaadin.scala.mixins.ContainerHierarchicalMixin
import vaadin.scala.mixins.ContainerOrderedMixin
import vaadin.scala.mixins.ContainerViewerMixin
import vaadin.scala.mixins.ContainerSortableMixin

package mixins {
  trait ContainerMixin extends ScaladinMixin
  trait ContainerHierarchicalMixin extends ContainerMixin
  trait ContainerOrderedMixin extends ContainerMixin
  trait ContainerViewerMixin extends ScaladinMixin
  trait ContainerSortableMixin extends ContainerOrderedMixin
  trait ContainerIndexedMixin extends ContainerOrderedMixin
}

//Base Container trait is outside the companion object so extending classes can have nicer syntax
trait Container extends Wrapper {

  import scala.collection.JavaConverters._

  def p: com.vaadin.data.Container with ContainerMixin

  def item(id: Any): Option[Item] = optionalWrapItem(p.getItem(id))

  def itemIds: Iterable[Any] = p.getItemIds.asScala

  def removeAllItems(): Boolean = p.removeAllItems

  def addContainerProperty(propertyId: Any, propertyType: Class[_], defaultValue: Option[Any] = None): Boolean = p.addContainerProperty(propertyId, propertyType, defaultValue.orNull)

  def removeContainerProperty(propertyId: Any): Boolean = p.removeContainerProperty(propertyId)

  def removeItem(itemId: Any): Boolean = p.removeItem(itemId)

  def addItem(): Any = p.addItem()
  def addItemOption(): Option[Any] = Option(p.addItem())

  def addItem(itemId: Any): Option[Item] = optionalWrapItem(p.addItem(itemId))

  def containsId(itemId: Any): Boolean = p.containsId(itemId)

  def size: Int = p.size()

  def property(itemId: Any, propertyId: Any): Option[Property[_]] = optionalWrapProperty(p.getContainerProperty(itemId, propertyId))

  def propertyIds(): Iterable[Any] = p.getContainerPropertyIds().asScala

  def getType(propertyId: Any): Class[_] = p.getType(propertyId)

  def wrapItem(unwrapped: com.vaadin.data.Item): Item

  //override if needed
  protected def wrapProperty(unwrapped: com.vaadin.data.Property[_]): Property[_] = new BasicProperty(unwrapped)

  protected def optionalWrapItem(item: com.vaadin.data.Item): Option[Item] = item match {
    case i: com.vaadin.data.Item => Some(wrapItem(i))
    case _ => None
  }

  protected def optionalWrapProperty(property: com.vaadin.data.Property[_]): Option[Property[_]] = property match {
    case p: com.vaadin.data.Property[_] => Some(wrapProperty(p))
    case _ => None
  }
}

object Container {
  def apply(items: Tuple2[Any, Seq[Tuple2[Any, Any]]]*): Container = fill(new IndexedContainer, items: _*)

  def filterable(items: Tuple2[Any, Seq[Tuple2[Any, Any]]]*): FilterableContainer = fill(new IndexedContainer with FilterableContainer, items: _*)

  def fill[C <: Container](container: C, items: Tuple2[Any, Seq[Tuple2[Any, Any]]]*): C = {
    for (item <- items) {
      container.addItem(item._1) match {

        case Some(containerItem: Item) => {
          for (property <- item._2) {
            container.addContainerProperty(property._1, property._2.getClass, None)
            containerItem.property(property._1) match {
              case Some(p: Property[_]) => p.value = (property._2)
              case _ =>
            }
          }
        }

        case None =>
      }
    }
    container
  }

  trait Hierarchical extends Container {

    def p: com.vaadin.data.Container.Hierarchical with ContainerHierarchicalMixin

    import scala.collection.JavaConverters._

    def children(itemId: Any): Iterable[Any] = p.getChildren(itemId) match {
      case null => List.empty
      case result => result.asScala
    }

    def parent(itemId: Any): Any = p.getParent(itemId)
    def setParent(itemToParent: (Any, Any)): Unit = p.setParent(itemToParent._1, itemToParent._2)

    def rootItemIds: Iterable[Any] = p.rootItemIds.asScala

    def childrenAllowed(itemId: Any): Boolean = p.areChildrenAllowed(itemId)
    def setChildrenAllowed(childrenAllowedForItem: (Any, Boolean)): Unit = p.setChildrenAllowed(childrenAllowedForItem._1, childrenAllowedForItem._2)

    def isRoot(itemId: Any): Boolean = p.isRoot(itemId)

    def hasChildren(itemId: Any): Boolean = p.hasChildren(itemId)
  }

  trait Ordered extends Container {

    def p: com.vaadin.data.Container.Ordered with ContainerOrderedMixin

    def nextItemId(itemId: Any): Any = p.nextItemId(itemId)

    def prevItemId(itemId: Any): Any = p.prevItemId(itemId)

    def firstItemId: Any = p.firstItemId

    def lastItemId: Any = p.lastItemId

    def isFirstId(itemId: Any): Boolean = p.isFirstId(itemId)

    def isLastId(itemId: Any): Boolean = p.isLastId(itemId)

    def addItemAfter(previousItemId: Any): Any = p.addItemAfter(previousItemId)

    def addItemAfter(previousItemId: Any, newItemId: Any): Item = wrapItem(p.addItemAfter(previousItemId, newItemId))
  }

  trait Viewer extends Wrapper {
    def p: com.vaadin.data.Container.Viewer with ContainerViewerMixin

    def container_=(container: Option[Container]): Unit = if (container.isDefined) p.setContainerDataSource(container.get.p) else p.setContainerDataSource(null)
    def container_=(container: Container): Unit = if (container != null) p.setContainerDataSource(container.p) else p.setContainerDataSource(null)
    def container: Option[Container] = wrapperFor[Container](p.getContainerDataSource)
  }

  trait Sortable extends Ordered {
    import scala.collection.JavaConverters._

    def p: com.vaadin.data.Container.Sortable with ContainerSortableMixin

    def sort(propertyId: Array[AnyRef], ascending: Array[Boolean]): Unit = p.sort(propertyId, ascending)

    def sortableContainerPropertyIds(): Iterable[Any] = p.getSortableContainerPropertyIds.asScala
  }

  trait Indexed extends Ordered {

    def p: com.vaadin.data.Container.Indexed with ContainerIndexedMixin

    def indexOfId(itemId: Any): Int = p.indexOfId(itemId)

    def getIdByIndex(index: Int): Any = p.getIdByIndex(index)

    def addItemAt(index: Int): Any = p.addItemAt(index)

    def addItemAt(index: Int, newItemId: Any): Item = wrapItem(p.addItemAt(index, newItemId))
  }
}