package vaadin.scala.server

import vaadin.scala.Component

/**
 * @see com.vaadin.server.Responsive
 * @author Henri Kerola / Vaadin
 */
object Responsive {

  def makeResponsive(components: Component*): Unit = {
    val vaadinComponents = components map { _.p }
    com.vaadin.server.Responsive.makeResponsive(vaadinComponents: _*)
  }

}
