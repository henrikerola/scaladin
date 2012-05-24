package vaadin.scala

import com.vaadin.ui.AbstractSelect.MultiSelectMode._
import vaadin.scala.mixins.TreeMixin

package mixins {
  trait TreeMixin extends AbstractSelectMixin
}

/**
 * @see com.vaadin.ui.Tree
 * @author Henri Kerola / Vaadin
 */
class Tree(override val p: com.vaadin.ui.Tree with TreeMixin = new com.vaadin.ui.Tree with TreeMixin)
  extends AbstractSelect(p) with Container.Hierarchical with ItemDescriptionGeneratorOwner {

  def expanded(itemId: Any): Boolean = p.isExpanded(itemId)

  def expandItem(itemId: Any): Boolean = p.expandItem(itemId)

  def expandItemsRecursively(startItemId: Any): Boolean = p.expandItemsRecursively(startItemId)

  def collapseItem(itemId: Any): Boolean = p.collapseItem(itemId)

  def collapseItemsRecursively(startItemId: Any): Boolean = p.collapseItemsRecursively(startItemId)

  def selectionMode = {
    if (!p.isSelectable)
      SelectionMode.None
    else if (p.isMultiSelect && p.getMultiselectMode == SIMPLE)
      SelectionMode.MultiSimple
    else if (p.isMultiSelect)
      SelectionMode.Multi
    else
      SelectionMode.Single
  }

  def selectionMode_=(selectionMode: SelectionMode.Value) = selectionMode match {
    case SelectionMode.None =>
      p.setSelectable(false)
    case SelectionMode.Single =>
      p.setSelectable(true)
      p.setMultiSelect(false)
    case SelectionMode.Multi =>
      p.setSelectable(true)
      p.setMultiSelect(true)
      p.setMultiselectMode(DEFAULT)
    case SelectionMode.MultiSimple =>
      p.setSelectable(true)
      p.setMultiSelect(true)
      p.setMultiselectMode(SIMPLE)
  }

  // TODO: setNullSelectionItemId throws UnsupportedOperationException

  // TODO: ...

}

  