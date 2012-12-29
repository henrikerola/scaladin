package vaadin.scala

import scala.xml.Node
import scala.xml.NodeBuffer
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

class Label(override val p: com.vaadin.ui.Label with LabelMixin = new com.vaadin.ui.Label with LabelMixin) extends AbstractComponent(p) with PropertyViewer with Property with ValueChangeNotifier {

  def contentMode = Label.ContentMode(p.getContentMode)
  def contentMode_=(contentMode: Label.ContentMode.Value) = p.setContentMode(contentMode.id)

  def value_=(value: NodeBuffer): Unit = p.setValue(value.mkString)

  //readOnly is inherited from Component and Property, needs override
  override def readOnly: Boolean = p.isReadOnly
  override def readOnly_=(readOnly: Boolean): Unit = p.setReadOnly(readOnly)
}

