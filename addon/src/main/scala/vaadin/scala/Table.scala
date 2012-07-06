package vaadin.scala

import com.vaadin.ui.AbstractSelect.MultiSelectMode._
import vaadin.scala.mixins.TableMixin
import vaadin.scala.mixins.ContainerOrderedMixin
import vaadin.scala.mixins.ContainerSortableMixin
import vaadin.scala.internal.HeaderClickListener
import vaadin.scala.internal.FooterClickListener
import vaadin.scala.internal.ColumnReorderListener
import vaadin.scala.internal.ColumnResizeListener
import vaadin.scala.internal.TableColumnGenerator
import vaadin.scala.internal.CellStyleGenerator
import vaadin.scala.internal.WrapperUtil

package mixins {
  trait TableMixin extends AbstractSelectMixin with ContainerOrderedMixin with ContainerSortableMixin { self: com.vaadin.ui.Table =>
    override protected def formatPropertyValue(rowId: Any, colId: Any, property: com.vaadin.data.Property): String = wrapper.asInstanceOf[Table].propertyValueFormatter match {
      case Some(formatter) => formatter(Table.FormatPropertyEvent(WrapperUtil.wrapperFor[Table](this).get, rowId, colId)).getOrElse(null)
      case None => self.formatPropertyValue(rowId, colId, property)
    }
  }
}

object Table {
  object ColumnHeaderMode extends Enumeration {
    import com.vaadin.ui.Table._
    val Hidden = Value(COLUMN_HEADER_MODE_HIDDEN)
    val Id = Value(COLUMN_HEADER_MODE_ID)
    val Explicit = Value(COLUMN_HEADER_MODE_EXPLICIT)
    val ExplicitDefaultsId = Value(COLUMN_HEADER_MODE_EXPLICIT_DEFAULTS_ID)
  }

  object RowHeaderMode extends Enumeration {
    import com.vaadin.ui.Table._
    val Hidden = Value(ROW_HEADER_MODE_HIDDEN)
    val Id = Value(ROW_HEADER_MODE_ID)
    val Item = Value(ROW_HEADER_MODE_ITEM)
    val Index = Value(ROW_HEADER_MODE_INDEX)
    val Explicit = Value(ROW_HEADER_MODE_EXPLICIT)
    val Property = Value(ROW_HEADER_MODE_PROPERTY)
    val IconOnly = Value(ROW_HEADER_MODE_ICON_ONLY)
    val ExplicitDefaultsId = Value(ROW_HEADER_MODE_EXPLICIT_DEFAULTS_ID)
  }

  object ColumnAlignment extends Enumeration {
    import com.vaadin.ui.Table._
    val Left = Value(ALIGN_LEFT)
    val Center = Value(ALIGN_CENTER)
    val Right = Value(ALIGN_RIGHT)
  }

  case class HeaderClickEvent(component: Component, propertyId: Any, button: Int, clientX: Int, clientY: Int, relativeX: Int, relativeY: Int, doubleClick: Boolean, altKey: Boolean, ctrlKey: Boolean, metaKey: Boolean, shiftKey: Boolean) extends AbstractClickEvent(component, button, clientX, clientY, relativeX, relativeY, doubleClick, altKey, ctrlKey, metaKey, shiftKey)
  case class FooterClickEvent(component: Component, propertyId: Any, button: Int, clientX: Int, clientY: Int, relativeX: Int, relativeY: Int, doubleClick: Boolean, altKey: Boolean, ctrlKey: Boolean, metaKey: Boolean, shiftKey: Boolean) extends AbstractClickEvent(component, button, clientX, clientY, relativeX, relativeY, doubleClick, altKey, ctrlKey, metaKey, shiftKey)
  case class ColumnResizeEvent(component: Component, propertyId: Any, previousWidth: Int, currentWidth: Int) extends Event
  case class ColumnReorderEvent(component: Component) extends Event
  case class ColumnGenerationEvent(table: Table, itemId: Any, propertyId: Any) extends Event
  case class CellStyleGenerationEvent(itemId: Any, propertyId: Any) extends Event
  case class FormatPropertyEvent(table: Table, itemId: Any, propertyId: Any) extends Event

}

/**
 * @see com.vaadin.ui.Table
 * @author Henri Kerola / Vaadin
 */
class Table(override val p: com.vaadin.ui.Table with TableMixin = new com.vaadin.ui.Table with TableMixin)
  extends AbstractSelect(p) with ContainerOrdered with ContainerSortable with ItemDescriptionGeneratorOwner with ItemClickNotifier {

  def visibleColumns: Seq[Any] = p.getVisibleColumns
  def visibleColumns_=(visibleColumns: Seq[Any]) = p.setVisibleColumns(visibleColumns map { _.asInstanceOf[Object] } toArray)

  def columnHeaders: Seq[Option[String]] = p.getColumnHeaders map {
    case null => None
    case header => Some(header)
  }
  def columnHeaders_=(columnHeaders: => Seq[String]) = p.setColumnHeaders(columnHeaders toArray)
  def columnHeaders_=(columnHeaders: Seq[Option[String]]) = p.setColumnHeaders(columnHeaders map {
    case None => null
    case Some(header) => header
  } toArray)

  def columnIcons: Seq[Option[Resource]] = p.getColumnIcons map { wrapperFor[Resource](_) }
  def columnIcons_=(columnIcons: => Seq[Resource]) = p.setColumnIcons(columnIcons map { _.p } toArray)
  def columnIcons_=(columnIcons: Seq[Option[Resource]]) = p.setColumnIcons(columnIcons map {
    case None => null
    case Some(icon) => icon.p
  } toArray)

  def columnAlignments: Seq[Table.ColumnAlignment.Value] = p.getColumnAlignments map { Table.ColumnAlignment.withName(_) }
  def columnAlignments_=(columnAlignments: Seq[Table.ColumnAlignment.Value]) = p.setColumnAlignments(columnAlignments map { _.toString } toArray)

  def columnExpandRatio(propertyId: Any) = p.getColumnExpandRatio(propertyId)
  def columnExpandRatio(propertyId: Any, ratio: Float) = p.setColumnExpandRatio(propertyId, ratio)

  def columnWidth(propertyId: Any) = p.getColumnWidth(propertyId)
  def columnWidth(propertyId: Any, width: Int) = p.setColumnWidth(propertyId, width)

  def columnIcon(propertyId: Any) = wrapperFor[Resource](p.getColumnIcon(propertyId))
  def columnIcon(propertyId: Any, icon: Option[Resource]) = p.setColumnIcon(propertyId, if (icon.isDefined) icon.get.p else null)
  def columnIcon(propertyId: Any, icon: Resource) = p.setColumnIcon(propertyId, icon.p)

  def columnHeader(propertyId: Any) = Option(p.getColumnHeader(propertyId))
  def columnHeader(propertyId: Any, header: Option[String]) = p.setColumnHeader(propertyId, header.getOrElse(null))
  def columnHeader(propertyId: Any, header: String) = p.setColumnHeader(propertyId, header)

  def columnFooter(propertyId: Any) = Option(p.getColumnFooter(propertyId))
  def columnFooter(propertyId: Any, footer: Option[String]) = p.setColumnFooter(propertyId, footer.getOrElse(null))
  def columnFooter(propertyId: Any, footer: String) = p.setColumnFooter(propertyId, footer)

  def columnAlignment(propertyId: Any) = Table.ColumnAlignment.withName(p.getColumnAlignment(propertyId))
  def columnAlignment(propertyId: Any, alignment: Table.ColumnAlignment.Value) = p.setColumnAlignment(propertyId, alignment.toString)

  def pageLength = p.getPageLength
  def pageLength_=(pageLength: Int) = p.setPageLength(pageLength)

  def cacheRate = p.getCacheRate
  def cacheRate_=(cacheRate: Double) = p.setCacheRate(cacheRate)

  def currentPageFirstItemIndex: Int = p.getCurrentPageFirstItemIndex
  def currentPageFirstItemIndex_=(currentPageFirstItemIndex: Int) = p.setCurrentPageFirstItemIndex(currentPageFirstItemIndex)

  def currentPageFirstItemId: Option[Any] = Option(p.getCurrentPageFirstItemId)
  def currentPageFirstItemId_=(currentPageFirstItemId: Any) = p.setCurrentPageFirstItemId(currentPageFirstItemId)

  def columnCollapsingAllowed = p.isColumnCollapsingAllowed
  def columnCollapsingAllowed_=(columnCollapsingAllowed: Boolean) = p.setColumnCollapsingAllowed(columnCollapsingAllowed)

  def columnCollapsible(propertyId: Any): Boolean = p.isColumnCollapsible(propertyId)
  def columnCollapsible(propertyId: Any, collabsible: Boolean) = p.setColumnCollapsible(propertyId, collabsible)

  def columnReorderingAllowed = p.isColumnReorderingAllowed
  def columnReorderingAllowed_=(columnReorderingAllowed: Boolean) = p.setColumnReorderingAllowed(columnReorderingAllowed)

  def editable = p.isEditable;
  def editable_=(editable: Boolean) = p.setEditable(editable)

  def sortable = !p.isSortDisabled
  def sortable_=(sortable: Boolean) = p.setSortDisabled(!sortable)

  def sortContainerPropertyId: Option[Any] = Option(p.getSortContainerPropertyId)
  def sortContainerPropertyId_=(sortContainerPropertyId: Option[Any]) = p.setSortContainerPropertyId(sortContainerPropertyId.getOrElse(null))
  def sortContainerPropertyId_=(sortContainerPropertyId: Any) = p.setSortContainerPropertyId(sortContainerPropertyId)

  def sortAscending = p.isSortAscending
  def sortAscending_=(sortAscending: Boolean) = p.setSortAscending(true)

  def selectionMode = {
    if (!p.isSelectable)
      SelectionMode.None
    else if (p.isMultiSelect && p.getMultiSelectMode == SIMPLE)
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
      p.setMultiSelectMode(DEFAULT)
    case SelectionMode.MultiSimple =>
      p.setSelectable(true)
      p.setMultiSelect(true)
      p.setMultiSelectMode(SIMPLE)
  }

  def columnHeaderMode = Table.ColumnHeaderMode(p.getColumnHeaderMode)
  def columnHeaderMode_=(columnHeaderMode: Table.ColumnHeaderMode.Value) = p.setColumnHeaderMode(columnHeaderMode.id)

  def rowHeaderMode = Table.RowHeaderMode(p.getRowHeaderMode)
  def rowHeaderMode_=(rowHeaderMode: Table.RowHeaderMode.Value) = p.setRowHeaderMode(rowHeaderMode.id)

  def refreshRowCache() = p.refreshRowCache()

  def footerVisible = p.isFooterVisible
  def footerVisible_=(footerVisible: Boolean) = p.setFooterVisible(footerVisible)

  def tableFieldFactory: Option[TableFieldFactory] = wrapperFor[TableFieldFactory](p.getTableFieldFactory)
  def tableFieldFactory_=(factory: TableFieldFactory) = p.setTableFieldFactory(factory.p)
  def tableFieldFactory_=(factoryFunction: (TableFieldIngredients) => Option[Field]) = p.setTableFieldFactory(TableFieldFactory(factoryFunction).p)
  def tableFieldFactory_=(factory: Option[TableFieldFactory]) = factory match {
    case Some(factory) => p.setTableFieldFactory(factory.p)
    case None => p.setTableFieldFactory(null)
  }

  var propertyValueFormatter: Option[Table.FormatPropertyEvent => Option[String]] = None
  def propertyValueFormatter_=(propertyValueFormatter: Table.FormatPropertyEvent => Option[String]): Unit = this.propertyValueFormatter = Some(propertyValueFormatter)

  lazy val headerClickListeners = new ListenersTrait[Table.HeaderClickEvent, HeaderClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Table.HeaderClickListener])
    override def addListener(elem: Table.HeaderClickEvent => Unit) = p.addListener(new HeaderClickListener(elem))
    override def removeListener(elem: HeaderClickListener) = p.removeListener(elem)
  }

  lazy val footerClickListeners = new ListenersTrait[Table.FooterClickEvent, FooterClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Table.FooterClickListener])
    override def addListener(elem: Table.FooterClickEvent => Unit) = p.addListener(new FooterClickListener(elem))
    override def removeListener(elem: FooterClickListener) = p.removeListener(elem)
  }

  lazy val columnResizeListeners = new ListenersTrait[Table.ColumnResizeEvent, ColumnResizeListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Table.ColumnReorderListener])
    override def addListener(elem: Table.ColumnResizeEvent => Unit) = p.addListener(new ColumnResizeListener(elem))
    override def removeListener(elem: ColumnResizeListener) = p.removeListener(elem)
  }

  lazy val columnReorderListeners = new ListenersTrait[Table.ColumnReorderEvent, ColumnReorderListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Table.ColumnReorderListener])
    override def addListener(elem: Table.ColumnReorderEvent => Unit) = p.addListener(new ColumnReorderListener(elem))
    override def removeListener(elem: ColumnReorderListener) = p.removeListener(elem)
  }

  def cellStyleGenerator: Option[Table.CellStyleGenerationEvent => Option[String]] = p.getCellStyleGenerator match {
    case null => None
    case generator: CellStyleGenerator => Some(generator.generator)
  }

  def cellStyleGenerator_=(generator: Table.CellStyleGenerationEvent => Option[String]): Unit = {
    p.setCellStyleGenerator(new CellStyleGenerator(generator))
  }

  def cellStyleGenerator_=(generator: Option[Table.CellStyleGenerationEvent => Option[String]]): Unit = generator match {
    case None => p.setCellStyleGenerator(null)
    case Some(generator) => cellStyleGenerator = generator
  }

  // RowGenerator

}