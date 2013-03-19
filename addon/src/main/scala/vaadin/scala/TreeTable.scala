package vaadin.scala

import com.vaadin.ui.{ TreeTable => VaadinTreeTable }
import event.{ CollapseNotifier, ExpandNotifier }
import vaadin.scala.mixins.TreeTableMixin

package mixins {
  trait TreeTableMixin extends TableMixin with ContainerHierarchicalMixin { self: com.vaadin.ui.TreeTable => }
}

/**
 * @see com.vaadin.ui.TreeTable
 * @author Henri Kerola / Vaadin
 */
class TreeTable(override val p: VaadinTreeTable with TreeTableMixin = new VaadinTreeTable with TreeTableMixin)
    extends Table(p) with Container.Hierarchical with ExpandNotifier with CollapseNotifier {

  container = new HierarchicalContainer

  def isCollapsed(itemId: Any): Boolean = p.isCollapsed(itemId)
  def setCollapsed(itemId: Any, collapsed: Boolean) { p.setCollapsed(itemId, collapsed) }

  def hierarchyColumn: Option[Any] = Option(p.getHierarchyColumnId)
  def hierarchyColumn_=(hierarchyColumn: Option[Any]) { p.setHierarchyColumn(hierarchyColumn.orNull) }
  def hierarchyColumn_=(hierarchyColumn: Any) { p.setHierarchyColumn(hierarchyColumn) }

  def animationsEnabled: Boolean = p.isAnimationsEnabled
  def animationsEnabled_=(animationsEnabled: Boolean): Unit = p.setAnimationsEnabled(animationsEnabled)

}