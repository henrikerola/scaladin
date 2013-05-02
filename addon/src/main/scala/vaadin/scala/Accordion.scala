package vaadin.scala

import vaadin.scala.mixins.AccordionMixin

package mixins {
  trait AccordionMixin extends TabSheetMixin { self: com.vaadin.ui.Accordion => }
}

/**
 * @see com.vaadin.ui.Accordion
 * @author Henri Kerola / Vaadin
 */
class Accordion(
  override val p: com.vaadin.ui.Accordion with AccordionMixin = new com.vaadin.ui.Accordion with AccordionMixin)
    extends TabSheet(p)