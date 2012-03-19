package vaadin.scala
import com.vaadin.ui.Table.ColumnGenerator

trait ValueChangeFunction extends com.vaadin.data.Property.ValueChangeNotifier {
  def addListener(action: com.vaadin.data.Property.ValueChangeEvent => Unit): Unit = addListener(new vaadin.scala.PropertyValueChangeListener(action))
}

class PropertyValueChangeListener(action: com.vaadin.data.Property.ValueChangeEvent => Unit)
  extends com.vaadin.data.Property.ValueChangeListener {
  override def valueChange(event: com.vaadin.data.Property.ValueChangeEvent) = action(event)
}

trait AbstractSelect extends AbstractField {

}

trait Select extends AbstractSelect {

}

class NativeSelect(caption: String = null, width: Option[Measure] = None, height: Option[Measure] = None, value: Any = null, style: String = null, nullSelectionAllowed: Boolean = true)
  extends com.vaadin.ui.NativeSelect(caption) with ValueChangeFunction {
  setWidth(if (width.isDefined) width.get.toString else null)
  setHeight(if (height.isDefined) height.get.toString else null)
  if (value != null) setValue(value)
  setStyleName(style)
  setNullSelectionAllowed(nullSelectionAllowed)
}

class ComboBox(caption: String = null, width: Option[Measure] = None, height: Option[Measure] = None, dataSource: com.vaadin.data.Container = null, value: Any = null, style: String = null, prompt: String = null, nullSelectionAllowed: Boolean = true)
  extends Select /*with ValueChangeFunction*/ {

  override val p = new com.vaadin.ui.ComboBox(caption);
  WrapperRegistry.put(this)

  p.setWidth(if (width.isDefined) width.get.toString else null)
  p.setHeight(if (height.isDefined) height.get.toString else null)
  if (dataSource != null) p.setContainerDataSource(dataSource)
  if (value != null) p.setValue(value)
  p.setStyleName(style)
  p.setInputPrompt(prompt)
  p.setNullSelectionAllowed(nullSelectionAllowed)

  // TODO: Is there difference between null and "" so we could return "" when input prompt is null and avoid Option?
  def inputPrompt: Option[String] = Option(p.getInputPrompt)
  def inputPrompt_=(inputPrompt: Option[String]) = p.setInputPrompt(inputPrompt.getOrElse(null))
  def inputPrompt_=(inputPrompt: String) = p.setInputPrompt(inputPrompt)

  def textInputAllowed = p.isTextInputAllowed()
  def textInputAllowed_=(textInputAllowed: Boolean) = p.setTextInputAllowed(textInputAllowed)
}

class ListSelect(caption: String = null, width: Option[Measure] = None, height: Option[Measure] = None, dataSource: com.vaadin.data.Container = null, value: Any = null, style: String = null, nullSelectionAllowed: Boolean = true)
  extends com.vaadin.ui.ListSelect(caption) with ValueChangeFunction {
  setWidth(if (width.isDefined) width.get.toString else null)
  setHeight(if (height.isDefined) height.get.toString else null)
  if (dataSource != null) setContainerDataSource(dataSource)
  if (value != null) setValue(value)
  setStyleName(style)
  setNullSelectionAllowed(nullSelectionAllowed)
}

class OptionGroup(caption: String = null, width: Option[Measure] = None, height: Option[Measure] = None, dataSource: com.vaadin.data.Container = null, value: Any = null, style: String = null, prompt: String = null, nullSelectionAllowed: Boolean = true)
  extends com.vaadin.ui.OptionGroup(caption) with ValueChangeFunction {
  setWidth(if (width.isDefined) width.get.toString else null)
  setHeight(if (height.isDefined) height.get.toString else null)
  if (dataSource != null) setContainerDataSource(dataSource)
  if (value != null) setValue(value)
  setStyleName(style)
  setNullSelectionAllowed(nullSelectionAllowed)
}

class TwinColSelect(caption: String = null, width: Option[Measure] = None, height: Option[Measure] = None, dataSource: com.vaadin.data.Container = null, value: Any = null, style: String = null, prompt: String = null, nullSelectionAllowed: Boolean = true)
  extends com.vaadin.ui.TwinColSelect(caption) with ValueChangeFunction {
  setWidth(if (width.isDefined) width.get.toString else null)
  setHeight(if (height.isDefined) height.get.toString else null)
  if (dataSource != null) setContainerDataSource(dataSource)
  if (value != null) setValue(value)
  setStyleName(style)
  setNullSelectionAllowed(nullSelectionAllowed)
}

trait ItemClickListener extends com.vaadin.event.ItemClickEvent.ItemClickNotifier {
  def addItemClickListener(action: com.vaadin.event.ItemClickEvent => Unit) {
    addListener(new com.vaadin.event.ItemClickEvent.ItemClickListener {
      override def itemClick(event: com.vaadin.event.ItemClickEvent) = action(event)
    })
  }
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
  extends com.vaadin.ui.Tree with ValueChangeFunction with ItemClickListener {
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