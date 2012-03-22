package vaadin.scala

import scala.xml.Node

object Label {
  object ContentMode extends Enumeration {
    import com.vaadin.ui.Label._
    val text = Value(CONTENT_TEXT, "text")
    val preformatted = Value(CONTENT_PREFORMATTED, "preformatted")
    // Note, CONTENT_UIDL is deprecated in com.vaadin.ui.Label so not added here
    val xhtml = Value(CONTENT_XHTML, "xhtml")
    val xml = Value(CONTENT_XML, "xml")
    val raw = Value(CONTENT_RAW, "raw")
  }
}

// TODO: implement interfaces
class Label(implicit wrapper: WrapperRegistry) extends AbstractComponent  with PropertyViewer {

  override val p = new com.vaadin.ui.Label;
  wr.put(this)

  // icon, caption as constructor parameters?
  def this(content: String = null, width: Option[Measure] = 100 percent, height: Option[Measure] = None, property: com.vaadin.data.Property = null, contentMode: Label.ContentMode.Value = Label.ContentMode(com.vaadin.ui.Label.CONTENT_DEFAULT), style: String = null)(implicit wrapper: WrapperRegistry) = {
    this()

    this.width = width
    this.height = height
    if (property != null) p.setPropertyDataSource(property)
    if (content != null) p.setValue(content)
    p.setContentMode(contentMode.id)
    p.setStyleName(style)
  }

  // TODO: should we call value as content instead?
  def value: Option[String] = Option(if (p.getValue() != null) p.getValue().toString else null)
  def value_=(value: Option[String]): Unit = p.setValue(value.getOrElse(null))
  def value_=(value: String): Unit = p.setValue(value)

  def contentMode = Label.ContentMode(p.getContentMode)
  def contentMode_=(contentMode: Label.ContentMode.Value) = p.setContentMode(contentMode.id)

  // TODO: valuechangelistener
  // TODO: getType
}

class HtmlLabel(content: Node = null, width: Option[Measure] = 100 percent, height: Option[Measure] = None, property: com.vaadin.data.Property = null, style: String = null)(implicit wrapper: WrapperRegistry)
  extends Label(width = width, height = height, content = if (content != null) content.toString else null, property = property, contentMode = Label.ContentMode.xhtml, style = style)(wrapper)
