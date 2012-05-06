package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TableTests extends FunSuite {
  
  test("ColumnHeaderModes") {
    assert(Table.ColumnHeaderMode.Hidden.id === com.vaadin.ui.Table.COLUMN_HEADER_MODE_HIDDEN)
    assert(Table.ColumnHeaderMode.Id.id === com.vaadin.ui.Table.COLUMN_HEADER_MODE_ID)
    assert(Table.ColumnHeaderMode.Explicit.id === com.vaadin.ui.Table.COLUMN_HEADER_MODE_EXPLICIT)
    assert(Table.ColumnHeaderMode.ExplicitDefaultsId.id === com.vaadin.ui.Table.COLUMN_HEADER_MODE_EXPLICIT_DEFAULTS_ID)
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
  
  test("ColumnAlignments") {
    assert(Table.ColumnAlignment.Left.toString === com.vaadin.ui.Table.ALIGN_LEFT)
    assert(Table.ColumnAlignment.Center.toString === com.vaadin.ui.Table.ALIGN_CENTER)
    assert(Table.ColumnAlignment.Right.toString === com.vaadin.ui.Table.ALIGN_RIGHT)
  }

  test("columnExpandRatio") {
    val table = new Table

    assert(table.columnExpandRatio('myPropertyId) === -1)

    table.columnExpandRatio('myPropertyId, 1)
    assert(table.columnExpandRatio('myPropertyId) === 1)
  }
  
  test("columnWidth") {
    val table = new Table

    assert(table.columnWidth('myPropertyId) === -1)

    table.columnWidth('myPropertyId, 30)
    assert(table.columnWidth('myPropertyId) === 30)
  }
  
  test("columnIcon") {
    val table = new Table
    assert(table.columnIcon('myPropertyId) === None)
    
    val icon = new ThemeResource("test.png")
    
    table.columnIcon('myPropertyId, icon)
    assert(table.columnIcon('myPropertyId) === Some(icon))
    
    table.columnIcon('myPropertyId, None)
    assert(table.columnIcon('myPropertyId) === None)
    
    table.columnIcon('myPropertyId, Some(icon))
    assert(table.columnIcon('myPropertyId) === Some(icon))
  }
  
  test("columnHeader") {
    val table = new Table
    assert(table.columnHeader('myPropertyId) === Some("'myPropertyId"))
    
    table.columnHeader('myPropertyId, "Test")
    assert(table.columnHeader('myPropertyId) === Some("Test"))
    
    table.columnHeader('myPropertyId, None)
    assert(table.columnHeader('myPropertyId) === Some("'myPropertyId"))
    
    table.columnHeader('myPropertyId, Some("Test"))
    assert(table.columnHeader('myPropertyId) === Some("Test"))
  }
  
  test("columnFooter") {
    val table = new Table
    assert(table.columnFooter('myPropertyId) === None)
    
    table.columnFooter('myPropertyId, "Test")
    assert(table.columnFooter('myPropertyId) === Some("Test"))
    
    table.columnFooter('myPropertyId, None)
    assert(table.columnFooter('myPropertyId) === None)
    
    table.columnFooter('myPropertyId, Some("Test"))
    assert(table.columnFooter('myPropertyId) === Some("Test"))
  }
  
  test("columnAlignment") {
    val table = new Table
    assert(table.columnAlignment('myPropertyId) === Table.ColumnAlignment.Left)
    
    table.columnAlignment('myPropertyId, Table.ColumnAlignment.Right)
    assert(table.columnAlignment('myPropertyId) === Table.ColumnAlignment.Right)
  }

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
    assert(table.selectionMode === SelectionMode.None)

  }

  test("selectionMode, None") {
    import SelectionMode._
    val table = new Table
    table.selectionMode = None
    assert(table.selectionMode === None)
  }

  test("selectionMode, Single") {
    import SelectionMode._
    val table = new Table
    table.selectionMode = Single
    assert(table.selectionMode === Single)
  }

  test("selectionMode, Multi") {
    val table = new Table
    table.selectionMode = SelectionMode.Multi
    assert(table.selectionMode === SelectionMode.Multi)
  }

  test("selectionMode, MultiSimple") {
    val table = new Table
    table.selectionMode = SelectionMode.MultiSimple
    assert(table.selectionMode === SelectionMode.MultiSimple)
  }

  test("columnHeaderMode") {
    val table = new Table

    assert(table.columnHeaderMode === Table.ColumnHeaderMode.ExplicitDefaultsId)

    table.columnHeaderMode = Table.ColumnHeaderMode.Explicit
    assert(table.columnHeaderMode === Table.ColumnHeaderMode.Explicit)
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