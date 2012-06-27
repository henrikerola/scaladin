package vaadin.scala

import vaadin.scala.mixins.TreeTableMixin
import vaadin.scala.mixins.ContainerHierarchicalMixin

package mixins {
  trait TreeTableMixin extends TableMixin with ContainerHierarchicalMixin { self: com.vaadin.ui.TreeTable =>
    
  }
}

/**
 * @see com.vaadin.ui.TreeTable
 * @author Henri Kerola / Vaadin
 */
class TreeTable(override val p: com.vaadin.ui.TreeTable with TreeTableMixin = new com.vaadin.ui.TreeTable with TreeTableMixin)
  extends Table(p) with ContainerHierarchical with ExpandNotifier with CollapseNotifier {

  def collapsed(itemId: Any, collapsed: Boolean) = p.setCollapsed(itemId, collapsed)

  def collapsed(itemId: Any): Boolean = p.isCollapsed(itemId)

  def hierarchyColumn: Option[Any] = Option(p.getHierarchyColumnId)
  def hierarchyColumn_=(hierarchyColumn: Option[Any]) = p.setHierarchyColumn(hierarchyColumn.getOrElse(null))
  def hierarchyColumn_=(hierarchyColumn: Any) = p.setHierarchyColumn(hierarchyColumn)

  def animationsEnabled: Boolean = p.isAnimationsEnabled
  def animationsEnabled_=(animationsEnabled: Boolean) = p.setAnimationsEnabled(animationsEnabled)

}