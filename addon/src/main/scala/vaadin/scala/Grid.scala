package vaadin.scala

import com.vaadin.data.sort.SortOrder
import vaadin.scala.internal._
import vaadin.scala.mixins._
import com.vaadin.ui.{ Grid => VaadinGrid }
import vaadin.scala.Grid._
import vaadin.scala.renderer.mixins.RendererMixin
import collection.JavaConverters._
import scala.reflect.ClassTag
import vaadin.scala.event.{ SortNotifier, SelectionNotifier }
import vaadin.scala.renderer.{ TextRenderer, Renderer }

package mixins {
  trait GridMixin extends AbstractComponentMixin { self: com.vaadin.ui.Grid => }
}

object Grid {

  class Header {

  }

  class Footer {
  }

  trait HeaderRow extends Wrapper {
    val p: VaadinGrid.HeaderRow
  }

  trait FooterRow extends Wrapper {
    override val p: VaadinGrid.FooterRow
  }

  object HeightMode extends Enumeration {
    import com.vaadin.shared.ui.grid.HeightMode._

    val Css = Value(CSS.ordinal)
    val Row = Value(ROW.ordinal)
  }

  object ScrollDestination extends Enumeration {
    import com.vaadin.shared.ui.grid.ScrollDestination._
    val Any = Value(ANY.ordinal)
    val Start = Value(START.ordinal)
    val Middle = Value(MIDDLE.ordinal)
    val End = Value(END.ordinal)
  }

  trait Column extends Wrapper { column =>

    val p: com.vaadin.ui.Grid.Column

    def propertyId: Any = p.getColumnProperty

    def headerCaption: String = p.getHeaderCaption
    def headerCaption_=(headerCaption: String): Unit = p.setHeaderCaption(headerCaption)

    def width: Double = p.getWidth
    def width_=(pixelWidth: Double): Unit = p.setWidth(pixelWidth)

    def widthUndefined() = p.setWidthUndefined()

    def setLastFrozenColumn(): Unit = p.setLastFrozenColumn()

    def renderer: Renderer[_] = p.getRenderer match {
      case r: RendererMixin => wrapperFor[Renderer[_]](r).get
      case tr: com.vaadin.ui.renderer.TextRenderer => {
        // Assuming that using the default TextRenderer,
        // setting a wrapped TextRenderer as a side-effect
        // Not really other place to do this.
        val r = new TextRenderer
        renderer = r
        r
      }
    }

    def renderer_=(renderer: Renderer[_]): Unit = p.setRenderer(renderer.p)

    // TODO: converter

    def sortable: Boolean = p.isSortable
    def sortable_=(sortable: Boolean): Unit = p.setSortable(sortable)

    def expandRatio: Int = p.getExpandRatio
    def expandRatio_=(expandRatio: Int): Unit = p.setExpandRatio(expandRatio)
    def expandRatio_=(expandRatio: Option[Int]): Unit = {
      if (expandRatio.isEmpty) p.clearExpandRatio()
      else p.setExpandRatio(expandRatio.get)
    }

    def minimumWidth: Double = p.getMinimumWidth
    def minimumWidth_=(minimumWidth: Double): Unit = p.setMinimumWidth(minimumWidth)

    def maximumWidth: Double = p.getMaximumWidth
    def maximumWidth_=(maximumWidth: Double): Unit = p.setMaximumWidth(maximumWidth)

  }

  case class RowReference(grid: Grid, itemId: Any) {
    def item: Item = grid.container.getItem(itemId)
  }

  case class CellReference(grid: Grid, itemId: Any, propertyId: Any) {
    def item: Item = grid.container.getItem(itemId)
    def property: Property[_] = item.getProperty(propertyId)
    def value: Option[Any] = property.value
  }

  sealed trait SelectionModel extends Wrapper {

    val p: com.vaadin.ui.Grid.SelectionModel

    def isSelected(itemId: Any): Boolean = p.isSelected(itemId)

    def selectedRows: Seq[Any] = p.getSelectedRows.toArray

    def reset(): Unit = p.reset()
  }

  object SelectionModel {
    trait Multi extends SelectionModel {

      val p: com.vaadin.ui.Grid.SelectionModel.Multi

      def select(itemIds: Any*): Boolean = p.select(itemIds)

      def deselect(itemIds: Any*): Boolean = p.deselect(itemIds)

      def selectAll(): Boolean = p.selectAll()

      def deselectAll(): Boolean = p.deselectAll()
    }

    trait Single extends SelectionModel {

      val p: com.vaadin.ui.Grid.SelectionModel.Single

      def select(itemId: Any): Boolean = p.select(itemId)

      def selectedRow: Option[Any] = Option(p.getSelectedRow)
    }

    trait None extends SelectionModel {
      val p: com.vaadin.ui.Grid.SelectionModel.None
    }
  }

  def apply(container: Container.Indexed): Grid = {
    new Grid(new VaadinGrid(container.p) with GridMixin)
  }
}

/**
 *
 * @author Henri Kerola / Vaadin
 */
class Grid(override val p: VaadinGrid with GridMixin)
    extends AbstractComponent(p) with SelectionNotifier with SortNotifier {

  def this() {
    this(new VaadinGrid(new IndexedContainer().p) with GridMixin)
    val f = classOf[VaadinGrid].getDeclaredField("defaultContainer")
    f.setAccessible(true)
    f.set(p, true)
  }

  private class CustomVaadinFieldGroup extends com.vaadin.data.fieldgroup.FieldGroup {
    protected override def getPropertyType(propertyId: AnyRef): Class[_] =
      if (getItemDataSource == null)
        return container.p.getType(propertyId)
      else
        return super.getPropertyType(propertyId)
  }
  editorFieldGroup = new FieldGroup(new CustomVaadinFieldGroup with FieldGroupMixin)

  def container: Container.Indexed = wrapperFor(p.getContainerDataSource).get
  def container_=(container: Container.Indexed) { p.setContainerDataSource(container.p) }

  private[this] def wrapColumn(vaadinColumn: com.vaadin.ui.Grid.Column) = new Column {
    override val p = vaadinColumn
  }

  def getColumn(propertyId: Any): Option[Column] = Option(p.getColumn(propertyId)).map(wrapColumn(_))

  def columns: Seq[Column] = p.getColumns.asScala.map(wrapColumn(_))

  def addColumn[TYPE](propertyId: Any)(implicit tag: ClassTag[TYPE]): Column =
    if (tag.runtimeClass == classOf[Nothing])
      wrapColumn(p.addColumn(propertyId))
    else
      wrapColumn(p.addColumn(propertyId, tag.runtimeClass))

  def removeAllColumns(): Unit = p.removeAllColumns()

  def removeColumn(propertyId: Any): Unit = p.removeColumn(propertyId)

  def setColumnOrder(propertyIds: Seq[Any]) = p.setColumnOrder(propertyIds.asInstanceOf[Seq[Object]]: _*)

  def frozenColumnCount: Int = p.getFrozenColumnCount
  def frozenColumnCount_=(frozenColumnCount: Int): Unit = p.setFrozenColumnCount(frozenColumnCount)

  def scrollTo(itemId: Any): Unit = p.scrollTo(itemId)

  def scrollTo(itemId: Any, destination: Grid.ScrollDestination.Value): Unit =
    p.scrollTo(itemId, com.vaadin.shared.ui.grid.ScrollDestination.values.apply(destination.id))

  def scrollToStart(): Unit = p.scrollToStart()

  def scrollToEnd(): Unit = p.scrollToEnd()

  def heightByRows: Double = p.getHeightByRows
  def heightByRows_=(heightByRows: Double): Unit = p.setHeightByRows(heightByRows)

  def heightMode: HeightMode.Value = Grid.HeightMode(p.getHeightMode.ordinal)
  def heightMode_=(heightMode: HeightMode.Value): Unit =
    p.setHeightMode(com.vaadin.shared.ui.grid.HeightMode.values.apply(heightMode.id))

  def selectionMode: SelectionMode.Value = p.getSelectionModel match {
    case n: VaadinGrid.SelectionModel.None => SelectionMode.None
    case s: VaadinGrid.SelectionModel.Single => SelectionMode.Single
    case m: VaadinGrid.SelectionModel.Multi => SelectionMode.Multi
  }

  def selectionMode_=(selectionMode: SelectionMode.Value): Unit = {
    val selectionModel = selectionMode match {
      case SelectionMode.None => VaadinGrid.SelectionMode.NONE
      case SelectionMode.Single => VaadinGrid.SelectionMode.SINGLE
      case SelectionMode.MultiSimple => VaadinGrid.SelectionMode.MULTI
      case SelectionMode.Multi => VaadinGrid.SelectionMode.MULTI
    }
    p.setSelectionMode(selectionModel)
  }

  def selectionModel: Grid.SelectionModel = p.getSelectionModel match {
    case n: VaadinGrid.NoSelectionModel => new SelectionModel.None { val p = n }
    case s: VaadinGrid.SingleSelectionModel => new SelectionModel.Single { val p = s }
    case m: VaadinGrid.MultiSelectionModel => new SelectionModel.Multi { val p = m }
  }

  def isSelected(itemId: Any): Boolean = p.isSelected(itemId)

  def selectedRows: Seq[Any] = p.getSelectedRows.toArray

  def selectedRow: Option[Any] = Option(p.getSelectedRow)

  def select(itemId: Any): Boolean = p.select(itemId)

  def deselect(itemId: Any): Boolean = p.deselect(itemId)

  def sortOrder: Seq[(Any, SortDirection.Value)] =
    p.getSortOrder.asScala map { so => (so.getPropertyId, SortDirection(so.getDirection.ordinal)) }

  def sortOrder_=(sortOrder: Seq[(Any, SortDirection.Value)]): Unit = {
    val so = sortOrder map { so => new SortOrder(so._1, com.vaadin.shared.data.sort.SortDirection.values.apply(so._2.id)) }
    p.setSortOrder(so.asJava)
  }

  def sortOrder_=(sortOrder: Option[Seq[(Any, SortDirection.Value)]]): Unit =
    if (sortOrder.isEmpty) p.clearSortOrder()
    else this.sortOrder = sortOrder.get

  def sort(propertyId: Any, direction: SortDirection.Value): Unit =
    p.sort(propertyId, com.vaadin.shared.data.sort.SortDirection.values.apply(direction.id))

  def removeHeaderRow(rowIndex: Int): Unit = {
    p.removeHeaderRow(rowIndex)
  }

  def defaultHeaderRow: Option[HeaderRow] = Option(p.getDefaultHeaderRow) map { vaadinHeaderRow =>
    new HeaderRow {
      override val p = vaadinHeaderRow
    }
  }
  def defaultHeaderRow_=(defaultHeaderRow: HeaderRow): Unit = p.setDefaultHeaderRow(defaultHeaderRow.p)
  def defaultHeaderRow_=(defaultHeaderRow: Option[HeaderRow]): Unit = p.setDefaultHeaderRow(peerFor(defaultHeaderRow))

  def headerVisible: Boolean = p.isHeaderVisible
  def headerVisible_=(headerVisible: Boolean): Unit = p.setHeaderVisible(headerVisible)

  def getFooterRow(rowIndex: Int): Grid.FooterRow = new Grid.FooterRow {
    override val p: VaadinGrid.FooterRow = Grid.this.p.getFooterRow(rowIndex)
  }

  def addFooterRowAt(index: Int): Grid.FooterRow = new Grid.FooterRow {
    override val p: VaadinGrid.FooterRow = Grid.this.p.addFooterRowAt(index)
  }

  def appendFooterRow(): Grid.FooterRow = new Grid.FooterRow {
    override val p: VaadinGrid.FooterRow = Grid.this.p.appendFooterRow
  }

  def footerRowCount: Int = p.getFooterRowCount

  def prependFooterRow(): Grid.FooterRow = new Grid.FooterRow {
    override val p: VaadinGrid.FooterRow = Grid.this.p.prependFooterRow
  }

  def removeFooterRow(row: Grid.FooterRow): Unit = p.removeFooterRow(row.p)

  def removeFooterRow(rowIndex: Int): Unit = p.removeFooterRow(rowIndex)

  def footerVisible: Boolean = p.isFooterVisible
  def footerVisible_=(footerVisible: Boolean): Unit = p.setFooterVisible(footerVisible)

  def rowStyleGenerator: Option[Grid.RowReference => Option[String]] = Option(p.getRowStyleGenerator) map {
    case (g: internal.GridRowStyleGenerator) => g.action
  }

  def rowStyleGenerator_=(generator: Grid.RowReference => Option[String]): Unit = {
    p.setRowStyleGenerator(new GridRowStyleGenerator(generator))
  }

  def rowStyleGenerator_=(generator: Option[Grid.RowReference => Option[String]]): Unit =
    p.setRowStyleGenerator(generator.map(new GridRowStyleGenerator(_)).orNull)

  def cellStyleGenerator: Option[Grid.CellReference => Option[String]] = Option(p.getCellStyleGenerator) map {
    case (g: internal.GridCellStyleGenerator) => g.action
  }

  def cellStyleGenerator_=(generator: Grid.CellReference => Option[String]): Unit = {
    p.setCellStyleGenerator(new GridCellStyleGenerator(generator))
  }

  def cellStyleGenerator_=(generator: Option[Grid.CellReference => Option[String]]): Unit =
    p.setCellStyleGenerator(generator.map(new GridCellStyleGenerator(_)).orNull)

  def addRow(values: Any*): Any = {
    p.addRow(values.asInstanceOf[Seq[Object]]: _*)
  }

  def editorEnabled: Boolean = p.isEditorEnabled
  def editorEnabled_=(editorRowEnabled: Boolean): Unit = p.setEditorEnabled(editorRowEnabled)

  def editedItemId: Option[Any] = Option(p.getEditedItemId)

  def editorFieldGroup: FieldGroup = wrapperFor(p.getEditorFieldGroup).get
  def editorFieldGroup_=(fieldGroup: FieldGroup): Unit = p.setEditorFieldGroup(fieldGroup.p)

  def editorActive: Boolean = p.isEditorActive

  def getEditorRowField(propertyId: Any): Field[_] = wrapperFor(p.getEditorField(propertyId)).get

  def editItem(itemId: Any): Unit = p.editItem(itemId)

  def setEditorField(propertyId: Any, field: Field[_]): Unit =
    p.setEditorField(propertyId, field.p)

  def saveEditor(): Unit = p.saveEditor()

  def cancelEditor(): Unit = p.cancelEditor()

}
