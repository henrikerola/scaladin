package vaadin.scala

object Item {
  def apply(properties: Tuple2[Any, Any]*) = {
    val item = new PropertysetItem()
    properties foreach (p => item.addItemProperty(p._1, Property(p._2)))

    item
  }

  def unapplySeq(item: Item): Option[Seq[Property]] = {
    if (item != null) Some(item.propertyIds.map(item.property).toSeq)
    else None
  }

  def getProperties(item: Item) = item.propertyIds.map(item.property)
}

trait Item extends Wrapper {

  import scala.collection.JavaConverters._

  def p: com.vaadin.data.Item

  def property(id: Any): Property = wrapProperty(p.getItemProperty(id))

  def propertyIds() = p.getItemPropertyIds().asScala

  def addItemProperty(id: Any, property: Property) = p.addItemProperty(id, property.p)

  def removeItemProperty(id: Any) = p.removeItemProperty(id)

  protected def wrapProperty[P <: com.vaadin.data.Property](unwrapped: P): Property
}

class PropertysetItem(override val p: com.vaadin.data.util.PropertysetItem = new com.vaadin.data.util.PropertysetItem) extends Item {

  def getListeners(listenerType: Class[_]) = p.getListeners(listenerType)

  def wrapProperty(unwrapped: com.vaadin.data.Property): Property = new BasicProperty(unwrapped)
}