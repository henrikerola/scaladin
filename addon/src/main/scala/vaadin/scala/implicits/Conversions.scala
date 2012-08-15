package vaadin.scala

package object implicits {

  implicit def containerToFilterable(c: Container) = new FilterableContainerWrap(c.p)

  implicit def itemToFilterable(i: Item) = new FilterableItemWrap(i.p)

  implicit def itemOptionToFilterable(i: Option[Item]) = i match {
    case Some(item: Item) => new FilterableItemWrap(item.p)
    case None => EmptyFilterableItem
  }

  implicit def propertyListToValueWrap[P <: Property[_]](pl: List[P]) = new PropertyListWrap(pl)
}