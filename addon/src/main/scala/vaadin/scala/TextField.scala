package vaadin.scala

class TextField(implicit val wr: WrapperRegistry)
    extends AbstractTextField {

  override val p = new com.vaadin.ui.TextField
  wr.put(this)

  def this(caption: String = null, width: String = null, height: String = null, property: com.vaadin.data.Property = null, value: Any = null, style: String = null, prompt: String = null)(implicit wr: WrapperRegistry) {
    this()

    p.setCaption(caption)
    p.setWidth(width)
    p.setHeight(height)
    if (property != null) p.setPropertyDataSource(property)
    if (value != null) p.setValue(value)
    p.setStyleName(style)
    p.setInputPrompt(prompt)
  }
}