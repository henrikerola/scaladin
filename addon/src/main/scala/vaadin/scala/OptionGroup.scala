package vaadin.scala

class OptionGroup(override val p: com.vaadin.ui.OptionGroup = new com.vaadin.ui.OptionGroup) extends AbstractSelect with MultiSelectable {

  WrapperRegistry.put(this)

  def itemEnabled(itemId: Any) = p.isItemEnabled(itemId)
  def itemEnabled(itemId: Any, enabled: Boolean) = p.setItemEnabled(itemId, enabled)

  def htmlContentAllowed = p.isHtmlContentAllowed
  def htmlContentAllowed_=(htmlContentAllowed: Boolean) = p.setHtmlContentAllowed(htmlContentAllowed)
  
  // TODO: blur and focus listeners

}