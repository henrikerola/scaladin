package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala.TextField

class ValidationTests extends FunSuite {

  test("Validators add/remove") {
    val field = new TextField
    field.validators += (value: Any) => InvalidValue("reason")
  }
}