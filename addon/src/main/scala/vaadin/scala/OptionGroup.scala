package vaadin.scala

import com.vaadin.ui.{ OptionGroup => VaadinOptionGroup }
import event.{ FocusNotifier, BlurNotifier }
import vaadin.scala.mixins.OptionGroupMixin

package mixins {
  trait OptionGroupMixin extends AbstractSelectMixin { self: com.vaadin.ui.OptionGroup => }
}

class OptionGroup(override val p: VaadinOptionGroup with OptionGroupMixin = new VaadinOptionGroup with OptionGroupMixin)
    extends AbstractSelect(p) with MultiSelectable with BlurNotifier with FocusNotifier {

  def isItemEnabled(itemId: Any): Boolean = p.isItemEnabled(itemId)
  def setItemEnabled(itemId: Any, enabled: Boolean) { p.setItemEnabled(itemId, enabled) }

  def htmlContentAllowed: Boolean = p.isHtmlContentAllowed
  def htmlContentAllowed_=(htmlContentAllowed: Boolean) { p.setHtmlContentAllowed(htmlContentAllowed) }
}