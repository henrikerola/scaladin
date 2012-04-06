package vaadin.scala

object ComboBox {
  object FilterinMode extends Enumeration {
    import com.vaadin.ui.AbstractSelect.Filtering._
    val Off = Value(FILTERINGMODE_OFF)
    val StartsWith = Value(FILTERINGMODE_STARTSWITH)
    val Contains = Value(FILTERINGMODE_CONTAINS)
  }
}

// In Vaadin ComboBox extends Select, here just extend AbstractSelect directly and add methods from Select to ComboBox
class ComboBox(override val p: com.vaadin.ui.ComboBox = new com.vaadin.ui.ComboBox)(implicit wrapper: WrapperRegistry) extends AbstractSelect {

  wrapper.put(this)

  def inputPrompt: Option[String] = Option(p.getInputPrompt)
  def inputPrompt_=(inputPrompt: Option[String]) = p.setInputPrompt(inputPrompt.getOrElse(null))
  def inputPrompt_=(inputPrompt: String) = p.setInputPrompt(inputPrompt)

  def textInputAllowed = p.isTextInputAllowed
  def textInputAllowed_=(textInputAllowed: Boolean) = p.setTextInputAllowed(textInputAllowed)

  // from Select:

  def filteringMode = ComboBox.FilterinMode(p.getFilteringMode)
  def filteringMode_=(filteringMode: ComboBox.FilterinMode.Value) = p.setFilteringMode(filteringMode.id)

  def scrollToSelectedItem = p.isScrollToSelectedItem
  def scrollToSelectedItem_=(scrollToSelectedItem: Boolean) = p.setScrollToSelectedItem(scrollToSelectedItem)

  // TODO: blur and focus listeners
}