package vaadin.scala

import vaadin.scala.mixins.AbstractSingleComponentContainerMixin

package mixins {
  trait AbstractSingleComponentContainerMixin extends AbstractComponentMixin with SingleComponentContainerMixin {
    self: com.vaadin.ui.AbstractSingleComponentContainer =>
  }
}

abstract class AbstractSingleComponentContainer(
  override val p: com.vaadin.ui.AbstractSingleComponentContainer with AbstractSingleComponentContainerMixin)
    extends AbstractComponent(p) with SingleComponentContainer