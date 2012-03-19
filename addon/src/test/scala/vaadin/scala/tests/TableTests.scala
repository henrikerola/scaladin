package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite

class TableTests extends FunSuite {

  test("pageLength") {
    val table = new Table

    assert(table.pageLength === 15)

    table.pageLength = 23
    assert(table.pageLength === 23)
  }

  test("cacheRate") {
    val table = new Table

    assert(table.cacheRate === 2)

    table.cacheRate = 0.33
    assert(table.cacheRate === 0.33)
  }

  test("columnCollapsingAllowed") {
    val table = new Table

    assert(!table.columnCollapsingAllowed)

    table.columnCollapsingAllowed = true
    assert(table.columnCollapsingAllowed)
  }

  test("columnReorderingAllowed") {
    val table = new Table

    assert(!table.columnReorderingAllowed)

    table.columnReorderingAllowed = true
    assert(table.columnReorderingAllowed)
  }
  
  test("editable") {
    val table = new Table
    
    assert(!table.editable)
    
    table.sortable = true
    assert(table.sortable)
  }
  
  test("sortable") {
    val table = new Table
    
    assert(table.sortable)
    
    table.sortable = false
    assert(!table.sortable)
  }

  test("ColumnHeaderModes") {
    assert(Table.ColumnHeaderMode.hidden.id === com.vaadin.ui.Table.COLUMN_HEADER_MODE_HIDDEN)
    assert(Table.ColumnHeaderMode.id.id === com.vaadin.ui.Table.COLUMN_HEADER_MODE_ID)
    assert(Table.ColumnHeaderMode.explicit.id === com.vaadin.ui.Table.COLUMN_HEADER_MODE_EXPLICIT)
    assert(Table.ColumnHeaderMode.explicitDefaultsId.id === com.vaadin.ui.Table.COLUMN_HEADER_MODE_EXPLICIT_DEFAULTS_ID)
  }

  test("columnHeaderMode") {
    val table = new Table

    assert(table.columnHeaderMode === Table.ColumnHeaderMode.explicitDefaultsId)

    table.columnHeaderMode = Table.ColumnHeaderMode.explicit
    assert(table.columnHeaderMode === Table.ColumnHeaderMode.explicit)
  }

  test("RowHeaderModes") {
    assert(Table.RowHeaderMode.hidden.id === com.vaadin.ui.Table.ROW_HEADER_MODE_HIDDEN)
    assert(Table.RowHeaderMode.id.id === com.vaadin.ui.Table.ROW_HEADER_MODE_ID)
    assert(Table.RowHeaderMode.item.id === com.vaadin.ui.Table.ROW_HEADER_MODE_ITEM)
    assert(Table.RowHeaderMode.index.id === com.vaadin.ui.Table.ROW_HEADER_MODE_INDEX)
    assert(Table.RowHeaderMode.explicit.id === com.vaadin.ui.Table.ROW_HEADER_MODE_EXPLICIT)
    assert(Table.RowHeaderMode.property.id === com.vaadin.ui.Table.ROW_HEADER_MODE_PROPERTY)
    assert(Table.RowHeaderMode.iconOnly.id === com.vaadin.ui.Table.ROW_HEADER_MODE_ICON_ONLY)
    assert(Table.RowHeaderMode.explicitDefaultsId.id === com.vaadin.ui.Table.ROW_HEADER_MODE_EXPLICIT_DEFAULTS_ID)
  }

  test("rowHeaderMode") {
    val table = new Table

    assert(table.rowHeaderMode === Table.RowHeaderMode.hidden)

    table.rowHeaderMode = Table.RowHeaderMode.iconOnly
    assert(table.rowHeaderMode === Table.RowHeaderMode.iconOnly)
  }

  test("footerVisible") {
    val table = new Table

    assert(!table.footerVisible)

    table.footerVisible = true
    assert(table.footerVisible)
  }

}