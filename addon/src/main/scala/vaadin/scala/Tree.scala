package vaadin.scala

import com.vaadin.ui.AbstractSelect.MultiSelectMode._
import vaadin.scala.mixins.TreeMixin
import vaadin.scala.mixins.AbstractSelectMixin
import vaadin.scala.mixins.ContainerHierarchicalMixin
import vaadin.scala.internal.ItemStyleGenerator
import vaadin.scala.listeners.ExpandListener
import vaadin.scala.listeners.CollapseListener

package mixins {
  trait TreeMixin extends AbstractSelectMixin with ContainerHierarchicalMixin
}

object Tree {
  // TODO move out from Tree? because possible that used in other components too in future (e.g. ComboBox)
  case class ItemStyleEvent(itemId: Any) extends Event
}

/**
 * @see com.vaadin.ui.Tree
 * @author Henri Kerola / Vaadin
 */
class Tree(override val p: com.vaadin.ui.Tree with TreeMixin = new com.vaadin.ui.Tree with TreeMixin)
  extends AbstractSelect(p) with ContainerHierarchical with ItemDescriptionGeneratorOwner with ItemClickNotifier with ExpandNotifier with CollapseNotifier {

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

  def itemStyleGenerator: Option[Tree.ItemStyleEvent => Option[String]] = p.getItemStyleGenerator match {
    case null => None
    case generator: ItemStyleGenerator => Some(generator.action)
  }
  def itemStyleGenerator_=(generator: Tree.ItemStyleEvent => Option[String]): Unit = {
    p.setItemStyleGenerator(new ItemStyleGenerator(generator))
  }
  def itemStyleGenerator_=(generator: Option[Tree.ItemStyleEvent => Option[String]]): Unit = generator match {
    case None => p.setItemStyleGenerator(null)
    case Some(generator) => itemStyleGenerator = generator
  }

  // TODO: setNullSelectionItemId throws UnsupportedOperationException

  // TODO: ...

}

package internal {
  // FIXME: should not extend Listener?
  class ItemStyleGenerator(val action: Tree.ItemStyleEvent => Option[String]) extends com.vaadin.ui.Tree.ItemStyleGenerator with Listener {
    def getStyle(itemId: Any) = action(Tree.ItemStyleEvent(itemId)).getOrElse(null)
  }
}

