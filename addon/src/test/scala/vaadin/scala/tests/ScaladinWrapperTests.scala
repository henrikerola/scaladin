package vaadin.scala.tests
import org.scalatest.FunSuite
import vaadin.scala.VerticalLayout
import vaadin.scala.WrapperRegistry
import vaadin.scala.Component
import vaadin.scala.AbstractComponent
import vaadin.scala.ScaladinWrapper

class ScaladinWrapperTests extends FunSuite {

  implicit val testWrapper = new WrapperRegistry

  test("Wrap Vaadin Label") {
    val vaadinLabel = new com.vaadin.ui.Label with ScaladinWrapper
    vaadinLabel.init

    val scaladinLayout = new VerticalLayout {
      components += vaadinLabel
    }

    //assert that hierarchy works both ways
    assert(vaadinLabel === scaladinLayout.components.head)
    assert(vaadinLabel.parent === Some(scaladinLayout))
  }

  test("Wrapping Vaadin component and using it without initing first") {
    intercept[IllegalStateException] {
      val vaadinLabel = new com.vaadin.ui.Label with ScaladinWrapper
      vaadinLabel.parent
    }
  }
}