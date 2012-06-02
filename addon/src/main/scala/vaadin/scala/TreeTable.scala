package vaadin.scala

import vaadin.scala.mixins.TreeTableMixin
import vaadin.scala.mixins.ContainerHierarchicalMixin

package mixins {
  trait TreeTableMixin extends TableMixin with ContainerHierarchicalMixin
}

object TreeTable {

}

/**
 * @see com.vaadin.ui.TreeTable
 * @author Henri Kerola / Vaadin
 */
class TreeTable(override val p: com.vaadin.ui.TreeTable with TreeTableMixin = new com.vaadin.ui.TreeTable with TreeTableMixin)
  extends Table(p) with ContainerHierarchical {

}