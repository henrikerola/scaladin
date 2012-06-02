package vaadin.scala

import com.vaadin.ui.AbstractSelect.MultiSelectMode._
import vaadin.scala.mixins.TableMixin
import vaadin.scala.mixins.ContainerOrderedMixin
import vaadin.scala.mixins.ContainerSortableMixin

package mixins {
  trait TableMixin extends AbstractSelectMixin with ContainerOrderedMixin with ContainerSortableMixin
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
}

class Table(override val p: com.vaadin.ui.Table with TableMixin = new com.vaadin.ui.Table with TableMixin)
  extends AbstractSelect(p) with ContainerOrdered with ContainerSortable with ItemDescriptionGeneratorOwner {

  def visibleColumns: Seq[Any] = p.getVisibleColumns
  def visibleColumns_=(visibleColumns: Seq[Any]) = p.setVisibleColumns(visibleColumns map { _.asInstanceOf[Object] } toArray)

  def columnHeaders: Seq[Option[String]] = p.getColumnHeaders map {
    case null => None
    case header => Some(header)
  }
  def columnHeaders_=(columnHeaders: Seq[Option[String]]) = p.setColumnHeaders(columnHeaders map {
    case None => null
    case Some(header) => header
  } toArray)

  def columnIcons: Seq[Option[Resource]] = p.getColumnIcons map { wrapperFor[Resource](_) }
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

  def currentPageFirstItemId: Any = p.getCurrentPageFirstItemId
  def currentPageFirstItemId(currentPageFirstItemId: Any) = p.setCurrentPageFirstItemId(currentPageFirstItemId)

  def columnCollapsingAllowed = p.isColumnCollapsingAllowed
  def columnCollapsingAllowed_=(columnCollapsingAllowed: Boolean) = p.setColumnCollapsingAllowed(columnCollapsingAllowed)

  def columnReorderingAllowed = p.isColumnReorderingAllowed
  def columnReorderingAllowed_=(columnReorderingAllowed: Boolean) = p.setColumnReorderingAllowed(columnReorderingAllowed)

  def editable = p.isEditable;
  def editable_=(editable: Boolean) = p.setEditable(editable)

  def sortable = !p.isSortDisabled
  def sortable_=(sortable: Boolean) = p.setSortDisabled(!sortable)

  // TODO: sortContainerPropertyId: Any = p.getSortContainerPropertyId 
  // TODO: setSortContainerPropertyId

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

  lazy val itemClickListeners = new ListenersTrait[ItemClickEvent, ItemClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.event.ItemClickEvent.ItemClickListener])
    override def addListener(elem: ItemClickEvent => Unit) = p.addListener(new ItemClickListener(elem))
    override def removeListener(elem: ItemClickListener) = p.removeListener(elem)
  }

  lazy val headerClickListeners = new ListenersTrait[HeaderClickEvent, HeaderClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Table.HeaderClickListener])
    override def addListener(elem: HeaderClickEvent => Unit) = p.addListener(new HeaderClickListener(elem))
    override def removeListener(elem: HeaderClickListener) = p.removeListener(elem)
  }

  lazy val footerClickListeners = new ListenersTrait[FooterClickEvent, FooterClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Table.FooterClickListener])
    override def addListener(elem: FooterClickEvent => Unit) = p.addListener(new FooterClickListener(elem))
    override def removeListener(elem: FooterClickListener) = p.removeListener(elem)
  }

  lazy val columnResizeListeners = new ListenersTrait[ColumnResizeEvent, ColumnResizeListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Table.ColumnReorderListener])
    override def addListener(elem: ColumnResizeEvent => Unit) = p.addListener(new ColumnResizeListener(elem))
    override def removeListener(elem: ColumnResizeListener) = p.removeListener(elem)
  }

  lazy val columnReorderListeners = new ListenersTrait[ColumnReorderEvent, ColumnReorderListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Table.ColumnReorderListener])
    override def addListener(elem: ColumnReorderEvent => Unit) = p.addListener(new ColumnReorderListener(elem))
    override def removeListener(elem: ColumnReorderListener) = p.removeListener(elem)
  }

  private class CellStyleGenerator(val generator: (Any, Any) => String) extends com.vaadin.ui.Table.CellStyleGenerator {
    def getStyle(itemId: Any, propertyId: Any) = generator(itemId, propertyId)

  }

  def cellStyleGenerator: Option[(Any, Any) => String] = p.getCellStyleGenerator match {
    case null => None
    case generator: CellStyleGenerator => Some(generator.generator)
  }

  def cellStyleGenerator_=(generator: (Any, Any) => String): Unit = {
    p.setCellStyleGenerator(new CellStyleGenerator(generator))
  }

  def cellStyleGenerator_=(generator: Option[(Any, Any) => String]): Unit = generator match {
    case None => p.setCellStyleGenerator(null)
    case Some(generator) => cellStyleGenerator = generator
  }

  // RowGenerator

}

case class ItemClickEvent(component: Component, item: com.vaadin.data.Item, itemId: Any, propertyId: Any, button: Int, clientX: Int, clientY: Int, relativeX: Int, relativeY: Int, doubleClick: Boolean, altKey: Boolean, ctrlKey: Boolean, metaKey: Boolean, shiftKey: Boolean) extends AbstractClickEvent(component, button, clientX, clientY, relativeX, relativeY, doubleClick, altKey, ctrlKey, metaKey, shiftKey)

class ItemClickListener(val action: ItemClickEvent => Unit) extends com.vaadin.event.ItemClickEvent.ItemClickListener with Listener {
  def itemClick(e: com.vaadin.event.ItemClickEvent) = action(ItemClickEvent(wrapperFor[Table](e.getComponent).get, e.getItem(), e.getItemId(), e.getPropertyId, e.getButton, e.getClientX, e.getClientY, e.getRelativeX, e.getRelativeY, e.isDoubleClick, e.isAltKey, e.isCtrlKey, e.isMetaKey, e.isShiftKey))
}

case class HeaderClickEvent(component: Component, propertyId: Any, button: Int, clientX: Int, clientY: Int, relativeX: Int, relativeY: Int, doubleClick: Boolean, altKey: Boolean, ctrlKey: Boolean, metaKey: Boolean, shiftKey: Boolean) extends AbstractClickEvent(component, button, clientX, clientY, relativeX, relativeY, doubleClick, altKey, ctrlKey, metaKey, shiftKey)

class HeaderClickListener(val action: HeaderClickEvent => Unit) extends com.vaadin.ui.Table.HeaderClickListener with Listener {
  def headerClick(e: com.vaadin.ui.Table.HeaderClickEvent) = action(HeaderClickEvent(wrapperFor[Table](e.getComponent).get, e.getPropertyId, e.getButton, e.getClientX, e.getClientY, e.getRelativeX, e.getRelativeY, e.isDoubleClick, e.isAltKey, e.isCtrlKey, e.isMetaKey, e.isShiftKey))
}

case class FooterClickEvent(component: Component, propertyId: Any, button: Int, clientX: Int, clientY: Int, relativeX: Int, relativeY: Int, doubleClick: Boolean, altKey: Boolean, ctrlKey: Boolean, metaKey: Boolean, shiftKey: Boolean) extends AbstractClickEvent(component, button, clientX, clientY, relativeX, relativeY, doubleClick, altKey, ctrlKey, metaKey, shiftKey)

class FooterClickListener(val action: FooterClickEvent => Unit) extends com.vaadin.ui.Table.FooterClickListener with Listener {
  def footerClick(e: com.vaadin.ui.Table.FooterClickEvent) = action(FooterClickEvent(wrapperFor[Table](e.getComponent).get, e.getPropertyId, e.getButton, e.getClientX, e.getClientY, e.getRelativeX, e.getRelativeY, e.isDoubleClick, e.isAltKey, e.isCtrlKey, e.isMetaKey, e.isShiftKey))
}

case class ColumnResizeEvent(component: Component, propertyId: Any, previousWidth: Int, currentWidth: Int) extends Event

class ColumnResizeListener(val action: ColumnResizeEvent => Unit) extends com.vaadin.ui.Table.ColumnResizeListener with Listener {
  def columnResize(e: com.vaadin.ui.Table.ColumnResizeEvent) = action(ColumnResizeEvent(wrapperFor[Table](e.getComponent).get, e.getPropertyId, e.getPreviousWidth, e.getCurrentWidth))
}

case class ColumnReorderEvent(component: Component) extends Event

class ColumnReorderListener(val action: ColumnReorderEvent => Unit) extends com.vaadin.ui.Table.ColumnReorderListener with Listener {
  def columnReorder(e: com.vaadin.ui.Table.ColumnReorderEvent) = action(ColumnReorderEvent(wrapperFor[Table](e.getComponent).get))
}