package vaadin.scala

object Container {
  def apply(items: Tuple2[Any, Seq[Tuple2[Any, Any]]]*) = {
    val container = new com.vaadin.data.util.IndexedContainer
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

    def getItem(id: Any) = p.getItem(id)

    def getItemIds() = p.getItemIds

    def removeAllItems() = p.removeAllItems

    def addContainerProperty(propertyId: Any, propertyType: Class[_], defaultValue: Any) = p.addContainerProperty(propertyId, propertyType, defaultValue)

    def removeContainerProperty(propertyId: Any) = p.removeContainerProperty(propertyId)

    def removeItem(itemId: Any) = p.removeItem(itemId)

    def addItem() = p.addItem()

    def addItem(itemId: Any) = p.addItem(itemId)

    def containsId(itemId: Any) = p.containsId(itemId)

    def size() = p.size()

    def getContainerProperty(itemId: Any, propertyId: Any) = p.getContainerProperty(itemId, propertyId)

    def getContainerPropertyIds() = p.getContainerPropertyIds()

    def getType(propertyId: Any) = p.getType(propertyId)
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

    def removeItem(itemId: Any) = p.removeItem(itemId)
  }
}