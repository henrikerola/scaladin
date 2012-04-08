package vaadin.scala.tests
import org.scalatest.FunSuite
import vaadin.scala.VerticalLayout
import vaadin.scala.WrapperRegistry
import vaadin.scala.Component
import vaadin.scala.AbstractComponent
import vaadin.scala.ScaladinWrapper

class ScaladinWrapperTests extends FunSuite {

  implicit val testWrapper = new WrapperRegistry

  test("wrap Vaadin Label") {
    val vaadinLabel = new com.vaadin.ui.Label with ScaladinWrapper init

    val scaladinLayout = new VerticalLayout {
      components += vaadinLabel
    }

    //assert that hierarchy works both ways
    assert(vaadinLabel === scaladinLayout.components.head)
    assert(vaadinLabel.parent === Some(scaladinLayout))
  }

  test("wrapping Vaadin component and using it without initing first") {
    intercept[IllegalStateException] {
      val vaadinLabel = new com.vaadin.ui.Label with ScaladinWrapper
      vaadinLabel.parent
    }
  }

  test("type checks") {
    val vaadinLabel = new com.vaadin.ui.Label with ScaladinWrapper init

    assert(vaadinLabel.isInstanceOf[com.vaadin.ui.Label])
    assert(vaadinLabel.p.isInstanceOf[com.vaadin.ui.Label])

  }
}