package vaadin.scala

class TextField
    extends AbstractTextField {

  override val p = new com.vaadin.ui.TextField
  WrapperRegistry.put(this)

  def this(caption: String = null, width: String = null, height: String = null, property: com.vaadin.data.Property = null, value: Any = null, style: String = null, prompt: String = null) {
    this()
    p.setCaption(caption)
    p.setWidth(width)
    p.setHeight(height)
    if (property != null) p.setPropertyDataSource(property)
    if (value != null) p.setValue(value)
    p.setStyleName(style)
    p.setInputPrompt(prompt)
  }

  def prompt: Option[String] = Option(p.getInputPrompt())
  def prompt_=(prompt: Option[String]) = p.setInputPrompt(prompt.getOrElse(null))
  def prompt_=(prompt: String) = p.setInputPrompt(prompt)

}