package vaadin.scala

import vaadin.scala.mixins.HorizontalLayoutMixin

package mixins {
  trait HorizontalLayoutMixin extends AbstractOrderedLayoutMixin
}

class HorizontalLayout(override val p: com.vaadin.ui.HorizontalLayout with HorizontalLayoutMixin = new com.vaadin.ui.HorizontalLayout with HorizontalLayoutMixin) extends AbstractOrderedLayout(p)