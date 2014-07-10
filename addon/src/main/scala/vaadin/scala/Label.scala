package vaadin.scala

import event.ValueChangeNotifier
import vaadin.scala.converter.Converter
import vaadin.scala.mixins.LabelMixin
import com.vaadin.shared.ui.label.ContentMode

package mixins {
  trait LabelMixin extends AbstractComponentMixin { self: com.vaadin.ui.Label => }
}

object Label {
  object ContentMode extends Enumeration {
    import com.vaadin.shared.ui.label.ContentMode._
    val Text = Value(TEXT.ordinal, "text")
    val Preformatted = Value(PREFORMATTED.ordinal, "preformatted")
    val Html = Value(HTML.ordinal, "html")
    // Note, XML and RAW are deprecated so not added here
  }

  def undefinedSized(newValue: String): Label = new Label {
    value = newValue
    sizeUndefined()
  }

  def apply(labelValue: String): Label = new Label { value = labelValue }
}

class Label(override val p: com.vaadin.ui.Label with LabelMixin = new com.vaadin.ui.Label with LabelMixin)
    extends AbstractComponent(p) with PropertyViewer with Property[String] with ValueChangeNotifier {

  def contentMode: Label.ContentMode.Value = Label.ContentMode(p.getContentMode.ordinal)
  def contentMode_=(contentMode: Label.ContentMode.Value) { p.setContentMode(ContentMode.values.apply(contentMode.id)) }

  //readOnly is inherited from Component and Property, needs override
  override def readOnly: Boolean = p.isReadOnly
  override def readOnly_=(readOnly: Boolean) { p.setReadOnly(readOnly) }

  def converter: Option[Converter[String, Any]] = wrapperFor(p.getConverter)
  def converter_=(converter: Converter[String, _]) { p.setConverter(converter.p) }
}

