package vaadin.scala

import vaadin.scala.mixins.HierarchicalContainerMixin

package mixins {
  trait HierarchicalContainerMixin extends IndexedContainerMixin with ContainerHierarchicalMixin {
    self: com.vaadin.data.util.HierarchicalContainer =>
  }
}

object HierarchicalContainer {
  def removeItemRecursively(container: Container.Hierarchical, itemId: Any): Boolean = com.vaadin.data.util.HierarchicalContainer.removeItemRecursively(container.p, itemId)
}

/**
 * @see com.vaadin.data.util.HierarchicalContainer
 * @author Henri Kerola / Vaadin
 */
class HierarchicalContainer(override val p: com.vaadin.data.util.HierarchicalContainer with HierarchicalContainerMixin = new com.vaadin.data.util.HierarchicalContainer with HierarchicalContainerMixin)
    extends IndexedContainer(p) with Container.Hierarchical {

  def moveAfterSibling(itemId: Any, siblingId: Any): Unit = p.moveAfterSibling(itemId, siblingId)

  def removeItemRecursively(itemId: Any): Unit = p.removeItemRecursively(itemId)

  def includeParentsWhenFiltering: Boolean = p.isIncludeParentsWhenFiltering
  def includeParentsWhenFiltering_=(includeParentsWhenFiltering: Boolean): Unit = p.setIncludeParentsWhenFiltering(includeParentsWhenFiltering)

  // TODO: protected doSort(), doFilterContainer, passesFilters
}