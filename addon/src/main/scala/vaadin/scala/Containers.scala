package vaadin.scala
import scala.collection.JavaConverters._

trait FilterableContainer extends Container.Container {

  /**
   * Filter based on item id
   */
  def \(itemId: Any): Option[Item] = getItem(itemId)

  /**
   * Filter based on Item
   */
  def filterItems(itemFilter: Item => Boolean): List[Item] = getItemIds.map(getItem).flatten.filter(itemFilter).toList

  /**
   * Filter based on property
   */
  def filterProperties(propertyFilter: Property => Boolean): List[Property] = getItemIds.map(getItem).flatten.flatMap(Item.getProperties).filter(propertyFilter).toList

  /**
   * Filter based on property id
   */
  def \\(propertyId: Any): List[Property] = getItemIds.map(getItem).flatten.map(_.property(propertyId)).flatten.toList
}

trait FilterableItem extends Item {
  /**
   * Filter based on property
   */
  def filterProperties(propertyFilter: Property => Boolean): List[Property] = Item.getProperties(this).filter(propertyFilter).toList

  /**
   * Filter based on property id
   */
  def \(propertyId: Any): Option[Property] = property(propertyId)

  def values: List[Any] = Item.getProperties(this).map(_.value).toList
}

class FilterableContainerWrap(wrapped: com.vaadin.data.Container) extends Container.Container with FilterableContainer {
  def p = wrapped
  def wrapItem(unwrapped: com.vaadin.data.Item) = new FilterableItemWrap(unwrapped)
  def wrapProperty(unwrapped: com.vaadin.data.Property) = new BasicProperty(unwrapped)
}

class FilterableItemWrap(wrapped: com.vaadin.data.Item) extends FilterableItem {
  def p = wrapped
  def wrapProperty(unwrapped: com.vaadin.data.Property) = new BasicProperty(unwrapped)
}

object EmptyFilterableItem extends FilterableItem {
  val p = null

  override def filterProperties(propertyFilter: Property => Boolean): List[Property] = List()

  override def \(propertyId: Any): Option[Property] = None

  override def values: List[Any] = List()

  def wrapProperty(unwrapped: com.vaadin.data.Property) = null
}

class PropertyListWrap(wrapped: List[Property]) {
  def values = wrapped.map(_.value)
}

