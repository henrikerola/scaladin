package vaadin.scala

object Container {
  def apply(items: Tuple2[Any, Seq[Tuple2[Any, Any]]]*): Container = fill(new IndexedContainer, items: _*)

  def filterable(items: Tuple2[Any, Seq[Tuple2[Any, Any]]]*): FilterableContainer = fill(new IndexedContainer with FilterableContainer, items: _*)

  def fill[C <: Container](container: C, items: Tuple2[Any, Seq[Tuple2[Any, Any]]]*): C = {
    for (item <- items) {
      container.addItem(item._1) match {

        case Some(containerItem: Item) => {
          for (property <- item._2) {
            container.addContainerProperty(property._1, property._2.getClass, null)
            containerItem.property(property._1) match {
              case Some(p: Property) => p.value = (property._2)
              case None =>
            }
          }
        }

        case None =>
      }
    }
    container
  }

  trait Container extends Wrapper {

    import scala.collection.JavaConverters._

    def p: com.vaadin.data.Container

    def item(id: Any): Option[Item] = optionalWrapItem(p.getItem(id))

    def itemIds(): Iterable[Any] = p.getItemIds.asScala

    def removeAllItems(): Boolean = p.removeAllItems

    def addContainerProperty(propertyId: Any, propertyType: Class[_], defaultValue: Any): Boolean = p.addContainerProperty(propertyId, propertyType, defaultValue)

    def removeContainerProperty(propertyId: Any): Boolean = p.removeContainerProperty(propertyId)

    def removeItem(itemId: Any): Boolean = p.removeItem(itemId)

    def addItem(): Option[Any] = Some(p.addItem())

    def addItem(itemId: Any): Option[Item] = optionalWrapItem(p.addItem(itemId))

    def containsId(itemId: Any): Boolean = p.containsId(itemId)

    def size(): Int = p.size()

    def property(itemId: Any, propertyId: Any): Property = wrapProperty(p.getContainerProperty(itemId, propertyId))

    def propertyIds(): Iterable[Any] = p.getContainerPropertyIds().asScala

    def getType(propertyId: Any): Class[_] = p.getType(propertyId)

    protected def wrapItem(unwrapped: com.vaadin.data.Item): Item

    protected def wrapProperty(unwrapped: com.vaadin.data.Property): Property

    protected def optionalWrapItem(item: com.vaadin.data.Item): Option[Item] = item match {
      case i: com.vaadin.data.Item => Some(wrapItem(i))
      case _ => None
    }
  }

  trait Viewer extends Wrapper {
    def p: com.vaadin.data.Container.Viewer

    def container_=(container: Container) = p.setContainerDataSource(container.p)
    def container = p.getContainerDataSource
  }

  trait Hierarchical extends Container {

    def p: com.vaadin.data.Container.Hierarchical

    def children(itemId: Any) = p.getChildren(itemId)

    def parent(itemId: Any) = p.getParent(itemId)
    def parent_=(itemId: Any, newParentId: Any) = p.setParent(itemId, newParentId)

    def rootItemIds = p.rootItemIds

    def areChildrenAllowed(itemId: Any) = p.areChildrenAllowed(itemId)

    def setChildrenAllowed(itemId: Any, areChildrenAllowed: Boolean) = p.setChildrenAllowed(itemId, areChildrenAllowed)

    def isRoot(itemId: Any) = p.isRoot(itemId)

    def hasChildren(itemId: Any) = p.hasChildren(itemId)

    override def removeItem(itemId: Any) = p.removeItem(itemId)
  }
  trait Ordered extends Container {

    def p: com.vaadin.data.Container.Ordered

    def nextItemId(itemId: Any): Any = p.nextItemId(itemId)

    def prevItemId(itemId: Any): Any = p.prevItemId(itemId)

    def firstItemId(): Any = p.firstItemId

    def lastItemId(): Any = p.lastItemId

    def isFirstId(itemId: Any): Boolean = p.isFirstId(itemId)

    def isLastId(itemId: Any): Boolean = p.isLastId(itemId)

    def addItemAfter(previousItemId: Any): Any = p.addItemAfter(previousItemId)

    def addItemAfter(previousItemId: Any, newItemId: Any): Item = wrapItem(p.addItemAfter(previousItemId, newItemId))

  }

  trait Sortable extends Ordered {
    import scala.collection.JavaConverters._

    def p: com.vaadin.data.Container.Sortable

    def sort(propertyId: Array[AnyRef], ascending: Array[Boolean]) = p.sort(propertyId, ascending)

    def sortableContainerPropertyIds(): Iterable[Any] = p.getSortableContainerPropertyIds.asScala
  }
}