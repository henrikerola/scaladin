package vaadin.scala.tests

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import vaadin.scala._
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

@RunWith(classOf[JUnitRunner])
class ValidationTests extends FunSuite with MockitoSugar {

  val validValidator = new Validator { def validate(value: Option[Any]) = Valid }
  val invalidValidator = new Validator { def validate(value: Option[Any]) = Invalid(List("reason")) }

  test("add/remove") {
    val field = new TextField
    field.value = "test"

    field.validators += invalidValidator
    assert(Invalid(List("reason")) === field.validate)

    field.validators -= invalidValidator

    assert(Valid === field.validate)
  }

  test("add function as validator") {
    val field = new TextField
    field.validators += (_ => Invalid(List("reason")))
    field.value = "test"
    assert(Invalid(List("reason")) === field.validate)
  }

  test("validator gets correct value") {
    var called = false
    val field = new TextField
    val testValidator = Validator(value => { called = true; assert(Some("test") === value); Valid })
    field.validators += testValidator
    field.value = "test"
    field.validate
    assert(called, "Validator was never called")
  }

  test("ValidationResult isValid") {
    val field = new TextField
    field.value = "test"

    field.validators += validValidator

    assert(field.validate.isValid, "Field wasn't valid")

    field.validators += invalidValidator

    assert(!field.validate.isValid, "Field wasn't invalid")
  }

  test("Validation chaining") {
    val field1 = new TextField
    field1.value = "test"
    field1.validators += validValidator

    val field2 = new TextField
    field2.value = "test"
    field2.validators += invalidValidator

    assert(field1.validate :: field1.validate === Valid)
    assert(field1.validate :: field2.validate === Invalid(List("reason")))
    assert(field2.validate :: field1.validate === Invalid(List("reason")))
    assert(field2.validate :: field2.validate === Invalid(List("reason", "reason")))
  }
}