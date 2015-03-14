package vaadin.scala

import com.vaadin.data
import vaadin.scala.mixins.IndexedContainerMixin

package mixins {
  trait IndexedContainerMixin extends ContainerMixin with ContainerIndexedMixin with ContainerSortableMixin {
    self: com.vaadin.data.util.IndexedContainer =>
  }
}

class IndexedContainer(override val p: com.vaadin.data.util.IndexedContainer with IndexedContainerMixin = new com.vaadin.data.util.IndexedContainer with IndexedContainerMixin)
    extends Container.Sortable with Container.Indexed {

  p.wrapper = this

  def wrapItem(unwrapped: com.vaadin.data.Item, propertyTypeResolver: Any => Class[_]): Item =
    new IndexedContainerItem(unwrapped, propertyTypeResolver)
}

class IndexedContainerItem(override val p: com.vaadin.data.Item, propertyTypeResolver: Any => Class[_]) extends Item {
  override protected def wrapProperty(propertyId: Any, unwrapped: data.Property[_]): Property[_, _] = {
    new BasicProperty(unwrapped, Option(propertyTypeResolver(propertyId)))
  }
}