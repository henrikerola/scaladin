package vaadin.scala

import vaadin.scala.mixins.OptionGroupMixin

package mixins {
  trait OptionGroupMixin extends AbstractSelectMixin { self: com.vaadin.ui.OptionGroup => }
}

class OptionGroup(override val p: com.vaadin.ui.OptionGroup with OptionGroupMixin = new com.vaadin.ui.OptionGroup with OptionGroupMixin)
    extends AbstractSelect(p) with MultiSelectable with BlurNotifier with FocusNotifier {

  def itemEnabled(itemId: Any) = p.isItemEnabled(itemId)
  def itemEnabled(itemId: Any, enabled: Boolean) = p.setItemEnabled(itemId, enabled)

  def htmlContentAllowed = p.isHtmlContentAllowed
  def htmlContentAllowed_=(htmlContentAllowed: Boolean) = p.setHtmlContentAllowed(htmlContentAllowed)
}