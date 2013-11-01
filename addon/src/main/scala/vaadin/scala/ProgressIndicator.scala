package vaadin.scala

import vaadin.scala.mixins.ProgressIndicatorMixin

package mixins {
  @Deprecated
  trait ProgressIndicatorMixin extends AbstractFieldMixin[java.lang.Float] { self: com.vaadin.ui.ProgressIndicator => }
}

/**
 * @see com.vaadin.ui.ProgressIndicator
 * @author Henri Kerola / Vaadin
 */
@Deprecated
class ProgressIndicator(override val p: com.vaadin.ui.ProgressIndicator with ProgressIndicatorMixin = new com.vaadin.ui.ProgressIndicator with ProgressIndicatorMixin) extends AbstractField[java.lang.Float](p) {

  def indeterminate: Boolean = p.isIndeterminate
  def indeterminate_=(indeterminate: Boolean) = p.setIndeterminate(indeterminate)

  def pollingInterval: Int = p.getPollingInterval
  def pollingInterval_=(pollingInterval: Int) = p.setPollingInterval(pollingInterval)
}