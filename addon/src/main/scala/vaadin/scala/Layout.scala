package vaadin.scala

import vaadin.scala.mixins.LayoutMixin

package mixins {
  trait LayoutMixin extends ComponentContainerMixin { self: com.vaadin.ui.Layout => }
}

object Layout {

  case class Margin(top: Boolean = false, right: Boolean = false, bottom: Boolean = false, left: Boolean = false)

  trait SpacingHandler {

    def p: com.vaadin.ui.Layout.SpacingHandler

    def spacing: Boolean = p.isSpacing()
    def spacing_=(spacing: Boolean) { p.setSpacing(spacing) }
  }

  trait AlignmentHandler {

    def p: com.vaadin.ui.Layout.AlignmentHandler

    def getAlignment(component: Component): Alignment.Value =
      Alignment(p.getComponentAlignment(component.p).getBitMask)

    def setAlignment(component: Component, alignment: Alignment.Value): Unit =
      p.setComponentAlignment(component.p, new com.vaadin.ui.Alignment(alignment.id))

    def defaultComponentAlignment: Alignment.Value =
      Alignment(p.getDefaultComponentAlignment.getBitMask)

    def defaultComponentAlignment_=(defaultComponentAlignment: Alignment.Value): Unit =
      p.setDefaultComponentAlignment(new com.vaadin.ui.Alignment(defaultComponentAlignment.id))

  }

  trait MarginHandler {

    def p: com.vaadin.ui.Layout.MarginHandler

    def margin: Margin = {
      val margin = p.getMargin
      Margin(margin.hasTop, margin.hasRight, margin.hasBottom, margin.hasLeft)
    }
    def margin_=(margin: Boolean) { p.setMargin(margin) }
    def margin_=(margin: Margin) {
      p.setMargin(new com.vaadin.shared.ui.MarginInfo(margin.top, margin.right, margin.bottom, margin.left))
    }
    def margin(top: Boolean = false, right: Boolean = false, bottom: Boolean = false, left: Boolean = false) {
      margin = (Margin(top, right, bottom, left))
    }
  }
}

/**
 *
 * @author Henri Kerola / Vaadin
 */
trait Layout extends ComponentContainer {

  override def p: com.vaadin.ui.Layout with LayoutMixin

}
