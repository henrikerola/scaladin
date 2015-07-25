package vaadin.scala

import com.vaadin.data.{ Property => VaadinProperty }
import vaadin.scala.mixins.IndexedContainerMixin

package mixins {
  trait IndexedContainerMixin extends ContainerMixin with ContainerIndexedMixin
      with ContainerSortableMixin with ContainerFilterableMixin {
    self: com.vaadin.data.util.IndexedContainer =>
  }
}

class IndexedContainer(override val p: com.vaadin.data.util.IndexedContainer with IndexedContainerMixin = new com.vaadin.data.util.IndexedContainer with IndexedContainerMixin)
    extends Container.Sortable with Container.Indexed with Container.Filterable {

  p.wrapper = this

  override def wrapItem(unwrapped: com.vaadin.data.Item): Item =
    new IndexedContainerItem(unwrapped, p.getScalaType)
}

class IndexedContainerItem(override val p: com.vaadin.data.Item, propertyTypeResolver: Any => Class[_]) extends Item {
  override protected def wrapProperty(propertyId: Any, unwrapped: VaadinProperty[_]): Property[_] = {
    new BasicProperty(unwrapped, Option(propertyTypeResolver(propertyId)))
  }
}