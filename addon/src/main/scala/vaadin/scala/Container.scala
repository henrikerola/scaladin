package vaadin.scala

import vaadin.scala.mixins._
import vaadin.scala.util.TypeMapper
import scala.collection.mutable
import vaadin.scala.Container.FilterEvent

package mixins {

  trait ContainerSuperCalls {
    def addContainerProperty(propertyId: Any, propertyType: Class[_], defaultValue: Any): Boolean
    def removeContainerProperty(propertyId: Any): Boolean
  }

  trait ContainerMixin extends ScaladinMixin with ContainerSuperCalls {
    this: com.vaadin.data.Container =>

    private val scalaTypes = mutable.Map.empty[Any, Class[_]]

    abstract override def addContainerProperty(propertyId: Any, propertyType: Class[_], defaultValue: Any): Boolean = {
      val javaType = TypeMapper.toJavaType(propertyType)
      if (super.addContainerProperty(propertyId, javaType, defaultValue)) {
        if (propertyType != javaType) {
          scalaTypes += propertyId -> propertyType
        }
        true
      } else false
    }

    abstract override def removeContainerProperty(propertyId: Any): Boolean = {
      if (super.removeContainerProperty(propertyId)) {
        scalaTypes -= propertyId
        true
      } else false
    }

    def getScalaType(propertyId: Any): Class[_] = scalaTypes.getOrElse(propertyId, getType(propertyId))
  }

  trait ContainerHierarchicalMixin extends ContainerMixin { self: com.vaadin.data.Container.Hierarchical => }
  trait ContainerOrderedMixin extends ContainerMixin { self: com.vaadin.data.Container.Ordered => }
  trait ContainerViewerMixin extends ScaladinMixin
  trait ContainerSortableMixin extends ContainerOrderedMixin { self: com.vaadin.data.Container.Sortable => }
  trait ContainerIndexedMixin extends ContainerOrderedMixin { self: com.vaadin.data.Container.Indexed => }
  trait ContainerFilterableMixin extends ContainerMixin { self: com.vaadin.data.Container.Filterable => }

  trait ContainerFilterMixin extends ScaladinInterfaceMixin {
    self: com.vaadin.data.Container.Filter =>

    override def wrapper = super.wrapper.asInstanceOf[Container.Filter]

    override def passesFilter(itemId: Any, item: com.vaadin.data.Item): Boolean =
      wrapper.passesFilter(new FilterEvent(itemId, item))

    override def appliesToProperty(propertyId: Any): Boolean =
      wrapper.appliesToProperty(propertyId)
  }
}

//Base Container trait is outside the companion object so extending classes can have nicer syntax
trait Container extends Wrapper {

  import scala.collection.JavaConverters._

  def p: com.vaadin.data.Container with ContainerMixin

  def getItem(id: Any): Item = p.getItem(id) match {
    case null => null
    case i => wrapItem(i)
  }

  def getItemOption(id: Any): Option[Item] = optionalWrapItem(p.getItem(id))

  def itemIds: Iterable[Any] = p.getItemIds.asScala

  def removeAllItems(): Boolean = p.removeAllItems()

  def addContainerProperty(propertyId: Any, propertyType: Class[_], defaultValue: Option[Any] = None): Boolean =
    p.addContainerProperty(propertyId, propertyType, defaultValue.orNull)

  def removeContainerProperty(propertyId: Any): Boolean = p.removeContainerProperty(propertyId)

  def removeItem(itemId: Any): Boolean = p.removeItem(itemId)

  def addItem(): Any = p.addItem()
  def addItemOption(): Option[Any] = Option(p.addItem())

  def addItem(itemId: Any): Option[Item] = optionalWrapItem(p.addItem(itemId))

  def containsId(itemId: Any): Boolean = p.containsId(itemId)

  def size: Int = p.size()

  def getProperty(itemId: Any, propertyId: Any): Property[_] = p.getContainerProperty(itemId, propertyId) match {
    case null => null
    case p => wrapProperty(propertyId, p)
  }

  def getPropertyOption(itemId: Any, propertyId: Any): Option[Property[_]] =
    optionalWrapProperty(propertyId, p.getContainerProperty(itemId, propertyId))

  def propertyIds: Iterable[Any] = p.getContainerPropertyIds().asScala

  def getType(propertyId: Any): Class[_] = p.getScalaType(propertyId)

  def wrapItem(unwrapped: com.vaadin.data.Item): Item

  //override if needed
  protected def wrapProperty(propertyId: Any, unwrapped: com.vaadin.data.Property[_]): Property[_] = {
    val scalaType = p.getScalaType(propertyId)
    new BasicProperty(unwrapped, Option(scalaType))
  }

  protected def optionalWrapItem(item: com.vaadin.data.Item): Option[Item] = item match {
    case i: com.vaadin.data.Item => Some(wrapItem(i))
    case _ => None
  }

  protected def optionalWrapProperty(propertyId: Any, property: com.vaadin.data.Property[_]): Option[Property[_]] = property match {
    case p: com.vaadin.data.Property[_] => Some(wrapProperty(propertyId, p))
    case _ => None
  }
}

object Container {
  def apply(items: (Any, Seq[(Any, Any)])*): Container = fill(new IndexedContainer, items: _*)

  def filterable(items: (Any, Seq[(Any, Any)])*): FilterableContainer =
    fill(new IndexedContainer with FilterableContainer, items: _*)

  def fill[C <: Container](container: C, items: (Any, Seq[(Any, Any)])*): C = {
    for (item <- items) {
      container.addItem(item._1) match {

        case Some(containerItem: Item) => {
          for (property <- item._2) {
            container.addContainerProperty(property._1, property._2.getClass, None)
            containerItem.getPropertyOption(property._1) match {
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

    def getChildren(itemId: Any): Iterable[Any] = p.getChildren(itemId) match {
      case null => List.empty
      case result => result.asScala
    }

    def getParent(itemId: Any): Any = p.getParent(itemId) // should return Option[Any]?
    def setParent(itemToParent: (Any, Any)) { p.setParent(itemToParent._1, itemToParent._2) }

    def rootItemIds: Iterable[Any] = p.rootItemIds.asScala

    def isChildrenAllowed(itemId: Any): Boolean = p.areChildrenAllowed(itemId)
    def setChildrenAllowed(childrenAllowedForItem: (Any, Boolean)) {
      p.setChildrenAllowed(childrenAllowedForItem._1, childrenAllowedForItem._2)
    }

    def isRoot(itemId: Any): Boolean = p.isRoot(itemId)

    def hasChildren(itemId: Any): Boolean = p.hasChildren(itemId)
  }

  trait Ordered extends Container {

    def p: com.vaadin.data.Container.Ordered with ContainerOrderedMixin

    // TODO: nextItemId, prevItemId, firstItemId, lastItemId should return Option[Any]?

    def nextItemId(itemId: Any): Any = p.nextItemId(itemId)

    def prevItemId(itemId: Any): Any = p.prevItemId(itemId)

    def firstItemId: Any = p.firstItemId

    def lastItemId: Any = p.lastItemId

    def isFirstId(itemId: Any): Boolean = p.isFirstId(itemId)

    def isLastId(itemId: Any): Boolean = p.isLastId(itemId)

    def addItemAfter(previousItemId: Any): Any = p.addItemAfter(previousItemId)

    def addItemAfter(previousItemId: Any, newItemId: Any): Item =
      wrapItem(p.addItemAfter(previousItemId, newItemId))
  }

  trait Viewer extends Wrapper {
    def p: com.vaadin.data.Container.Viewer with ContainerViewerMixin

    def container_=(container: Option[Container]) { p.setContainerDataSource(peerFor(container)) }
    def container_=(container: Container) { p.setContainerDataSource(container.p) }
    def container: Option[Container] = wrapperFor(p.getContainerDataSource)
  }

  trait Sortable extends Ordered {
    import scala.collection.JavaConverters._

    def p: com.vaadin.data.Container.Sortable with ContainerSortableMixin

    def sort(propertyId: Array[AnyRef], ascending: Array[Boolean]) { p.sort(propertyId, ascending) }

    def sortableContainerPropertyIds(): Iterable[Any] = p.getSortableContainerPropertyIds.asScala
  }

  trait Indexed extends Ordered {

    def p: com.vaadin.data.Container.Indexed with ContainerIndexedMixin

    def indexOfId(itemId: Any): Int = p.indexOfId(itemId)

    def getIdByIndex(index: Int): Any = p.getIdByIndex(index)

    def addItemAt(index: Int): Any = p.addItemAt(index)

    def addItemAt(index: Int, newItemId: Any): Item = wrapItem(p.addItemAt(index, newItemId))
  }

  trait Filterable extends Container {

    def p: com.vaadin.data.Container.Filterable with ContainerFilterableMixin

    lazy val filters = new mutable.Set[Filter]() {

      override def +=(filter: Filter): this.type = {
        p.addContainerFilter(filter.pFilter)
        this
      }

      override def -=(filter: Filter): this.type = {
        p.removeContainerFilter(filter.pFilter)
        this
      }

      override def contains(filter: Filter): Boolean =
        p.getContainerFilters.contains(filter.pFilter)

      override def iterator: Iterator[Filter] = {
        import scala.collection.JavaConverters._
        p.getContainerFilters.asScala.map(wrapperFor[Filter](_).get).iterator
      }
    }
  }

  class FilterEvent(val itemId: Any, vaadinItem: com.vaadin.data.Item) {
    lazy val item: Item = new BasicItem(vaadinItem)
  }

  trait Filter extends InterfaceWrapper {
    val pFilter = new com.vaadin.data.Container.Filter with ContainerFilterMixin
    pFilter.wrapper = this

    def passesFilter(event: FilterEvent): Boolean

    def appliesToProperty(propertyId: Any): Boolean
  }

  object Filter {
    def apply(filter: FilterEvent => Boolean, appliesToProp: Any => Boolean = Any => true): Filter = new Filter {
      override def passesFilter(event: FilterEvent): Boolean = filter(event)
      override def appliesToProperty(propertyId: Any): Boolean = appliesToProp(propertyId)
    }
  }
}