package vaadin.scala

object Table {
  object ColumnHeaderMode extends Enumeration {
    import com.vaadin.ui.Table._
    val hidden = Value(COLUMN_HEADER_MODE_HIDDEN)
    val id = Value(COLUMN_HEADER_MODE_ID)
    val explicit = Value(COLUMN_HEADER_MODE_EXPLICIT)
    val explicitDefaultsId = Value(COLUMN_HEADER_MODE_EXPLICIT_DEFAULTS_ID)
  }
  
  object RowHeaderMode extends Enumeration {
    import com.vaadin.ui.Table._
    val hidden = Value(ROW_HEADER_MODE_HIDDEN)
    val id = Value(ROW_HEADER_MODE_ID)
    val item = Value(ROW_HEADER_MODE_ITEM)
    val index = Value(ROW_HEADER_MODE_INDEX)
    val explicit = Value(ROW_HEADER_MODE_EXPLICIT)
    val property = Value(ROW_HEADER_MODE_PROPERTY)
    val iconOnly = Value(ROW_HEADER_MODE_ICON_ONLY)
    val explicitDefaultsId = Value(ROW_HEADER_MODE_EXPLICIT_DEFAULTS_ID)
  }
  
  object SelectionMode extends Enumeration {
    import com.vaadin.ui.AbstractSelect.MultiSelectMode._
    val disabled = Value
    val single = Value
    val multi = Value(DEFAULT.ordinal)
    val multiSimple = Value(SIMPLE.ordinal)
  }
}

class Table extends AbstractSelect {

  override val p = new com.vaadin.ui.Table //with ValueChangeFunction with ItemClickListener with TableColumnGenerator {
  WrapperRegistry.put(this)

  def this(caption: String = null, width: Option[Measure] = None, height: Option[Measure] = None, dataSource: com.vaadin.data.Container = null, property: com.vaadin.data.Property = null, value: Any = null, selectable: Boolean = false, immediate: Boolean = false, style: String = null) = {
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

  // selectable
  
  def columnHeaderMode = Table.ColumnHeaderMode(p.getColumnHeaderMode)
  def columnHeaderMode_=(columnHeaderMode: Table.ColumnHeaderMode.Value) = p.setColumnHeaderMode(columnHeaderMode.id)
  
  def rowHeaderMode = Table.RowHeaderMode(p.getRowHeaderMode)
  def rowHeaderMode_=(rowHeaderMode: Table.RowHeaderMode.Value) = p.setRowHeaderMode(rowHeaderMode.id)
  
  def refreshRowCache() = p.refreshRowCache()
  
  def footerVisible = p.isFooterVisible
  def footerVisible_=(footerVisible: Boolean) = p.setFooterVisible(footerVisible)
  
  // ItemDescriptionGenerator
  // RowGenerator

}