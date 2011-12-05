package vaadin.scala
import scala.collection.JavaConverters._

object Property {
  def apply[T](value: T): com.vaadin.data.Property = new com.vaadin.data.util.ObjectProperty[T](value)
}

object Item {
  def apply(properties: Tuple2[Any, Any]*): FilterableItem = {
    val item = new com.vaadin.data.util.PropertysetItem with FilterableItem
    properties foreach (p => item.addItemProperty(p._1, Property(p._2)))

    item
  }

  def getProperties(item: com.vaadin.data.Item) = item.getItemPropertyIds.asScala.map(item.getItemProperty)
  def getProperties(item: com.vaadin.data.Item, propertyId: Any) = item.getItemPropertyIds.asScala.filter(_ == propertyId).map(item.getItemProperty)
}

object Container {
  def apply(items: Tuple2[Any, Seq[Tuple2[Any, Any]]]*): FilterableContainer = {
    val container = new com.vaadin.data.util.IndexedContainer with FilterableContainer
    for (item <- items) {
      val containerItem = container.addItem(item._1)
      for (property <- item._2) {
        container.addContainerProperty(property._1, property._2.getClass, null)
        containerItem.getItemProperty(property._1).setValue(property._2)
      }
    }

    container
  }
}

trait FilterableContainer extends com.vaadin.data.Container {

  /**
   * Filter based on item id
   */
  def \(itemId: Any): SingleContainerFilterProduct = new SingleContainerFilterProduct(getItem(itemId))

  /**
   * Filter based on Item
   */
  def filterItems(itemFilter: com.vaadin.data.Item => Boolean): ContainerFilterProduct =
    new ContainerFilterProduct(getItemIds.asScala.map(getItem).filter(itemFilter).toList)

  /**
   * Filter based on property
   */
  def filterProperties(propertyFilter: com.vaadin.data.Property => Boolean): ItemFilterProduct =
    new ItemFilterProduct(getItemIds.asScala.map(getItem).flatMap(Item.getProperties).filter(propertyFilter).toList)

  /**
   * Filter based on property id
   */
  def \\(propertyId: Any): ItemFilterProduct =
    new ItemFilterProduct(getItemIds.asScala.map(getItem).map(_.getItemProperty(propertyId)).toList)
}

trait FilterableItem extends com.vaadin.data.Item {
  /**
   * Filter based on property
   */
  def filterProperties(propertyFilter: com.vaadin.data.Property => Boolean): ItemFilterProduct =
    new ItemFilterProduct(Item.getProperties(this).filter(propertyFilter).toList)

  /**
   * Filter based on property id
   */
  def \(propertyId: Any): com.vaadin.data.Property = getItemProperty(propertyId)
}

class ContainerFilterProduct(val items: List[com.vaadin.data.Item]) {

  /**
   * Filter based on property
   */
  def filterProperties(propertyFilter: com.vaadin.data.Property => Boolean): ItemFilterProduct =
    new ItemFilterProduct(items.flatMap(Item.getProperties).filter(propertyFilter).toList)

  /**
   * Filter based on property id
   */
  def \(propertyId: Any): ItemFilterProduct =
    new ItemFilterProduct(items.map(_.getItemProperty(propertyId)))
}

class SingleContainerFilterProduct(val item: com.vaadin.data.Item) {

  /**
   * Filter based on property
   */
  def filterProperties(propertyFilter: com.vaadin.data.Property => Boolean): ItemFilterProduct =
    new ItemFilterProduct(Item.getProperties(item).filter(propertyFilter).toList)

  /**
   * Filter based on property id
   */
  def \(propertyId: Any): com.vaadin.data.Property = item.getItemProperty(propertyId)
}

class ItemFilterProduct(val properties: List[com.vaadin.data.Property]) {
  def values = properties.map(_.getValue)
}
