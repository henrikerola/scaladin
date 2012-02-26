package vaadin.scala

package object implicits {

  implicit def containerToFilterable[C <: com.vaadin.data.Container](c: C) = new FilterableContainerWrap(c)

  implicit def itemToFilterable[I <: com.vaadin.data.Item](i: I) = new FilterableItemWrap(i)

  implicit def propertyListToValueWrap[P <: com.vaadin.data.Property](pl: List[P]) = new PropertyListWrap(pl)
}