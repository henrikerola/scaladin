package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala.TextField
import vaadin.scala.Invalid

class ValidationTests extends FunSuite {

  test("Validators add/remove") {
    val field = new TextField
    field.validators += (_ => Invalid("reason"))

    assert(Invalid("reason") === field.validate)
  }
}