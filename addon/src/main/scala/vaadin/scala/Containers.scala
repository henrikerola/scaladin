package vaadin.scala
import scala.collection.JavaConverters._

object Property {
  def apply[T](value: T): com.vaadin.data.Property = new com.vaadin.data.util.ObjectProperty[T](value)

  def unapply(property: com.vaadin.data.Property): Option[Any] = {
    if (property != null) Some(property.getValue)
    else None
  }
}

object Item {
  def apply(properties: Tuple2[Any, Any]*) = {
    val item = new com.vaadin.data.util.PropertysetItem
    properties foreach (p => item.addItemProperty(p._1, Property(p._2)))

    item
  }

  def unapplySeq(item: com.vaadin.data.Item): Option[Seq[com.vaadin.data.Property]] = {
    if (item != null) Some(item.getItemPropertyIds.asScala.map(item.getItemProperty(_)).toSeq)
    else None
  }

  def getProperties(item: com.vaadin.data.Item) = item.getItemPropertyIds.asScala.map(item.getItemProperty)
}

trait FilterableContainer extends com.vaadin.data.Container {

  /**
   * Filter based on item id
   */
  def \(itemId: Any) = getItem(itemId)

  /**
   * Filter based on Item
   */
  def filterItems(itemFilter: com.vaadin.data.Item => Boolean) = getItemIds.asScala.map(getItem).filter(itemFilter).toList

  /**
   * Filter based on property
   */
  def filterProperties(propertyFilter: com.vaadin.data.Property => Boolean) = getItemIds.asScala.map(getItem).flatMap(Item.getProperties).filter(propertyFilter).toList

  /**
   * Filter based on property id
   */
  def \\(propertyId: Any) = getItemIds.asScala.map(getItem).map(_.getItemProperty(propertyId)).toList
}

trait FilterableItem extends com.vaadin.data.Item {
  /**
   * Filter based on property
   */
  def filterProperties(propertyFilter: com.vaadin.data.Property => Boolean) = Item.getProperties(this).filter(propertyFilter).toList

  /**
   * Filter based on property id
   */
  def \(propertyId: Any): com.vaadin.data.Property = getItemProperty(propertyId)

  def values = Item.getProperties(this).map(_.getValue)
}

class Item extends Wrapper {

  def p: com.vaadin.data.Item

  def getItemProperty(id: Any) = p.getItemProperty(id)

  def getItemPropertyIds() = p.getItemPropertyIds()

  def addItemProperty(id: Any, property: com.vaadin.data.Property) = p.addItemProperty(id, property)

  def removeItemProperty(id: Any) = p.removeItemProperty(id)
}

class FilterableContainerWrap(wrapped: com.vaadin.data.Container) extends Container with FilterableContainer { def p = wrapped }

class FilterableItemWrap(wrapped: com.vaadin.data.Item) extends Item with FilterableItem { def p = wrapped }

class PropertyListWrap(wrapped: List[com.vaadin.data.Property]) {
  def values = wrapped.map(_.getValue)
}

class FunctionProperty[T](getter: () => T, setter: T => Unit = null) extends com.vaadin.data.Property {
  def getValue = getter().asInstanceOf[AnyRef]

  def setValue(value: Any) = {
    setter(value.asInstanceOf[T])
  }

  def getType = getter.getClass //dirty tricks

  def isReadOnly = setter != null

  def setReadOnly(readOnly: Boolean): Unit = {
    //NOOP
  }
}

