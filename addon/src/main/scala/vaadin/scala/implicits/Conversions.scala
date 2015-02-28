package vaadin.scala

package object implicits {

  implicit def containerToFilterable(c: Container) = new FilterableContainerWrap(c.p)

  implicit def itemToFilterable(i: Item) = new FilterableItemWrap(i.p)

  implicit def itemOptionToFilterable(i: Option[Item]) = i match {
    case Some(item: Item) => new FilterableItemWrap(item.p)
    case None => EmptyFilterableItem
  }

  implicit def propertyListToValueWrap[P <: Property[_, _]](pl: List[P]) = new PropertyListWrap(pl)

  import vaadin.scala.mixins.ComponentMixin
  import vaadin.scala.mixins.ComponentContainerMixin
  implicit def wrapperComponentToWrapped[W <: com.vaadin.ui.Component with ComponentMixin](wrapper: ScaladinComponentWrapper[W]): W = wrapper.p.asInstanceOf[W]
  implicit def wrapperComponentContainerToWrapped[W <: com.vaadin.ui.ComponentContainer with ComponentContainerMixin](wrapper: ScaladinComponentContainerWrapper[W]): W = wrapper.p.asInstanceOf[W]
}