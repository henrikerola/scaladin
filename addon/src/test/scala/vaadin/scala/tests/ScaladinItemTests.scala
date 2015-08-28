package vaadin.scala.tests

import vaadin.scala.ScaladinItem

class ScaladinItemTests extends ScaladinTestSuite {

  case class Person(val firstName: String, var lastName: String) {
    def message: String = "This is a message"
    def message_=(message: String) {}

    def age: Option[Int] = Some(2)
    def age_=(age: Option[Int]) {}
    def age_=(age: Int) {}
  }

  var person: Person = _
  var item: ScaladinItem[Person] = _

  before {
    person = new Person("John", "Smith")
    item = ScaladinItem[Person](person)
  }

  test("construct with propertyIds") {
    val item2 = ScaladinItem(person, "message" :: "firstName" :: "invalidPropertyId" :: Nil)

    val ids = item2.propertyIds.toList

    assert(2 === ids.size)
    assert("message" === ids(0))
    assert("firstName" === ids(1))
  }

  test("binding") {
    assert(4 === item.propertyIds.size)
    assert(Some("John") === item.getPropertyOption("firstName").get.value)
    assert(Some("Smith") === item.getPropertyOption("lastName").get.value)
    assert(Some("This is a message") === item.getPropertyOption("message").get.value)

    item.getPropertyOption("lastName").get.value = "Doe"

    assert(Some("Doe") === item.getPropertyOption("lastName").get.value)
    assert("Doe" === person.lastName)

    //assert(None === item.getPropertyOption("age").get.value)

    //    item.getPropertyOption("age").get.value = 2
    //    item.getPropertyOption("age").get.value = Some(2)
    //    item.getPropertyOption("age").get.value = None

  }

  test("readOnly for val is always true") {
    assert(item.getPropertyOption("firstName").get.readOnly)

    item.getPropertyOption("firstName").get.readOnly = false

    assert(item.getPropertyOption("firstName").get.readOnly)
  }

  test("readOnly for non-val is toggable") {
    assert(!item.getPropertyOption("lastName").get.readOnly)

    item.getPropertyOption("firstName").get.readOnly = true

    assert(item.getPropertyOption("firstName").get.readOnly)
  }

}
