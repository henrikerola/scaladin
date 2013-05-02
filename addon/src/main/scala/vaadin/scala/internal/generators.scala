package vaadin.scala.internal

import vaadin.scala.Table
import vaadin.scala.Tree
import vaadin.scala.Component
import vaadin.scala.event.ItemDescriptionEvent

class ColumnReorderListener(val action: Table.ColumnReorderEvent => Unit) extends com.vaadin.ui.Table.ColumnReorderListener with Listener {
  def columnReorder(e: com.vaadin.ui.Table.ColumnReorderEvent) = action(Table.ColumnReorderEvent(wrapperFor[Table](e.getComponent).get))
}

class TableColumnGenerator(val action: Table.ColumnGenerationEvent => Option[Any]) extends com.vaadin.ui.Table.ColumnGenerator with Listener {
  def generateCell(table: com.vaadin.ui.Table, itemId: Any, columnId: Any): Object = action(Table.ColumnGenerationEvent(wrapperFor[Table](table).get, itemId, columnId)).orNull match {
    case c: Component => c.p
    case a => a.asInstanceOf[AnyRef]
  }
}

class CellStyleGenerator(val action: Table.CellStyleGenerationEvent => Option[String]) extends com.vaadin.ui.Table.CellStyleGenerator with Listener {
  def getStyle(table: com.vaadin.ui.Table, itemId: Any, propertyId: Any) = action(Table.CellStyleGenerationEvent(wrapperFor[Table](table).get, itemId, propertyId)).orNull

}

// FIXME: should not extend Listener?
class ItemStyleGenerator(val action: Tree.ItemStyleEvent => Option[String]) extends com.vaadin.ui.Tree.ItemStyleGenerator with Listener {
  def getStyle(tree: com.vaadin.ui.Tree, itemId: Any) = action(Tree.ItemStyleEvent(wrapperFor[Tree](tree).get, itemId)).orNull
}

class ItemDescriptionGenerator(val action: ItemDescriptionEvent => String) extends com.vaadin.ui.AbstractSelect.ItemDescriptionGenerator with Listener {
  def generateDescription(source: com.vaadin.ui.Component, itemId: Any, propertyId: Any) = action(ItemDescriptionEvent(wrapperFor[Component](source).get, itemId, propertyId))
}