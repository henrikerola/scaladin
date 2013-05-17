package vaadin.scala

import vaadin.scala.mixins.HasComponentsMixin

/**
 *
 * @author Henri Kerola / Vaadin
 */
trait SelectiveRenderer extends HasComponents {

  def p: com.vaadin.ui.SelectiveRenderer with HasComponentsMixin

  def isRendered(component: Component): Boolean = p.isRendered(component.p)
}
