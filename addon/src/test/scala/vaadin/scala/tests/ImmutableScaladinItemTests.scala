package vaadin.scala.tests

import vaadin.scala.ImmutableScaladinItem

/**
 *
 * @author Henri Kerola / Vaadin
 */
class ImmutableScaladinItemTests extends ScaladinTestSuite {

  case class MyPerson(firstName: String, lastName: String)
  class MyNonCaseClass

  var person: MyPerson = _
  var item: ImmutableScaladinItem[MyPerson] = _

  before {
    person = new MyPerson("John", "Smith")
    item = ImmutableScaladinItem[MyPerson](person)
  }

  test("commit() without modifications, returned bean has correct values") {

    val person = item.commit()

    assert("John" == item.bean.firstName)
    assert("Smith" == item.bean.lastName)

    assert("John" == person.firstName)
    assert("Smith" == person.lastName)
  }

  test("commit() after modifications, returned bean has correct values") {
    item.getProperty("firstName").value = "Joe"

    val person = item.commit()

    assert("Joe" == item.bean.firstName)
    assert("Smith" == item.bean.lastName)

    assert("Joe" == person.firstName)
    assert("Smith" == person.lastName)
  }

  test("IllegalArgumentException is thrown for non-case classes") {
    intercept[IllegalArgumentException] {
      ImmutableScaladinItem[MyNonCaseClass](new MyNonCaseClass)
    }
  }

  test("commit() calls commitListener") {
    var cnt = 0

    item.commitListeners += { e =>
      cnt = cnt + 1
      assert(person === e.bean)
    }

    item.commit()

    assert(1 == cnt)
  }
}
