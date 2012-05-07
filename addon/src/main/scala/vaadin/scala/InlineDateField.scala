package vaadin.scala

import vaadin.scala.mixins.InlineDateFieldMixin

package mixins {
  trait InlineDateFieldMixin extends DateFieldMixin
}

class InlineDateField(override val p: com.vaadin.ui.InlineDateField with InlineDateFieldMixin = new com.vaadin.ui.InlineDateField with InlineDateFieldMixin) extends DateField(p)
