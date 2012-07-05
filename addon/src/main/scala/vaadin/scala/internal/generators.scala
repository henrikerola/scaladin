package vaadin.scala.internal

import vaadin.scala.Table
import vaadin.scala.Listener

class ColumnReorderListener(val action: Table.ColumnReorderEvent => Unit) extends com.vaadin.ui.Table.ColumnReorderListener with Listener {
  def columnReorder(e: com.vaadin.ui.Table.ColumnReorderEvent) = action(Table.ColumnReorderEvent(wrapperFor[Table](e.getComponent).get))
}

class TableColumnGenerator(val action: Table.ColumnGenerationEvent => Any) extends com.vaadin.ui.Table.ColumnGenerator with Listener {
  def generateCell(table: com.vaadin.ui.Table, itemId: Any, columnId: Any): Object = action(Table.ColumnGenerationEvent(wrapperFor[Table](table).get, itemId, columnId)).asInstanceOf[AnyRef]
}

class CellStyleGenerator(val generator: Table.CellStyleGenerationEvent => Option[String]) extends com.vaadin.ui.Table.CellStyleGenerator {
  def getStyle(itemId: Any, propertyId: Any) = generator(Table.CellStyleGenerationEvent(itemId, propertyId)).getOrElse(null)

}