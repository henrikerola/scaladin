package vaadin.scala.tests

import vaadin.scala.AbstractTextField
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import org.scalatest.mock.MockitoSugar
import vaadin.scala.mixins.AbstractTextFieldMixin
import org.mockito.Mockito

@RunWith(classOf[JUnitRunner])
class AbstractTextFieldTests extends FunSuite with BeforeAndAfter with MockitoSugar {

  class VaadinAbstractTextField extends com.vaadin.ui.AbstractTextField with AbstractTextFieldMixin {
  }

  var abstractTextField: AbstractTextField = _
  var spy: VaadinAbstractTextField = _

  before {
    spy = Mockito.spy(new VaadinAbstractTextField)
    abstractTextField = new AbstractTextField(spy) {}

  }

  test("TextChangeEventModes") {
    assert(AbstractTextField.TextChangeEventMode.Eager.id === com.vaadin.ui.AbstractTextField.TextChangeEventMode.EAGER.ordinal)
    assert(AbstractTextField.TextChangeEventMode.Lazy.id === com.vaadin.ui.AbstractTextField.TextChangeEventMode.LAZY.ordinal)
    assert(AbstractTextField.TextChangeEventMode.Timeout.id === com.vaadin.ui.AbstractTextField.TextChangeEventMode.TIMEOUT.ordinal)
  }

  test("nullSettingAllowed") {
    assert(!abstractTextField.nullSettingAllowed)

    abstractTextField.nullSettingAllowed = true
    assert(abstractTextField.nullSettingAllowed)
  }

  test("maxLength") {
    assert(abstractTextField.maxLength === -1)

    abstractTextField.maxLength = 10
    assert(abstractTextField.maxLength === 10)
  }

  test("columns") {
    assert(abstractTextField.columns === 0)

    abstractTextField.columns = 5
    assert(abstractTextField.columns === 5)
  }

  test("selectAll()") {
    abstractTextField.selectAll()
    Mockito.verify(spy).selectAll()
  }

  test("cursorPosition") {
    assert(abstractTextField.cursorPosition === 0)

    abstractTextField.cursorPosition = 10
    assert(abstractTextField.cursorPosition === 10)
  }

  test("textChangeTimeout") {
    assert(abstractTextField.textChangeTimeout === 400)

    abstractTextField.textChangeTimeout = 100
    assert(abstractTextField.textChangeTimeout === 100)
  }

  test("textChangeEventMode") {
    assert(abstractTextField.textChangeEventMode === AbstractTextField.TextChangeEventMode.Lazy)

    abstractTextField.textChangeEventMode = AbstractTextField.TextChangeEventMode.Timeout
    assert(abstractTextField.textChangeEventMode === AbstractTextField.TextChangeEventMode.Timeout)
  }

}