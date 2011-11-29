package vaadin.scala
import scala.collection.JavaConverters._

object Property {
  def apply[T](value: T): com.vaadin.data.Property = new com.vaadin.data.util.ObjectProperty[T](value)
}

object Item {
  def apply(properties: Tuple2[Any, Any]*): com.vaadin.data.Item = {
    val item = new com.vaadin.data.util.PropertysetItem
    properties foreach (p => item.addItemProperty(p._1, Property(p._2)))
    item
  }

  def getProperties(item: com.vaadin.data.Item) = item.getItemPropertyIds.asScala.map(item.getItemProperty)

}

object Container {
  def apply(items: Tuple2[Any, Seq[Tuple2[Any, Any]]]*): com.vaadin.data.Container = {
    val container = new com.vaadin.data.util.IndexedContainer
    for (item <- items) {
      for (property <- item._2) {
        container.addContainerProperty(property._1, property._2.getClass, null)
        val containerItem = container.addItem(item._1)
        containerItem.getItemProperty(property._1).setValue(property._2)
      }
    }

    container
  }
}

trait FilterableContainer extends com.vaadin.data.Container {

  def \(itemFilter: Any => Boolean): ContainerFilterProduct =
    new ContainerFilterProduct(getItemIds.asScala.map(getItem).filter(itemFilter).toList)

  def \\(propertyFilter: Any => Boolean): ItemFilterProduct =
    new ItemFilterProduct(getItemIds.asScala.map(getItem).flatMap(Item.getProperties).filter(propertyFilter).toList)
}

trait FilterableItem extends com.vaadin.data.Item {
  def \(propertyFilter: Any => Boolean): ItemFilterProduct =
    new ItemFilterProduct(Item.getProperties(this).filter(propertyFilter).toList)

}

class ContainerFilterProduct(val items: List[com.vaadin.data.Item]) {
  def \(propertyFilter: Any => Boolean): ItemFilterProduct =
    new ItemFilterProduct(items.flatMap(Item.getProperties).filter(propertyFilter).toList)
}

class ItemFilterProduct(val properties: List[com.vaadin.data.Property]) {
  def values = properties.map(_.getValue)
}
