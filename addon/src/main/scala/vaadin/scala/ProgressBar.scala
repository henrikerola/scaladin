package vaadin.scala

import vaadin.scala.mixins.ProgressBarMixin

package mixins {
  trait ProgressBarMixin extends AbstractFieldMixin[java.lang.Float] {
    self: com.vaadin.ui.ProgressBar =>
  }
}

/**
 * @see com.vaadin.ui.ProgressBar
 * @author Henri Kerola / Vaadin
 */
class ProgressBar(
  override val p: com.vaadin.ui.ProgressBar with ProgressBarMixin = new com.vaadin.ui.ProgressBar with ProgressBarMixin)
    extends AbstractField[java.lang.Float](p) {

  def indeterminate: Boolean = p.isIndeterminate
  def indeterminate_=(indeterminate: Boolean): Unit = p.setIndeterminate(indeterminate)

}
