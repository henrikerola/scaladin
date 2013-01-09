package vaadin.scala.tests

import vaadin.scala._
import org.mockito.Mockito._
import scala.beans.BeanProperty

class FieldGroupTests extends ScaladinTestSuite {

  var fieldGroup: FieldGroup = _

  before {
    fieldGroup = new FieldGroup
  }

  test("setting FieldGroupFieldFactory") {
    val factory = DefaultFieldGroupFieldFactory

    fieldGroup.fieldFactory = factory
    assertSameFactory(factory, fieldGroup.fieldFactory)

    fieldGroup.fieldFactory = Option(factory)
    assertSameFactory(factory, fieldGroup.fieldFactory)

    val factoryFunctionMock = mock[Function2[Class[_], Class[_], Option[Field[_]]]]
    val itemMock = mock[Item]
    fieldGroup.fieldFactory = factoryFunctionMock

    assert(fieldGroup.fieldFactory.isDefined, "No field factory created for field function")

    val dataType = classOf[String]
    val fieldType = classOf[TextField]
    fieldGroup.fieldFactory.get.createField(dataType, fieldType)

    verify(factoryFunctionMock)(dataType, fieldType)
  }

  //TODO
  test("creating fields") {

  }

  //  test("add field") {
  //    val field = new TextField
  //    fieldGroup.addField('testId, field)
  //    val addedField = fieldGroup.field('testId)
  //    assert(addedField.isDefined)
  //    assert(field === addedField.get)
  //    assert(field.p === addedField.get.p)
  //  }

  test("item datasource") {
    val item = new IndexedContainer().addItem('id).get
    fieldGroup.item = item
    assert(fieldGroup.item.get.p === item.p)

    fieldGroup.item = Some(item)
    assert(fieldGroup.item.get.p === item.p)
  }

  test("item datasource, None") {
    fieldGroup.item = None
    assert(fieldGroup.item == None)
  }

  //  test("add field option") {
  //    val field = new TextField
  //    //these shouldn't throw exceptions
  //    fieldGroup.addField(None, None)
  //    fieldGroup.addField(Some('id), None)
  //    fieldGroup.addField(None, Some(new TextField))
  //    fieldGroup.addField(Some('id), Some(field))
  //
  //    assert(1 === fieldGroup.layout.components.size)
  //    assert(Some(field) === fieldGroup.field('id))
  //  }

  //  test("commit") {
  //    assert(fieldGroup.commit.isSuccess)
  //
  //    val field = new TextField { value = "value" }
  //    field.validators += (_ => Valid)
  //    fieldGroup.addField('id, field)
  //    assert(fieldGroup.commit.isSuccess)
  //
  //    field.validators += (_ => Invalid(List()))
  //    assert(!fieldGroup.commit.isSuccess)
  //  }

  test("buffered") {
    fieldGroup.buffered = true
    val person = Person("test", "tester")
    fieldGroup.item = new BeanItem(person)
    fieldGroup.field("firstName").foreach(_.value = "newValue")

    assert("test" === person.firstName)
    assert(fieldGroup.commit.isRight)
    assert("newValue" === person.firstName)
  }

  private def assertSameFactory(expected: FieldGroupFieldFactory, result: Option[FieldGroupFieldFactory]) = {
    assert(result.isDefined, "No fieldfactory as result")
    assert(expected === result.get)
    assert(expected.p === result.get.p)
  }

  case class Person(@BeanProperty var firstName: String, @BeanProperty var lastName: String)
}