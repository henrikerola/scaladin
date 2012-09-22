package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.BeforeAndAfter
import org.scalatest.mock.MockitoSugar
import vaadin.scala.mixins.TableMixin
import org.mockito.Mockito
import org.mockito.ArgumentCaptor

@RunWith(classOf[JUnitRunner])
class TableTests extends FunSuite with BeforeAndAfter with MockitoSugar {

  class VaadinTable extends com.vaadin.ui.Table with TableMixin {
    override def formatPropertyValue(rowId: Any, colId: Any, property: com.vaadin.data.Property[_]): String = super.formatPropertyValue(rowId, colId, property)
  }

  var table: Table = _
  var spy: VaadinTable = _

  before {
    val vaadinTable = new VaadinTable
    spy = Mockito.spy(vaadinTable)
    table = new Table(spy)
    table.p.addContainerProperty("col1", classOf[String], "")	
    table.p.addContainerProperty("col2", classOf[String], "")
  }

  test("ColumnHeaderModes") {
    assert(Table.ColumnHeaderMode.Hidden.id === com.vaadin.ui.Table.ColumnHeaderMode.HIDDEN.ordinal)
    assert(Table.ColumnHeaderMode.Id.id === com.vaadin.ui.Table.ColumnHeaderMode.ID.ordinal)
    assert(Table.ColumnHeaderMode.Explicit.id === com.vaadin.ui.Table.ColumnHeaderMode.EXPLICIT.ordinal)
    assert(Table.ColumnHeaderMode.ExplicitDefaultsId.id === com.vaadin.ui.Table.ColumnHeaderMode.EXPLICIT_DEFAULTS_ID.ordinal)
  }

  test("RowHeaderModes") {
    assert(Table.RowHeaderMode.Hidden.id === com.vaadin.ui.Table.RowHeaderMode.HIDDEN.ordinal)
    assert(Table.RowHeaderMode.Id.id === com.vaadin.ui.Table.RowHeaderMode.ID.ordinal)
    assert(Table.RowHeaderMode.Item.id === com.vaadin.ui.Table.RowHeaderMode.ITEM.ordinal)
    assert(Table.RowHeaderMode.Index.id === com.vaadin.ui.Table.RowHeaderMode.INDEX.ordinal)
    assert(Table.RowHeaderMode.Explicit.id === com.vaadin.ui.Table.RowHeaderMode.EXPLICIT.ordinal)
    assert(Table.RowHeaderMode.Property.id === com.vaadin.ui.Table.RowHeaderMode.PROPERTY.ordinal)
    assert(Table.RowHeaderMode.IconOnly.id === com.vaadin.ui.Table.RowHeaderMode.ICON_ONLY.ordinal)
    assert(Table.RowHeaderMode.ExplicitDefaultsId.id === com.vaadin.ui.Table.RowHeaderMode.EXPLICIT_DEFAULTS_ID.ordinal)
  }

  test("ColumnAlignments") {
    assert(Table.ColumnAlignment.Left.id === com.vaadin.ui.Table.Align.LEFT.ordinal)
    assert(Table.ColumnAlignment.Center.id === com.vaadin.ui.Table.Align.CENTER.ordinal)
    assert(Table.ColumnAlignment.Right.id === com.vaadin.ui.Table.Align.RIGHT.ordinal)
  }

  test("visibleColumns") {
    assert(table.visibleColumns === "col1" :: "col2" :: Nil)

    table.visibleColumns = "col1" :: Nil
    Mockito.verify(spy).setVisibleColumns(Array("col1"))

    assert(table.visibleColumns === "col1" :: Nil)

    table.visibleColumns = Nil
    assert(table.visibleColumns === Nil)
  }

  test("columnHeaders") {
    table.columnHeaderMode = Table.ColumnHeaderMode.Explicit

    assert(table.columnHeaders === None :: None :: Nil)

    table.columnHeaders = Some("Header 1") :: None :: Nil
    Mockito.verify(spy).setColumnHeaders(Array("Header 1", null))
    assert(table.columnHeaders === Some("Header 1") :: None :: Nil)

    table.columnHeaders = "Header 1" :: "Header 2" :: Nil
    assert(table.columnHeaders === Some("Header 1") :: Some("Header 2") :: Nil)

  }

  test("columnIcons") {
    val icons = Some(new ThemeResource("1.png")) :: None :: Nil

    assert(table.columnIcons === None :: None :: Nil)

    table.columnIcons = icons
    Mockito.verify(spy).setColumnIcons(Array(icons(0).get.p, null))

    assert(table.columnIcons === icons)
  }

  test("columnAlignments") {
    import Table.ColumnAlignment._
    import com.vaadin.ui.Table.Align._

    assert(table.columnAlignments === Left :: Left :: Nil)

    table.columnAlignments = Center :: Right :: Nil
    Mockito.verify(spy).setColumnAlignments(CENTER, RIGHT)

    assert(table.columnAlignments === Center :: Right :: Nil)
  }

  test("columnExpandRatio") {
    assert(table.columnExpandRatio('myPropertyId) === -1)

    table.columnExpandRatio('myPropertyId, 1)
    assert(table.columnExpandRatio('myPropertyId) === 1)
  }

  test("columnWidth") {
    assert(table.columnWidth('myPropertyId) === -1)

    table.columnWidth('myPropertyId, 30)
    assert(table.columnWidth('myPropertyId) === 30)
  }

  test("columnIcon") {
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
    assert(table.columnHeader('myPropertyId) === Some("'myPropertyId"))

    table.columnHeader('myPropertyId, "Test")
    assert(table.columnHeader('myPropertyId) === Some("Test"))

    table.columnHeader('myPropertyId, None)
    assert(table.columnHeader('myPropertyId) === Some("'myPropertyId"))

    table.columnHeader('myPropertyId, Some("Test"))
    assert(table.columnHeader('myPropertyId) === Some("Test"))
  }

  test("columnFooter") {
    assert(table.columnFooter('myPropertyId) === None)

    table.columnFooter('myPropertyId, "Test")
    assert(table.columnFooter('myPropertyId) === Some("Test"))

    table.columnFooter('myPropertyId, None)
    assert(table.columnFooter('myPropertyId) === None)

    table.columnFooter('myPropertyId, Some("Test"))
    assert(table.columnFooter('myPropertyId) === Some("Test"))
  }

  test("columnAlignment") {
    assert(table.columnAlignment('myPropertyId) === Table.ColumnAlignment.Left)

    table.columnAlignment('myPropertyId, Table.ColumnAlignment.Right)
    assert(table.columnAlignment('myPropertyId) === Table.ColumnAlignment.Right)
  }

  test("pageLength") {
    assert(table.pageLength === 15)

    table.pageLength = 23
    assert(table.pageLength === 23)
  }

  test("cacheRate") {
    assert(table.cacheRate === 2)

    table.cacheRate = 0.33
    assert(table.cacheRate === 0.33)
  }

  test("currentPageFirstItemIndex") {
    assert(table.currentPageFirstItemIndex === 0)
    Mockito.verify(spy).getCurrentPageFirstItemIndex

    table.currentPageFirstItemIndex = 10
    Mockito.verify(spy).setCurrentPageFirstItemIndex(10)
  }

  test("currentPageFirstItemId") {
    assert(table.currentPageFirstItemId === None)
    Mockito.verify(spy).getCurrentPageFirstItemId

    table.currentPageFirstItemId = "test"
    Mockito.verify(spy).setCurrentPageFirstItemId("test")
  }

  test("columnCollapsingAllowed") {
    assert(!table.columnCollapsingAllowed)

    table.columnCollapsingAllowed = true
    assert(table.columnCollapsingAllowed)
  }

  test("columnCollapsible") {
    assert(table.columnCollapsible("col1"))

    table.columnCollapsible("col1", false)
    assert(!table.columnCollapsible("col1"))
  }

  test("columnReorderingAllowed") {
    assert(!table.columnReorderingAllowed)

    table.columnReorderingAllowed = true
    assert(table.columnReorderingAllowed)
  }

  test("editable") {
    assert(!table.editable)

    table.editable = true
    assert(table.editable)
  }

  test("sortable") {
    assert(table.sortable)

    table.sortable = false
    assert(!table.sortable)
  }

  test("sortContainerPropertyId") {
    assert(table.sortContainerPropertyId === None)

    table.sortContainerPropertyId = Some("col1")
    assert(table.sortContainerPropertyId === Some("col1"))

    table.sortContainerPropertyId = None
    assert(table.sortContainerPropertyId === None)

    table.sortContainerPropertyId = "col1"
    assert(table.sortContainerPropertyId === Some("col1"))
  }

  test("selectionMode, default should be None") {
    assert(table.selectionMode === SelectionMode.None)

  }

  test("selectionMode, None") {
    import SelectionMode._
    table.selectionMode = None
    assert(table.selectionMode === None)
  }

  test("selectionMode, Single") {
    import SelectionMode._
    table.selectionMode = Single
    assert(table.selectionMode === Single)
  }

  test("selectionMode, Multi") {
    table.selectionMode = SelectionMode.Multi
    assert(table.selectionMode === SelectionMode.Multi)
  }

  test("selectionMode, MultiSimple") {
    table.selectionMode = SelectionMode.MultiSimple
    assert(table.selectionMode === SelectionMode.MultiSimple)
  }

  test("columnHeaderMode") {
    assert(table.columnHeaderMode === Table.ColumnHeaderMode.ExplicitDefaultsId)

    table.columnHeaderMode = Table.ColumnHeaderMode.Explicit
    assert(table.columnHeaderMode === Table.ColumnHeaderMode.Explicit)
  }

  test("rowHeaderMode") {
    assert(table.rowHeaderMode === Table.RowHeaderMode.Hidden)

    table.rowHeaderMode = Table.RowHeaderMode.IconOnly
    assert(table.rowHeaderMode === Table.RowHeaderMode.IconOnly)
  }

  test("footerVisible") {
    assert(!table.footerVisible)

    table.footerVisible = true
    assert(table.footerVisible)
  }

  test("ColumnGenerationEvent") {
    class ColumnGeneratorFunction extends Function1[Table.ColumnGenerationEvent, Option[Any]] {
      def apply(x: Table.ColumnGenerationEvent): Option[Any] = None
    }

    val columnGenerator = new ColumnGeneratorFunction
    val spy = Mockito.spy(columnGenerator)
    table.columnGenerators += "colid" -> spy

    table.p.getColumnGenerator("colid").generateCell(table.p, "itemId", "colid")

    val argument = ArgumentCaptor.forClass(classOf[Table.ColumnGenerationEvent]);
    Mockito.verify(spy).apply(argument.capture())
    assert(argument.getValue.table === table)
    assert(argument.getValue.itemId === "itemId")
    assert(argument.getValue.propertyId === "colid")
  }

  test("columnGenerators") {
    val button = new Button
    
    table.columnGenerators += "colid1" -> { e => None }
    table.columnGenerators += "colid2" -> { e => Some("test") }
    table.columnGenerators += "colid3" -> { e => Some(button) }
    
    assert(table.p.getColumnGenerator("colid1").generateCell(table.p, "itemId", "colid1") === null)
    assert(table.p.getColumnGenerator("colid2").generateCell(table.p, "itemId", "colid2") === "test")
    assert(table.p.getColumnGenerator("colid3").generateCell(table.p, "itemId", "colid3") === button.p)
    assert(table.columnGenerators.size === 3)
    
    table.columnGenerators -= "colid2"
    assert(table.columnGenerators.size === 2)
  }

  ignore("propertyValueFormatter") {
    //    val formatter = { e: Table.FormatPropertyEvent =>
    //      None
    //    }
    //
    //    assert(table.propertyValueFormatter === None)
    //
    //    table.propertyValueFormatter = formatter
    //    assert(table.propertyValueFormatter === Some(formatter))
    //
    //    table.propertyValueFormatter = None
    //    assert(table.propertyValueFormatter === None)
    //
    //    table.propertyValueFormatter = Some(formatter)
    //    assert(table.propertyValueFormatter === Some(formatter))

  }

  ignore("propertyValueFormatter2") {
    //    var eventTable: Table = null
    //    var itemId: Any = null
    //    var propertyId: Any = null
    //
    //    val formatter = { e: Table.FormatPropertyEvent =>
    //      eventTable = e.table
    //      itemId = e.itemId
    //      propertyId = e.propertyId
    //      Some("test")
    //    }
    //    table.propertyValueFormatter = formatter
    //
    //    assert(table.p.asInstanceOf[VaadinTable].formatPropertyValue("itemId", "propId", null) === "test")
    //    assert(itemId === "itemId")
    //    assert(propertyId === "propId")
    //    assert(eventTable === table)

  }

}
