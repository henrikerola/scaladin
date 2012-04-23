package vaadin.scala
import scala.collection.JavaConverters._

trait FilterableContainer extends Container.Container {

  /**
   * Filter based on item id
   */
  def \(itemId: Any) = getItem(itemId)

  /**
   * Filter based on Item
   */
  def filterItems(itemFilter: Item => Boolean) = getItemIds.asScala.map(getItem).filter(itemFilter).toList

  /**
   * Filter based on property
   */
  def filterProperties(propertyFilter: Property => Boolean) = getItemIds.asScala.map(getItem).flatMap(Item.getProperties).filter(propertyFilter).toList

  /**
   * Filter based on property id
   */
  def \\(propertyId: Any) = getItemIds.asScala.map(getItem).map(_.property(propertyId)).toList
}

trait FilterableItem extends Item {
  /**
   * Filter based on property
   */
  def filterProperties(propertyFilter: Property => Boolean) = Item.getProperties(this).filter(propertyFilter).toList

  /**
   * Filter based on property id
   */
  def \(propertyId: Any): Property = property(propertyId)

  def values = Item.getProperties(this).map(_.value)
}

class FilterableContainerWrap(wrapped: com.vaadin.data.Container) extends Container.Container with FilterableContainer {
  def p = wrapped
  def wrapItem(unwrapped: com.vaadin.data.Item) = new FilterableItemWrap(unwrapped)
  def wrapProperty(unwrapped: com.vaadin.data.Property) = new BasicProperty(unwrapped)
}

class FilterableItemWrap(wrapped: com.vaadin.data.Item) extends Item with FilterableItem {
  def p = wrapped
  def wrapProperty(unwrapped: com.vaadin.data.Property) = new BasicProperty(unwrapped)
}

class PropertyListWrap(wrapped: List[com.vaadin.data.Property]) {
  def values = wrapped.map(_.getValue)
}

