package vaadin.scala

import vaadin.scala.mixins.ProgressIndicatorMixin

package mixins {
  trait ProgressIndicatorMixin extends AbstractFieldMixin
}

/**
 * @see com.vaadin.ui.ProgressIndicator
 * @author Henri Kerola / Vaadin
 */
class ProgressIndicator(override val p: com.vaadin.ui.ProgressIndicator with ProgressIndicatorMixin = new com.vaadin.ui.ProgressIndicator with ProgressIndicatorMixin) extends AbstractField[Number](p) {

  def indeterminate: Boolean = p.isIndeterminate
  def indeterminate_=(indeterminate: Boolean) = p.setIndeterminate(indeterminate)

  def pollingInterval: Int = p.getPollingInterval
  def pollingInterval_=(pollingInterval: Int) = p.setPollingInterval(pollingInterval)
}