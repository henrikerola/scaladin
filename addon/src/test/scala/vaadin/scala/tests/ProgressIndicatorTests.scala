package vaadin.scala.tests

import vaadin.scala.ProgressIndicator
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import org.mockito.Mockito
import org.mockito.Matchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import vaadin.scala.mixins.ProgressIndicatorMixin

@RunWith(classOf[JUnitRunner])
class ProgressIndicatorTests extends FunSuite with BeforeAndAfter {

  var progressIndicator: ProgressIndicator = _

  before {
    progressIndicator = new ProgressIndicator
  }

  test("indeterminate") {
    assert(!progressIndicator.indeterminate)

    progressIndicator.indeterminate = true
    assert(progressIndicator.indeterminate)
  }

  test("pollingInterval") {
    assert(progressIndicator.pollingInterval === 1000)

    progressIndicator.pollingInterval = 3000
    assert(progressIndicator.pollingInterval === 3000)
  }
}
