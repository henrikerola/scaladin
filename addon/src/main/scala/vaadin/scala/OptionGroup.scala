package vaadin.scala

import vaadin.scala.mixins.OptionGroupMixin

package mixins {
  trait OptionGroupMixin extends AbstractSelectMixin { self: com.vaadin.ui.OptionGroup => }
}

class OptionGroup(override val p: com.vaadin.ui.OptionGroup with OptionGroupMixin = new com.vaadin.ui.OptionGroup with OptionGroupMixin)
    extends AbstractSelect(p) with MultiSelectable with BlurNotifier with FocusNotifier {

  def itemEnabled(itemId: Any): Boolean = p.isItemEnabled(itemId)
  def itemEnabled(itemId: Any, enabled: Boolean): Unit = p.setItemEnabled(itemId, enabled)

  def htmlContentAllowed: Boolean = p.isHtmlContentAllowed
  def htmlContentAllowed_=(htmlContentAllowed: Boolean): Unit = p.setHtmlContentAllowed(htmlContentAllowed)
}