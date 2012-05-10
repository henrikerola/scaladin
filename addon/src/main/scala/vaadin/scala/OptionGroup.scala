package vaadin.scala

import vaadin.scala.mixins.OptionGroupMixin
import vaadin.scala.listeners.BlurListeners
import vaadin.scala.listeners.FocusListeners

package mixins {
  trait OptionGroupMixin extends AbstractSelectMixin
}

class OptionGroup(override val p: com.vaadin.ui.OptionGroup with OptionGroupMixin = new com.vaadin.ui.OptionGroup with OptionGroupMixin) extends AbstractSelect(p) with MultiSelectable {

  def itemEnabled(itemId: Any) = p.isItemEnabled(itemId)
  def itemEnabled(itemId: Any, enabled: Boolean) = p.setItemEnabled(itemId, enabled)

  def htmlContentAllowed = p.isHtmlContentAllowed
  def htmlContentAllowed_=(htmlContentAllowed: Boolean) = p.setHtmlContentAllowed(htmlContentAllowed)

  lazy val blurListeners = new BlurListeners(p)
  lazy val focusListeners = new FocusListeners(p)

}