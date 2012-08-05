package vaadin.scala

import scala.xml.Node
import vaadin.scala.mixins.LabelMixin

package mixins {
  trait LabelMixin extends AbstractComponentMixin
}

object Label {
  object ContentMode extends Enumeration {
    import com.vaadin.ui.Label._
    val Text = Value(CONTENT_TEXT, "text")
    val Preformatted = Value(CONTENT_PREFORMATTED, "preformatted")
    // Note, CONTENT_UIDL is deprecated in com.vaadin.ui.Label so not added here
    val Xhtml = Value(CONTENT_XHTML, "xhtml")
    val Xml = Value(CONTENT_XML, "xml")
    val Raw = Value(CONTENT_RAW, "raw")
  }

  def undefinedSized(newValue: String): Label = new Label {
    value = newValue
    sizeUndefined()
  }

  def html(htmlValue: Node) = new Label { value = htmlValue.toString; contentMode = ContentMode.Xhtml }
  def apply(labelValue: String) = new Label { value = labelValue }
}

// TODO: implement interfaces
class Label(override val p: com.vaadin.ui.Label with LabelMixin = new com.vaadin.ui.Label with LabelMixin) extends AbstractComponent(p) with PropertyViewer {

  // TODO: should we call value as content instead?
  def value: Option[String] = Option(if (p.getValue() != null) p.getValue().toString else null)
  def value_=(value: Option[String]): Unit = p.setValue(value.getOrElse(null))
  def value_=(value: String): Unit = p.setValue(value)
  def value_=(value: Node): Unit = p.setValue(value.toString)

  def contentMode = Label.ContentMode(p.getContentMode)
  def contentMode_=(contentMode: Label.ContentMode.Value) = p.setContentMode(contentMode.id)

  // TODO: valuechangelistener
  // TODO: getType
}

