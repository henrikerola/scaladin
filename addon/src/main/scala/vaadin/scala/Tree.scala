package vaadin.scala

import com.vaadin.shared.ui.MultiSelectMode._
import event._
import vaadin.scala.mixins.TreeMixin
import vaadin.scala.internal.ItemStyleGenerator

package mixins {
  trait TreeMixin extends AbstractSelectMixin with ActionContainerMixin with ContainerHierarchicalMixin {
    self: com.vaadin.ui.Tree =>
  }
}

object Tree {
  // TODO move out from Tree? because possible that used in other components too in future (e.g. ComboBox)
  case class ItemStyleEvent(tree: Tree, itemId: Any) extends Event
}

/**
 * @see com.vaadin.ui.Tree
 * @author Henri Kerola / Vaadin
 */
class Tree(override val p: com.vaadin.ui.Tree with TreeMixin = new com.vaadin.ui.Tree with TreeMixin)
    extends AbstractSelect(p) with Action.Container with Container.Hierarchical with ItemDescriptionGeneratorOwner
    with ItemClickNotifier with ExpandNotifier with CollapseNotifier {

  container = new HierarchicalContainer

  def expanded(itemId: Any): Boolean = p.isExpanded(itemId)

  def expandItem(itemId: Any): Boolean = p.expandItem(itemId)

  def expandItemsRecursively(startItemId: Any): Boolean = p.expandItemsRecursively(startItemId)

  def collapseItem(itemId: Any): Boolean = p.collapseItem(itemId)

  def collapseItemsRecursively(startItemId: Any): Boolean = p.collapseItemsRecursively(startItemId)

  def selectionMode: SelectionMode.Value = {
    if (!p.isSelectable)
      SelectionMode.None
    else if (p.isMultiSelect && p.getMultiselectMode == SIMPLE)
      SelectionMode.MultiSimple
    else if (p.isMultiSelect)
      SelectionMode.Multi
    else
      SelectionMode.Single
  }

  def selectionMode_=(selectionMode: SelectionMode.Value) {
    selectionMode match {
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
  }

  def itemStyleGenerator: Option[Tree.ItemStyleEvent => Option[String]] = p.getItemStyleGenerator match {
    case null => None
    case generator: ItemStyleGenerator => Some(generator.action)
  }
  def itemStyleGenerator_=(generator: Tree.ItemStyleEvent => Option[String]) {
    p.setItemStyleGenerator(new ItemStyleGenerator(generator))
  }
  def itemStyleGenerator_=(generator: Option[Tree.ItemStyleEvent => Option[String]]) {
    generator match {
      case None => p.setItemStyleGenerator(null)
      case Some(generator) => itemStyleGenerator = generator
    }
  }

  // TODO: setNullSelectionItemId throws UnsupportedOperationException

  // TODO: ...

}