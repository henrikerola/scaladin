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