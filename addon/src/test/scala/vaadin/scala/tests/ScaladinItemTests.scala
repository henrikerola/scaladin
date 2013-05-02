package vaadin.scala.tests

import vaadin.scala.ScaladinItem

class ScaladinItemTests extends ScaladinTestSuite {

  case class Person(val firstName: String, var lastName: String)

  test("binding") {
    val item = new ScaladinItem[Person](new Person("John", "Smith"))
    assert(2 === item.propertyIds.size)
    assert(Some("John") === item.property("firstName").get.value)
    assert(Some("Smith") === item.property("lastName").get.value)

    item.property("lastName").get.value = "Doe"

    assert(Some("Doe") === item.property("lastName").get.value)
  }

}
