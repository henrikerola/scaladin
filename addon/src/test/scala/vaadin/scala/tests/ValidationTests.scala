package vaadin.scala.tests

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import vaadin.scala._
import vaadin.scala.Validator._
import org.scalatest.mock.MockitoSugar

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

  test("Use Java Vaadin validators, inline wrap") {
    import scala.util._
    import scala.util.control.Exception._

    val field1 = new TextField
    field1.validators += Validator(value => {
      catching(classOf[com.vaadin.data.Validator.InvalidValueException]) withTry (new com.vaadin.data.validator.EmailValidator("foo").validate(value.orNull)) match {
        case Success(value) => Valid
        case Failure(exception) => Invalid(List(exception.getMessage))
      }
    })

    field1.value = "foo"
    assert(field1.validate === Invalid(List("foo")))

    field1.value = "test@email.com"
    assert(field1.validate === Valid)
  }

  test("Use Java Vaadin validators, extension wrap") {
    import scala.util._
    import scala.util.control.Exception._

    val field1 = new TextField
    field1.validators += new Validator {
      def validate(value: Option[Any]): Validation = {
        catching(classOf[com.vaadin.data.Validator.InvalidValueException]) withTry (new com.vaadin.data.validator.EmailValidator("foo").validate(value.orNull)) match {
          case Success(value) => Valid
          case Failure(exception) => Invalid(List(exception.getMessage))
        }
      }
    }

    field1.value = "foo"
    assert(field1.validate === Invalid(List("foo")))

    field1.value = "test@email.com"
    assert(field1.validate === Valid)
  }

  test("wrap an existing Vaadin validator") {
    val validator = Validator(new com.vaadin.data.validator.EmailValidator("fail"))
    assert(validator.validate(None) === Valid)
    assert(validator.validate(Some("foo@example.com")) === Valid)
    assert(validator.validate(Some("example.com")) === Invalid(List("fail")))

  }

  test("wrapped IntValidator") {
    //class IntValidator extends scala.Validator()
  }

}