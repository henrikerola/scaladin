package vaadin.scala.tests

import vaadin.scala.Label
import vaadin.scala.mixins.ScaladinMixin
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MixinTests extends FunSuite with BeforeAndAfter with MockitoSugar {

  before {

  }

  test("Setting the wrapper for a mixin more than once should throw an exception") {
    val labelWithMixin = new com.vaadin.ui.Label with ScaladinMixin
    labelWithMixin.wrapper = new Label
    intercept[RuntimeException] {
      labelWithMixin.wrapper = new Label
    }
  }

}