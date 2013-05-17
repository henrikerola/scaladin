package vaadin.scala

import vaadin.scala.mixins.AbstractLayoutMixin

package mixins {
  trait AbstractLayoutMixin extends AbstractComponentContainerMixin with LayoutMixin {
    self: com.vaadin.ui.AbstractLayout =>
  }
}

/**
 *
 * @author Henri Kerola / Vaadin
 */
abstract class AbstractLayout(override val p: com.vaadin.ui.AbstractLayout with AbstractLayoutMixin)
  extends AbstractComponentContainer(p) with Layout
