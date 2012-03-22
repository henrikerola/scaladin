package vaadin.scala

import com.vaadin.ui.AbstractSelect.MultiSelectMode._

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

  object SelectionMode extends Enumeration {
    val None, Single, Multi, MultiSimple = Value
  }
}

class Table(implicit wrapper: WrapperRegistry) extends AbstractSelect {

  override val p = new com.vaadin.ui.Table //with ValueChangeFunction with ItemClickListener with TableColumnGenerator {
  wr.put(this)

  def this(caption: String = null, width: Option[Measure] = None, height: Option[Measure] = None, dataSource: com.vaadin.data.Container = null, property: com.vaadin.data.Property = null, value: Any = null, selectable: Boolean = false, immediate: Boolean = false, style: String = null)(implicit wrapper: WrapperRegistry) = {
    this()
    this.caption = caption
    this.width = width
    this.height = height
    if (dataSource != null) p.setContainerDataSource(dataSource)
    if (property != null) p.setPropertyDataSource(property)
    this.value = value
    p.setSelectable(selectable)
    this.immediate = immediate
    p.setStyleName(style)
  }

  // VisibleColumns
  // getColumnHeaders()
  // getColumnIcons()
  // getColumnAlignments()
  // setColumnWidth(Object propertyId, int width)
  // setColumnExpandRatio(Object propertyId, float expandRatio) {
  // getColumnWidth(Object propertyId) {

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

  def selectionMode = {
    if (!p.isSelectable)
      Table.SelectionMode.None
    else if (p.isMultiSelect && p.getMultiSelectMode == SIMPLE)
      Table.SelectionMode.MultiSimple
    else if (p.isMultiSelect)
      Table.SelectionMode.Multi
    else
      Table.SelectionMode.Single
  }

  def selectionMode_=(selectionMode: Table.SelectionMode.Value) = selectionMode match {
    case Table.SelectionMode.None =>
      p.setSelectable(false)
    case Table.SelectionMode.Single =>
      p.setSelectable(true)
      p.setMultiSelect(false)
    case Table.SelectionMode.Multi =>
      p.setSelectable(true)
      p.setMultiSelect(true)
      p.setMultiSelectMode(DEFAULT)
    case Table.SelectionMode.MultiSimple =>
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

  def headerClickListeners = new ListenersTrait[HeaderClickEvent => Unit, HeaderClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Table.HeaderClickListener])
    override def addListener(elem: HeaderClickEvent => Unit) = p.addListener(new HeaderClickListener(elem))
    override def removeListener(elem: HeaderClickListener) = p.removeListener(elem)
  }

  def footerClickListeners = new ListenersTrait[FooterClickEvent => Unit, FooterClickListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Table.FooterClickListener])
    override def addListener(elem: FooterClickEvent => Unit) = p.addListener(new FooterClickListener(elem))
    override def removeListener(elem: FooterClickListener) = p.removeListener(elem)
  }

  def columnResizeListeners = new ListenersTrait[ColumnResizeEvent => Unit, ColumnResizeListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Table.ColumnReorderListener])
    override def addListener(elem: ColumnResizeEvent => Unit) = p.addListener(new ColumnResizeListener(elem))
    override def removeListener(elem: ColumnResizeListener) = p.removeListener(elem)
  }

  def columnReorderListeners = new ListenersTrait[ColumnReorderEvent => Unit, ColumnReorderListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Table.ColumnReorderListener])
    override def addListener(elem: ColumnReorderEvent => Unit) = p.addListener(new ColumnReorderListener(elem))
    override def removeListener(elem: ColumnReorderListener) = p.removeListener(elem)
  }

  // ItemDescriptionGenerator
  // RowGenerator

}

case class HeaderClickEvent(component: Component, propertyId: Any, button: Int, clientX: Int, clientY: Int, relativeX: Int, relativeY: Int, doubleClick: Boolean, altKey: Boolean, ctrlKey: Boolean, metaKey: Boolean, shiftKey: Boolean) extends ClickEvent(component, button, clientX, clientY, relativeX, relativeY, doubleClick, altKey, ctrlKey, metaKey, shiftKey)

class HeaderClickListener(val action: HeaderClickEvent => Unit)(implicit wrapper: WrapperRegistry) extends com.vaadin.ui.Table.HeaderClickListener with Listener {
  def headerClick(e: com.vaadin.ui.Table.HeaderClickEvent) = action(HeaderClickEvent(wrapper.get[Table](e.getComponent).get, e.getPropertyId, e.getButton, e.getClientX, e.getClientY, e.getRelativeX, e.getRelativeY, e.isDoubleClick, e.isAltKey, e.isCtrlKey, e.isMetaKey, e.isShiftKey))
}

case class FooterClickEvent(component: Component, propertyId: Any, button: Int, clientX: Int, clientY: Int, relativeX: Int, relativeY: Int, doubleClick: Boolean, altKey: Boolean, ctrlKey: Boolean, metaKey: Boolean, shiftKey: Boolean) extends ClickEvent(component, button, clientX, clientY, relativeX, relativeY, doubleClick, altKey, ctrlKey, metaKey, shiftKey)

class FooterClickListener(val action: FooterClickEvent => Unit)(implicit wrapper: WrapperRegistry) extends com.vaadin.ui.Table.FooterClickListener with Listener {
  def footerClick(e: com.vaadin.ui.Table.FooterClickEvent) = action(FooterClickEvent(wrapper.get[Table](e.getComponent).get, e.getPropertyId, e.getButton, e.getClientX, e.getClientY, e.getRelativeX, e.getRelativeY, e.isDoubleClick, e.isAltKey, e.isCtrlKey, e.isMetaKey, e.isShiftKey))
}

case class ColumnResizeEvent(component: Component, propertyId: Any, previousWidth: Int, currentWidth: Int) extends Event

class ColumnResizeListener(val action: ColumnResizeEvent => Unit)(implicit wrapper: WrapperRegistry) extends com.vaadin.ui.Table.ColumnResizeListener with Listener {
  def columnResize(e: com.vaadin.ui.Table.ColumnResizeEvent) = action(ColumnResizeEvent(wrapper.get[Table](e.getComponent).get, e.getPropertyId, e.getPreviousWidth, e.getCurrentWidth))
}

case class ColumnReorderEvent(component: Component) extends Event

class ColumnReorderListener(val action: ColumnReorderEvent => Unit)(implicit wrapper: WrapperRegistry) extends com.vaadin.ui.Table.ColumnReorderListener with Listener {
  def columnReorder(e: com.vaadin.ui.Table.ColumnReorderEvent) = action(ColumnReorderEvent(wrapper.get[Table](e.getComponent).get))
}