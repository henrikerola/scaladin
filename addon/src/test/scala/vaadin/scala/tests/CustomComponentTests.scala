package vaadin.scala.tests

import vaadin.scala._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CustomComponentTests extends FunSuite {

  test("compositionRoot") {
    class MyCustomComponent extends CustomComponent {
      val layout = new HorizontalLayout

      assert(compositionRoot === None)

      compositionRoot = layout
      assert(compositionRoot === Some(layout))
    }

    new MyCustomComponent
  }

}
