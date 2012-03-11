package vaadin.scala

class TextField
  extends AbstractTextField {

  override val p = new com.vaadin.ui.TextField
  WrapperRegistry.put(this)

  def this(caption: String = null, width: Option[Measure] = None, height: Option[Measure] = None, property: com.vaadin.data.Property = null, value: Any = null, style: String = null, prompt: String = null) {
    this()

    this.caption = caption
    this.width = width
    this.height = height
    if (property != null) p.setPropertyDataSource(property)
    if (value != null) p.setValue(value)
    p.setStyleName(style)
    p.setInputPrompt(prompt)
  }
}