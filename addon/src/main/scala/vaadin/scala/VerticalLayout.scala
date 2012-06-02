package vaadin.scala

import vaadin.scala.mixins.VerticalLayoutMixin

package mixins {
  trait VerticalLayoutMixin extends AbstractOrderedLayoutMixin
}

class VerticalLayout(override val p: com.vaadin.ui.VerticalLayout with VerticalLayoutMixin = new com.vaadin.ui.VerticalLayout with VerticalLayoutMixin) extends AbstractOrderedLayout(p)