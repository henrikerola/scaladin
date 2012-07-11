package vaadin.scala.tests

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import vaadin.scala._

@RunWith(classOf[JUnitRunner])
class ValidationTests extends FunSuite {

  test("add/remove") {
    val field = new TextField
    field.value = 'test

    val testValidator = new Validator { def validate(value: Any) = Invalid("reason1") }
    field.validators += testValidator
    assert(Invalid("reason1") === field.validate)

    field.validators -= testValidator

    assert(Valid === field.validate)
  }

  test("add function as validator") {
    val field = new TextField
    field.validators += (_ => Invalid("reason"))
    field.value = 'test
    assert(Invalid("reason") === field.validate)
  }
}