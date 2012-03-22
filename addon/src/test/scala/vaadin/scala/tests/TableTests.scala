package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite

class TableTests extends FunSuite {

  implicit val testWr = new WrapperRegistry

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

    table.editable = true
    assert(table.editable)
  }

  test("sortable") {
    val table = new Table

    assert(table.sortable)

    table.sortable = false
    assert(!table.sortable)
  }

  test("selectionMode, default should be None") {
    val table = new Table
    assert(table.selectionMode === Table.SelectionMode.None)

  }

  test("selectionMode, None") {
    import Table.SelectionMode._
    val table = new Table
    table.selectionMode = None
    assert(table.selectionMode === None)
  }

  test("selectionMode, Single") {
    import Table.SelectionMode._
    val table = new Table
    table.selectionMode = Single
    assert(table.selectionMode === Single)
  }

  test("selectionMode, Multi") {
    val table = new Table
    table.selectionMode = Table.SelectionMode.Multi
    assert(table.selectionMode === Table.SelectionMode.Multi)
  }

  test("selectionMode, MultiSimple") {
    val table = new Table
    table.selectionMode = Table.SelectionMode.MultiSimple
    assert(table.selectionMode === Table.SelectionMode.MultiSimple)
  }

  test("ColumnHeaderModes") {
    assert(Table.ColumnHeaderMode.Hidden.id === com.vaadin.ui.Table.COLUMN_HEADER_MODE_HIDDEN)
    assert(Table.ColumnHeaderMode.Id.id === com.vaadin.ui.Table.COLUMN_HEADER_MODE_ID)
    assert(Table.ColumnHeaderMode.Explicit.id === com.vaadin.ui.Table.COLUMN_HEADER_MODE_EXPLICIT)
    assert(Table.ColumnHeaderMode.ExplicitDefaultsId.id === com.vaadin.ui.Table.COLUMN_HEADER_MODE_EXPLICIT_DEFAULTS_ID)
  }

  test("columnHeaderMode") {
    val table = new Table

    assert(table.columnHeaderMode === Table.ColumnHeaderMode.ExplicitDefaultsId)

    table.columnHeaderMode = Table.ColumnHeaderMode.Explicit
    assert(table.columnHeaderMode === Table.ColumnHeaderMode.Explicit)
  }

  test("RowHeaderModes") {
    assert(Table.RowHeaderMode.Hidden.id === com.vaadin.ui.Table.ROW_HEADER_MODE_HIDDEN)
    assert(Table.RowHeaderMode.Id.id === com.vaadin.ui.Table.ROW_HEADER_MODE_ID)
    assert(Table.RowHeaderMode.Item.id === com.vaadin.ui.Table.ROW_HEADER_MODE_ITEM)
    assert(Table.RowHeaderMode.Index.id === com.vaadin.ui.Table.ROW_HEADER_MODE_INDEX)
    assert(Table.RowHeaderMode.Explicit.id === com.vaadin.ui.Table.ROW_HEADER_MODE_EXPLICIT)
    assert(Table.RowHeaderMode.Property.id === com.vaadin.ui.Table.ROW_HEADER_MODE_PROPERTY)
    assert(Table.RowHeaderMode.IconOnly.id === com.vaadin.ui.Table.ROW_HEADER_MODE_ICON_ONLY)
    assert(Table.RowHeaderMode.ExplicitDefaultsId.id === com.vaadin.ui.Table.ROW_HEADER_MODE_EXPLICIT_DEFAULTS_ID)
  }

  test("rowHeaderMode") {
    val table = new Table

    assert(table.rowHeaderMode === Table.RowHeaderMode.Hidden)

    table.rowHeaderMode = Table.RowHeaderMode.IconOnly
    assert(table.rowHeaderMode === Table.RowHeaderMode.IconOnly)
  }

  test("footerVisible") {
    val table = new Table

    assert(!table.footerVisible)

    table.footerVisible = true
    assert(table.footerVisible)
  }

}