package vaadin.scala

import vaadin.scala.mixins.VerticalLayoutMixin

package mixins {
  trait VerticalLayoutMixin extends AbstractOrderedLayoutMixin
}

object VerticalLayout {
  def fullSized(c: Component*): VerticalLayout = new VerticalLayout {
    components ++= c
    sizeFull()
  }

  def undefinedSized(c: Component*): VerticalLayout = new VerticalLayout {
    components ++= c
    sizeUndefined()
  }
}

class VerticalLayout(override val p: com.vaadin.ui.VerticalLayout with VerticalLayoutMixin = new com.vaadin.ui.VerticalLayout with VerticalLayoutMixin) extends AbstractOrderedLayout(p)