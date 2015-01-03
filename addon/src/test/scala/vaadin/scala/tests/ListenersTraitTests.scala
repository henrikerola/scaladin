package vaadin.scala.tests

import com.vaadin.ui.Button.{ ClickEvent, ClickListener }
import vaadin.scala._

/**
 *
 * @author Henri Kerola / Vaadin
 */
class ListenersTraitTests extends ScaladinTestSuite {

  test("iterator") {
    val button = new Button

    button.clickListeners += { e => }

    button.p.addClickListener(new ClickListener {
      override def buttonClick(clickEvent: ClickEvent): Unit = {}
    })

    assert(1 == button.clickListeners.size)
    assert(2 == button.p.getListeners(classOf[com.vaadin.ui.Button.ClickEvent]).size)
  }

  test("-=") {
    val button = new Button

    val clickListener = { e: Button.ClickEvent => }
    button.clickListeners += clickListener
    assert(1 == button.clickListeners.size)

    button.clickListeners -= clickListener
    assert(0 == button.clickListeners.size)
  }

}
