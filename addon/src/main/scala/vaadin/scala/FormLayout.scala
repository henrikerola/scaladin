package vaadin.scala

import vaadin.scala.mixins.FormLayoutMixin

package mixins {
  trait FormLayoutMixin extends AbstractOrderedLayoutMixin { self: com.vaadin.ui.FormLayout => }
}

class FormLayout(
  override val p: com.vaadin.ui.FormLayout with FormLayoutMixin = new com.vaadin.ui.FormLayout with FormLayoutMixin)
    extends AbstractOrderedLayout(p)