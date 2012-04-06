package vaadin.scala
import com.vaadin.ui.Table.ColumnGenerator

trait ValueChangeFunction extends com.vaadin.data.Property.ValueChangeNotifier {
  def addListener(action: com.vaadin.data.Property.ValueChangeEvent => Unit): Unit = addListener(new vaadin.scala.PropertyValueChangeListener(action))
}

class PropertyValueChangeListener(action: com.vaadin.data.Property.ValueChangeEvent => Unit)
    extends com.vaadin.data.Property.ValueChangeListener {
  override def valueChange(event: com.vaadin.data.Property.ValueChangeEvent) = action(event)
}

object ItemCaptionMode extends Enumeration {
  import com.vaadin.ui.AbstractSelect._
  val Id = Value(ITEM_CAPTION_MODE_ID)
  val Item = Value(ITEM_CAPTION_MODE_ITEM)
  val Index = Value(ITEM_CAPTION_MODE_INDEX)
  val ExplicitDefaultsId = Value(ITEM_CAPTION_MODE_EXPLICIT_DEFAULTS_ID)
  val Explicit = Value(ITEM_CAPTION_MODE_EXPLICIT)
  val IconOnly = Value(ITEM_CAPTION_MODE_ICON_ONLY)
  val Property = Value(ITEM_CAPTION_MODE_PROPERTY)
}

trait MultiSelectable extends AbstractSelect {

  def multiSelect = p.isMultiSelect
  def multiSelect_=(multiSelect: Boolean) = p.setMultiSelect(multiSelect)
}

trait TableColumnGenerator extends com.vaadin.ui.Table {
  def addGeneratedColumn(id: Any, generate: (com.vaadin.ui.Table, AnyRef, AnyRef) => AnyRef): Unit = {
    addGeneratedColumn(id, new ColumnGenerator {
      def generateCell(table: com.vaadin.ui.Table, itemId: AnyRef,
                       columnId: AnyRef): AnyRef = {
        generate(table, itemId, columnId)
      }
    })
  }
}

class TreeTable(caption: String = null, width: Option[Measure] = None, height: Option[Measure] = None, dataSource: com.vaadin.data.Container = null, property: com.vaadin.data.Property = null, value: Any = null, selectable: Boolean = false, immediate: Boolean = false, style: String = null)
    extends com.vaadin.ui.TreeTable(caption) with ValueChangeFunction with TableColumnGenerator {
  setWidth(if (width.isDefined) width.get.toString else null)
  setHeight(if (height.isDefined) height.get.toString else null)
  if (dataSource != null) setContainerDataSource(dataSource)
  if (property != null) setPropertyDataSource(property)
  if (value != null) setValue(value)
  setSelectable(selectable)
  setImmediate(immediate)
  setStyleName(style)
}

class Tree(caption: String = null, width: Option[Measure] = None, height: Option[Measure] = None, dataSource: com.vaadin.data.Container = null, property: com.vaadin.data.Property = null, value: Any = null, selectable: Boolean = false, nullSelectionAllowed: Boolean = true, immediate: Boolean = false, style: String = null)
    extends com.vaadin.ui.Tree with ValueChangeFunction {
  setWidth(if (width.isDefined) width.get.toString else null)
  setHeight(if (height.isDefined) height.get.toString else null)
  if (dataSource != null) setContainerDataSource(dataSource)
  if (property != null) setPropertyDataSource(property)
  if (value != null) setValue(value)
  setSelectable(selectable)
  setNullSelectionAllowed(nullSelectionAllowed)
  setImmediate(immediate)
  setStyleName(style)
}