package vaadin.scala

import vaadin.scala.listeners.BlurListeners
import vaadin.scala.listeners.FocusListeners

class OptionGroup(override val p: com.vaadin.ui.OptionGroup = new com.vaadin.ui.OptionGroup) extends AbstractSelect with MultiSelectable {

  WrapperRegistry.put(this)

  def itemEnabled(itemId: Any) = p.isItemEnabled(itemId)
  def itemEnabled(itemId: Any, enabled: Boolean) = p.setItemEnabled(itemId, enabled)

  def htmlContentAllowed = p.isHtmlContentAllowed
  def htmlContentAllowed_=(htmlContentAllowed: Boolean) = p.setHtmlContentAllowed(htmlContentAllowed)

  lazy val blurListeners = new BlurListeners(p)
  lazy val focusListeners = new FocusListeners(p)

}