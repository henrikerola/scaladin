package vaadin.scala.tests

import com.vaadin.data.sort.SortOrder
import vaadin.scala.converter.ResourceConverter
import vaadin.scala.event.{ SortEvent, SelectionEvent }
import com.vaadin.shared.data.sort.SortDirection
import com.vaadin.ui.Grid.{ RowReference, Column }
import org.mockito.{ ArgumentCaptor, Mockito }
import vaadin.scala.mixins.GridMixin
import vaadin.scala._
import vaadin.scala.Grid.HeightMode
import vaadin.scala.renderers.{ ImageRenderer, HtmlRenderer, TextRenderer }
import vaadin.scala.server.Resource

/**
 *
 * @author Henri Kerola / Vaadin
 */
class GridTests extends ScaladinTestSuite {

  class VaadinGrid extends com.vaadin.ui.Grid with GridMixin {
    setContainerDataSource(new IndexedContainer().p)
    val f = classOf[com.vaadin.ui.Grid].getDeclaredField("defaultContainer")
    f.setAccessible(true)
    f.set(this, true)
  }

  var grid: Grid = _
  var vaadinGrid: VaadinGrid = _
  var spy: VaadinGrid = _

  before {
    vaadinGrid = new VaadinGrid
    spy = Mockito.spy(vaadinGrid)
    grid = new Grid(spy)
    vaadinGrid.wrapper = grid
  }

  test("container") {
    assert(null != grid.container)

    val myContainer = new IndexedContainer
    grid.container = myContainer
    assert(myContainer == grid.container)
  }

  test("getColumn") {
    assert(grid.getColumn("fdf").isEmpty)

    Mockito.when(spy.getColumn("myid")).thenReturn(mock[Column])

    assert(grid.getColumn("myid").isDefined)
  }

  test("HeaderCell.text") {
    grid.addColumn("col1")
    val cell = grid.getHeaderRow(0).getCell("col1").get

    assert("Col1" == cell.text)

    cell.text = "test"
    assert("test" == cell.text)
  }

  test("HeaderCell.html") {
    grid.addColumn("col1")
    val cell = grid.getHeaderRow(0).getCell("col1").get

    cell.html = "<b>myhtml</b>"
    assert("<b>myhtml</b>" == cell.html)
  }

  test("HeaderCell.component") {
    grid.addColumn("col1")
    val cell = grid.getHeaderRow(0).getCell("col1").get

    val label = Label("")
    cell.component = label
    assert(label == cell.component)
  }

  test("HeaderCell.styleName") {
    grid.addColumn("col1")
    val cell = grid.getHeaderRow(0).getCell("col1").get

    assert(None == cell.styleName)

    cell.styleName = "test"
    assert(Some("test") == cell.styleName)

    cell.styleName = None
    assert(None == cell.styleName)

    cell.styleName = Some("test")
    assert(Some("test") == cell.styleName)

  }

  test("columns") {
    assert(grid.columns.isEmpty)

    grid.columns = Seq("propertyId1", "propertyId2")
    Mockito.verify(spy).setColumnOrder("propertyId1", "propertyId2")

    assert(2 == grid.columns.size)
  }

  test("addColumn without type") {
    grid.addColumn("test")
    Mockito.verify(spy).addColumn("test")
  }

  test("addColumn with type") {
    grid.addColumn[String]("test")
    Mockito.verify(spy).addColumn("test", classOf[String])
  }

  test("removeAllColumns()") {
    grid.removeAllColumns()
    Mockito.verify(spy).removeAllColumns()
  }

  test("columnReorderingAllowed") {
    assert(!grid.columnReorderingAllowed)

    grid.columnReorderingAllowed = true
    Mockito.verify(spy).setColumnReorderingAllowed(true)

    assert(grid.columnReorderingAllowed)
  }

  test("removeColumn") {
    grid.addColumn("propertyId")

    grid.removeColumn("propertyId")
    Mockito.verify(spy).removeColumn("propertyId")
  }

  test("Column.propertyId") {
    val column = grid.addColumn[String]("myColumn")

    assert("myColumn" == column.propertyId)
  }

  test("Column.headerCaption") {
    val column = grid.addColumn[String]("myColumn")
    assert("My Column" == column.headerCaption)

    column.headerCaption = "Header for Column"
    assert("Header for Column" == column.headerCaption)
  }

  test("Column.hidingToggleCaption") {
    val column = grid.addColumn[String]("myColumn")
    assert(column.hidingToggleCaption.isEmpty)

    column.hidingToggleCaption = "My Caption"
    assert(Some("My Caption") == column.hidingToggleCaption)

    column.hidingToggleCaption = None
    assert(column.hidingToggleCaption.isEmpty)

    column.hidingToggleCaption = Some("My Caption 2")
    assert(Some("My Caption 2") == column.hidingToggleCaption)
  }

  test("Column.width") {
    val column = grid.addColumn[String]("myColumn")
    assert(-1 == column.width)

    column.width = 25.6
    assert(25.6 == column.width)

    column.width = None
    assert(-1 == column.width)

    column.width = Some(50.0)
    assert(50.0 == column.width)
  }

  test("Column.renderer") {
    val column = grid.addColumn[String]("myColumn")

    assert(column.renderer.isInstanceOf[TextRenderer])

    val textRenderer = new TextRenderer
    assert(textRenderer.p.isInstanceOf[com.vaadin.ui.renderers.TextRenderer])
    column.renderer = textRenderer

    assert(textRenderer == column.renderer)
  }

  test("Column.renderer (html)") {
    val column = grid.addColumn[String]("myColumn")

    val htmlRenderer = new HtmlRenderer
    assert(htmlRenderer.p.isInstanceOf[com.vaadin.ui.renderers.HtmlRenderer])

    column.renderer = htmlRenderer

    assert(htmlRenderer == column.renderer)
  }

  test("Column.renderer_=(Renderer, Converter)") {
    val column = grid.addColumn[Resource]("myColumn")

    val renderer = ImageRenderer()
    val converter = ResourceConverter()

    column.renderer = (renderer, converter)

    assert(renderer === column.renderer)
    assert(Some(converter) === column.converter)

  }

  test("Column.converter") {
    val column = grid.addColumn[java.lang.Boolean]("myColumn")

    assert(column.converter.isEmpty)

    val converter = new vaadin.scala.converter.StringToBooleanConverter
    column.converter = converter
    assert(Some(converter) == column.converter)
  }

  test("Column.sortable") {
    val column = grid.addColumn[String]("myColumn")

    assert(column.sortable)

    column.sortable = false
    assert(!column.sortable)
  }

  test("Column.expandRatio") {
    val column = grid.addColumn[String]("myColumn")

    assert(-1 == column.expandRatio)

    column.expandRatio = 2
    assert(2 == column.expandRatio)

    column.expandRatio = None
    assert(-1 == column.expandRatio)

    column.expandRatio = Some(1)
    assert(1 == column.expandRatio)
  }

  test("Column.minimumWidth") {
    val column = grid.addColumn[String]("myColumn")

    assert(10 == column.minimumWidth)

    column.minimumWidth = 5
    assert(5 == column.minimumWidth)
  }

  test("Column.maximumWidth") {
    val column = grid.addColumn[String]("myColumn")

    assert(-1 == column.maximumWidth)

    column.maximumWidth = 20
    assert(20 == column.maximumWidth)
  }

  test("Column.editable") {
    val column = grid.addColumn[String]("myColumn")

    assert(column.editable)

    column.editable = false
    assert(!column.editable)
  }

  // TODO: test for Column.editorField

  test("Column.hidden") {
    val column = grid.addColumn[String]("myColumn")

    assert(!column.hidden)

    column.hidden = true
    assert(column.hidden)
  }

  test("Column.hidable") {
    val column = grid.addColumn[String]("myColumn")

    assert(!column.hidable)

    column.hidable = true
    assert(column.hidable)
  }

  // TODO tests for Grid.Column class

  test("setColumnOrder") {
    grid.addColumn("propertyId1")
    grid.addColumn("propertyId2")

    grid.setColumnOrder(Seq("propertyId1", "propertyId2"))

    Mockito.verify(spy).setColumnOrder("propertyId1", "propertyId2")
  }

  test("frozenColumnCount") {
    grid.addColumn("col")

    assert(0 == grid.frozenColumnCount)

    grid.frozenColumnCount = 1
    assert(1 == grid.frozenColumnCount)
  }

  test("scrollTo") {
    val itemId = grid.addRow()

    grid.scrollTo(itemId)
    Mockito.verify(spy).scrollTo(itemId)
  }

  test("scrollTo w/ ScrollDestination") {
    val itemId = grid.addRow()

    grid.scrollTo(itemId, Grid.ScrollDestination.Middle)
    Mockito.verify(spy).scrollTo(itemId, com.vaadin.shared.ui.grid.ScrollDestination.MIDDLE)
  }

  test("scrollToStart") {
    grid.scrollToStart()

    Mockito.verify(spy).scrollToStart()
  }

  test("scrollToEnd") {
    grid.scrollToEnd()

    Mockito.verify(spy).scrollToEnd()
  }

  test("heightByRows") {
    assert(10.0 == grid.heightByRows)

    grid.heightByRows = 5.5
    assert(5.5 == grid.heightByRows)
  }

  test("heightMode") {
    assert(Grid.HeightMode.Css == grid.heightMode)

    grid.heightMode = HeightMode.Row
    assert(Grid.HeightMode.Row == grid.heightMode)
  }

  test("selectionMode / selectionModel") {
    assert(SelectionMode.Single == grid.selectionMode)
    assert(grid.selectionModel.isInstanceOf[Grid.SelectionModel.Single])

    grid.selectionMode = SelectionMode.None
    assert(SelectionMode.None == grid.selectionMode)
    assert(grid.selectionModel.isInstanceOf[Grid.SelectionModel.None])

    grid.selectionMode = SelectionMode.Multi
    assert(SelectionMode.Multi == grid.selectionMode)
    assert(grid.selectionModel.isInstanceOf[Grid.SelectionModel.Multi])

    grid.selectionMode = SelectionMode.Single
    assert(SelectionMode.Single == grid.selectionMode)
    assert(grid.selectionModel.isInstanceOf[Grid.SelectionModel.Single])

    grid.selectionMode = SelectionMode.MultiSimple
    assert(SelectionMode.Multi == grid.selectionMode)
    assert(grid.selectionModel.isInstanceOf[Grid.SelectionModel.Multi])
  }

  test("isSelected") {
    assert(!grid.isSelected("itemid"))

    Mockito.when(spy.isSelected("selectedItemId")).thenReturn(true)
    assert(grid.isSelected("selectedItemId"))
  }

  test("selectedRows") {
    assert(grid.selectedRows.isEmpty)

    val arrayList = new java.util.ArrayList[AnyRef];
    arrayList.add("itemId1")
    arrayList.add("itemId2")

    Mockito.when(spy.getSelectedRows).thenReturn(arrayList)

    assert(Seq("itemId1", "itemId2") == grid.selectedRows)
  }

  test("selectedRow") {
    grid.selectionMode = SelectionMode.Single

    assert(grid.selectedRow.isEmpty)

    Mockito.verify(spy).getSelectedRow
  }

  test("select") {
    val itemId = grid.addRow()

    assert(grid.select(itemId))
    Mockito.verify(spy).select(itemId)
  }

  test("deselect") {
    val itemId = grid.addRow()
    grid.select(itemId)

    assert(grid.deselect(itemId))

    Mockito.verify(spy).deselect(itemId)
  }

  test("selectionListeners") {
    grid.addColumn[String]("propertyId")
    val itemId = grid.addRow("value")
    var cnt = 0

    val selectionListener = { e: SelectionEvent =>
      cnt = cnt + 1
      assert(grid == e.component)
      assert(Seq(itemId) == e.added)
      assert(e.removed.isEmpty)
    }
    grid.selectionListeners += selectionListener

    grid.select(itemId)
    assert(1 == cnt)

    assert(1 == grid.selectionListeners.size)
    grid.selectionListeners -= selectionListener
    assert(grid.selectionListeners.isEmpty)
  }

  test("sortOrder") {
    import vaadin.scala.SortDirection._

    vaadinGrid.setContainerDataSource(new IndexedContainer().p)

    grid.container.addContainerProperty("propertyId1", classOf[String], None)
    grid.container.addContainerProperty("propertyId2", classOf[String], None)

    assert(grid.sortOrder.isEmpty)

    grid.sortOrder = Seq(("propertyId1", Ascending), ("propertyId2", Descending))

    assert(Seq(("propertyId1", Ascending), ("propertyId2", Descending)) == grid.sortOrder)

    val captor = ArgumentCaptor.forClass(classOf[java.util.List[SortOrder]])
    Mockito.verify(spy).setSortOrder(captor.capture())

    assert(SortDirection.ASCENDING == captor.getValue.get(0).getDirection)
    assert("propertyId1" == captor.getValue.get(0).getPropertyId)
    assert(SortDirection.DESCENDING == captor.getValue.get(1).getDirection)
    assert("propertyId2" == captor.getValue.get(1).getPropertyId)
  }

  test("sortOrder = Some") {
    import vaadin.scala.SortDirection._

    grid.container.addContainerProperty("propertyId1", classOf[String], None)
    grid.container.addContainerProperty("propertyId2", classOf[String], None)

    grid.sortOrder = Some(Seq(("propertyId1", Ascending), ("propertyId2", Descending)))

    assert(Seq(("propertyId1", Ascending), ("propertyId2", Descending)) == grid.sortOrder)

    val captor = ArgumentCaptor.forClass(classOf[java.util.List[SortOrder]])
    Mockito.verify(spy).setSortOrder(captor.capture())

    assert(SortDirection.ASCENDING == captor.getValue.get(0).getDirection)
    assert("propertyId1" == captor.getValue.get(0).getPropertyId)
    assert(SortDirection.DESCENDING == captor.getValue.get(1).getDirection)
    assert("propertyId2" == captor.getValue.get(1).getPropertyId)
  }

  test("sortOrder = None") {
    grid.sortOrder = None
    Mockito.verify(spy).clearSortOrder()
  }

  test("sortListeners") {
    grid.addColumn[String]("propertyId")
    var cnt = 0

    val sortListener = { e: SortEvent =>
      cnt = cnt + 1
      assert(grid == e.component)
      assert(Seq(("propertyId", vaadin.scala.SortDirection.Ascending)) == e.sortOrder)
      assert(!e.userOriginated)
    }
    grid.sortListeners += sortListener

    grid.sort("propertyId", vaadin.scala.SortDirection.Ascending)
    assert(1 == cnt)

    assert(1 == grid.sortListeners.size)
    grid.sortListeners -= sortListener
    assert(0 == grid.sortListeners.size)
  }

  test("sort, ascending") {
    grid.addColumn[String]("propertyId")

    grid.sort("propertyId", vaadin.scala.SortDirection.Ascending)
    Mockito.verify(spy).sort("propertyId", com.vaadin.shared.data.sort.SortDirection.ASCENDING)
  }

  test("sort, descending") {
    grid.addColumn[String]("propertyId")

    grid.sort("propertyId", vaadin.scala.SortDirection.Descending)
    Mockito.verify(spy).sort("propertyId", com.vaadin.shared.data.sort.SortDirection.DESCENDING)
  }

  test("getHeaderRow") {
    grid.appendHeaderRow()

    assert(null != grid.getHeaderRow(0))
  }

  test("addHeaderRowAt") {
    assert(null != grid.addHeaderRowAt(0))

    Mockito.verify(spy).addHeaderRowAt(0)
  }

  test("appendHeaderRow") {
    assert(null != grid.appendHeaderRow())

    Mockito.verify(spy).appendHeaderRow()
  }

  test("headerRowCount") {
    assert(1 == grid.headerRowCount)

    Mockito.verify(spy).getHeaderRowCount
  }

  test("prependHeaderRow") {
    assert(null != grid.prependHeaderRow())

    Mockito.verify(spy).prependHeaderRow()
  }

  test("removeHeaderRow") {
    grid.removeHeaderRow(0)

    Mockito.verify(spy).removeHeaderRow(0)
  }

  test("defaultHeaderRow") {
    assert(grid.defaultHeaderRow.isDefined)

    grid.defaultHeaderRow = None
    assert(grid.defaultHeaderRow.isEmpty)

    // TODO
  }

  test("headerVisible") {
    assert(grid.headerVisible)

    grid.headerVisible = false
    assert(!grid.headerVisible)
  }

  test("getFooterRow") {
    grid.appendFooterRow()

    assert(null != grid.getFooterRow(0))
  }

  test("addFooterRowAt") {
    assert(null != grid.addFooterRowAt(0))

    Mockito.verify(spy).addFooterRowAt(0)
  }

  test("appendFooterRow") {
    assert(null != grid.appendFooterRow())

    Mockito.verify(spy).appendFooterRow()
  }

  test("footerRowCount") {
    assert(0 == grid.footerRowCount)

    Mockito.verify(spy).getFooterRowCount
  }

  test("prependFooterRow") {
    assert(null != grid.prependFooterRow())

    Mockito.verify(spy).prependFooterRow()
  }

  test("removeFooterRow") {
    grid.prependFooterRow()

    grid.removeFooterRow(0)

    Mockito.verify(spy).removeFooterRow(0)
  }

  test("footerVisible") {
    assert(grid.footerVisible)

    grid.footerVisible = false
    assert(!grid.footerVisible)
  }

  test("rowStyleGenerator") {
    assert(grid.rowStyleGenerator.isEmpty)

    grid.rowStyleGenerator = { e => None }
    assert(grid.rowStyleGenerator.isDefined)

    grid.rowStyleGenerator = None
    assert(grid.rowStyleGenerator.isEmpty)

    grid.rowStyleGenerator = Some({ e: Grid.RowReference => None })
    assert(grid.rowStyleGenerator.isDefined)
  }

  test("rowStyleGenerator generates correct stylename") {
    grid.rowStyleGenerator = { e => Some("123" + e.itemId) }

    val rr = new RowReference(grid.p)
    rr.set("myItemId")
    assert("123myItemId" == grid.p.getRowStyleGenerator.getStyle(rr))
  }

  test("cellStyleGenerator") {
    assert(grid.cellStyleGenerator.isEmpty)

    grid.cellStyleGenerator = { e => None }
    assert(grid.cellStyleGenerator.isDefined)

    grid.cellStyleGenerator = None
    assert(grid.cellStyleGenerator.isEmpty)

    grid.cellStyleGenerator = Some({ e: Grid.CellReference => None })
    assert(grid.cellStyleGenerator.isDefined)
  }

  test("addRow") {
    grid.addColumn("col1")
    grid.addColumn("col2")

    grid.addRow("a", "b")

    Mockito.verify(spy).addRow("a", "b")
  }

  test("editorEnabled") {
    grid.editorEnabled

    Mockito.verify(spy).isEditorEnabled
  }

  test("editedItemId") {
    assert(grid.editedItemId.isEmpty)

    Mockito.when(spy.getEditedItemId()).thenReturn("itemId", Nil: _*)

    assert(Some("itemId") == grid.editedItemId)
  }

  test("editorFieldGroup") {
    assert(null != grid.editorFieldGroup)

    val fieldGroup = new FieldGroup

    grid.editorFieldGroup = fieldGroup
    assert(fieldGroup == grid.editorFieldGroup)
  }

  test("editorRowActive") {
    Mockito.reset(spy)

    grid.editorActive

    Mockito.verify(spy).isEditorActive
  }

  test("editItem") {
    val itemId = grid.addRow()
    grid.editorEnabled = true

    grid.editItem(itemId)
  }

  test("saveEditor") {
    grid.saveEditor()

    Mockito.verify(spy).saveEditor()
  }

  test("cancelEditorRow") {
    grid.cancelEditor()

    Mockito.verify(spy).cancelEditor()
  }

  test("editorFieldFactory") {
    assert(grid.editorFieldFactory.isInstanceOf[vaadin.scala.DefaultFieldGroupFieldFactory])

    val fieldFactory = new FieldGroupFieldFactory {
      override def createField[T <: Field[_]](dataType: Class[_], fieldType: Class[T]): Option[T] = ???
    }

    grid.editorFieldFactory = fieldFactory
    assert(fieldFactory == grid.editorFieldFactory)
  }

  test("editorSaveCaption") {
    assert("Save" == grid.editorSaveCaption)

    grid.editorSaveCaption = "Tallenna"
    assert("Tallenna" == grid.editorSaveCaption)
  }

  test("editorCancelCaption") {
    assert("Cancel" == grid.editorCancelCaption)

    grid.editorCancelCaption = "Peruuta"
    assert("Peruuta" == grid.editorCancelCaption)
  }

  test("recalculateColumnWidths") {
    grid.recalculateColumnWidths()

    Mockito.verify(spy).recalculateColumnWidths()
  }

  test("columnVisibilityChangeListeners") {
    val col = grid.addColumn[String]("propertyId")
    var cnt = 0

    grid.columnVisibilityChangeListeners += { e =>
      cnt = cnt + 1
      assert(grid == e.grid)
      assert(col.p == e.column.p)
      assert(e.hidden)
      assert(!e.userOriginated)
    }

    col.hidden = true

    assert(1 == cnt)
  }

  test("columnVisibilityChangeListeners, adding and removing a listener") {

    val listener = { e: Grid.ColumnVisibilityChangeEvent => }

    grid.columnVisibilityChangeListeners += listener
    assert(1 == grid.columnVisibilityChangeListeners.size)

    grid.columnVisibilityChangeListeners -= listener
    assert(0 == grid.columnVisibilityChangeListeners.size)
  }

  test("detailsGenerator") {
    assert(grid.detailsGenerator.isEmpty)

    grid.detailsGenerator = { e => None }
    assert(grid.detailsGenerator.isDefined)

    grid.detailsGenerator = None
    assert(grid.detailsGenerator.isEmpty)

    grid.detailsGenerator = Some({ e: Grid.RowReference => None })
    assert(grid.detailsGenerator.isDefined)
  }

  test("detailsGenerator generates correct details component") {
    val detailsLabel = Label("details label")

    grid.detailsGenerator = { e => Some(detailsLabel) }

    val rr = new RowReference(grid.p)
    rr.set("myItemId")
    assert(detailsLabel.p == grid.p.getDetailsGenerator.getDetails(rr))
  }

  test("isDetailsVisible") {
    assert(!grid.isDetailsVisible("myid"))

    Mockito.verify(spy).isDetailsVisible("myid")
  }

  test("setDetailsVisible") {
    grid.setDetailsVisible("myid", true)

    Mockito.verify(spy).setDetailsVisible("myid", true)
  }

}
