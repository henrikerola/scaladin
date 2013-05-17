package vaadin.scala

import vaadin.scala.mixins.AbstractComponentContainerMixin

package mixins {
  trait AbstractComponentContainerMixin extends AbstractComponentMixin with ComponentContainerMixin {
    self: com.vaadin.ui.AbstractComponentContainer =>
  }
}

/**
 *
 * @author Henri Kerola / Vaadin
 */
abstract class AbstractComponentContainer(
  override val p: com.vaadin.ui.AbstractComponentContainer with AbstractComponentContainerMixin)
    extends AbstractComponent(p) with ComponentContainer
