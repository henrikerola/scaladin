package vaadin.scala

import vaadin.scala.mixins.ScaladinMixin

object Item {
  def apply(properties: Tuple2[Any, Any]*): Item = fill(new PropertysetItem, properties: _*)

  def unapplySeq(item: Item): Option[Seq[Property[_]]] = {
    if (item != null) Some(item.propertyIds.map(item.property).flatten.toSeq)
    else None
  }

  def fill[I <: Item](item: I, properties: Tuple2[Any, Any]*) = {
    properties foreach (p => item.addItemProperty(p._1, Property(p._2)))
    item
  }

  def filterable(properties: Tuple2[Any, Any]*): FilterableItem = fill(new PropertysetItem with FilterableItem, properties: _*)

  def getProperties(item: Item): Iterable[Property[_]] = item.propertyIds.flatMap(item.property)
  
  trait Viewer {
    def p: com.vaadin.data.Item.Viewer

    def item: Option[Item] = p.getItemDataSource match {
      case null => None
      case i => Some(new BasicItem(i))
    }

    def item_=(item: Item) = p.setItemDataSource(item.p)
    def item_=(item: Option[Item]) = item match {
      case Some(item) => p.setItemDataSource(item.p)
      case None => p.setItemDataSource(null)
    }
  }
}

trait Item extends Wrapper {

  import scala.collection.JavaConverters._

  def p: com.vaadin.data.Item

  def property(id: Any): Option[Property[_]] = optionalWrapProperty(p.getItemProperty(id))

  def propertyIds(): Iterable[Any] = p.getItemPropertyIds().asScala

  def addItemProperty(id: Any, property: Property[_]): Boolean = p.addItemProperty(id, property.p)

  def removeItemProperty(id: Any): Boolean = p.removeItemProperty(id)

  protected def optionalWrapProperty(property: com.vaadin.data.Property[_]): Option[Property[_]] = property match {
    case p: com.vaadin.data.Property[_] => Some(wrapProperty(p))
    case _ => None
  }

  //override if needed
  protected def wrapProperty(unwrapped: com.vaadin.data.Property[_]): Property[_] = new BasicProperty(unwrapped)
}

class PropertysetItem(override val p: com.vaadin.data.util.PropertysetItem = new com.vaadin.data.util.PropertysetItem) extends Item {

  def listeners(listenerType: Class[_]) = p.getListeners(listenerType)
}

class BasicItem(override val p: com.vaadin.data.Item) extends Item