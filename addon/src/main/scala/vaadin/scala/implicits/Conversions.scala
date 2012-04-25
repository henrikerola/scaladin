package vaadin.scala

package object implicits {

  implicit def containerToFilterable[C <: Container.Container](c: C) = new FilterableContainerWrap(c.p)

  implicit def itemToFilterable[I <: Item](i: I) = new FilterableItemWrap(i.p)

  implicit def propertyListToValueWrap[P <: Property](pl: List[P]) = new PropertyListWrap(pl)
}