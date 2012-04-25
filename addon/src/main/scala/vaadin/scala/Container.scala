package vaadin.scala

object Container {
  def apply(items: Tuple2[Any, Seq[Tuple2[Any, Any]]]*): Container = fill(new IndexedContainer, items: _*)

  def filterable(items: Tuple2[Any, Seq[Tuple2[Any, Any]]]*): FilterableContainer = fill(new IndexedContainer with FilterableContainer, items: _*)

  def fill[C <: Container](container: C, items: Tuple2[Any, Seq[Tuple2[Any, Any]]]*): C = {
    for (item <- items) {
      val containerItem = container.addItem(item._1)
      for (property <- item._2) {
        container.addContainerProperty(property._1, property._2.getClass, null)
        containerItem.getItemProperty(property._1).setValue(property._2)
      }
    }
    container
  }

  trait Container extends Wrapper {

    def p: com.vaadin.data.Container

    def getItem(id: Any): Item = wrapItem(p.getItem(id))

    def getItemIds() = p.getItemIds

    def removeAllItems() = p.removeAllItems

    def addContainerProperty(propertyId: Any, propertyType: Class[_], defaultValue: Any) = p.addContainerProperty(propertyId, propertyType, defaultValue)

    def removeContainerProperty(propertyId: Any) = p.removeContainerProperty(propertyId)

    def removeItem(itemId: Any) = p.removeItem(itemId)

    def addItem() = p.addItem()

    def addItem(itemId: Any) = p.addItem(itemId)

    def containsId(itemId: Any) = p.containsId(itemId)

    def size() = p.size()

    def getContainerProperty(itemId: Any, propertyId: Any) = wrapProperty(p.getContainerProperty(itemId, propertyId))

    def getContainerPropertyIds() = p.getContainerPropertyIds()

    def getType(propertyId: Any) = p.getType(propertyId)

    protected def wrapItem(unwrapped: com.vaadin.data.Item): Item

    protected def wrapProperty(unwrapped: com.vaadin.data.Property): Property
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

    def getSortableContainerPropertyIds(): Iterable[_] = p.getSortableContainerPropertyIds.asScala
  }
}