package vaadin.scala.tests

import vaadin.scala._
import vaadin.scala.Validator._
import org.mockito.Mockito._
import org.mockito.Matchers._
import vaadin.scala.internal.WrapperUtil

import scala.beans.BeanProperty
import vaadin.scala.FieldGroup.propertyId

class FieldGroupTests extends ScaladinTestSuite {

  var fieldGroup: FieldGroup = _

  before {
    fieldGroup = new FieldGroup
  }

  test("wrapper") {
    assert(Some(fieldGroup) == WrapperUtil.wrapperFor(fieldGroup.p))
  }

  test("setting FieldGroupFieldFactory") {
    val factory = DefaultFieldGroupFieldFactory

    fieldGroup.fieldFactory = factory
    assertSameFactory(factory, fieldGroup.fieldFactory)

    fieldGroup.fieldFactory = Option(factory)
    assertSameFactory(factory, fieldGroup.fieldFactory)

    val factoryFunctionMock = mock[Function2[Class[_], Class[_], Option[Field[_]]]]

    fieldGroup.fieldFactory = factoryFunctionMock

    assert(fieldGroup.fieldFactory.isDefined, "No field factory created for field function")

  }

  ignore("creating fields") {
    val field = new TextField

    val factoryFunctionMock = mock[Function2[Class[_], Class[_], Option[Field[_]]]]
    when(factoryFunctionMock(any(), any())).thenReturn(Option(field))

    fieldGroup.fieldFactory = factoryFunctionMock

    val dataType = classOf[String]
    val fieldType = classOf[TextField]
    val fieldfactory = fieldGroup.fieldFactory.get
    fieldfactory.createField(dataType, fieldType)

    fieldGroup.bind(field, 'propertyId)
    verify(factoryFunctionMock)(dataType, fieldType)
  }

  test("add field") {
    val field = new TextField

    fieldGroup.bind(field, 'testId)
    val addedField = fieldGroup.field('testId)
    assert(addedField.isDefined)
    assert(field === addedField.get)
    assert(field.p === addedField.get.p)
  }

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

  test("item defined in constructor") {
    val item = ScaladinItem(Person("John", "Doe"))
    val fieldGroup = FieldGroup(item)
    assert(Some(item) === fieldGroup.item)
  }

  test("commit") {
    val item = new IndexedContainer().addItem('id).get
    fieldGroup.item = item
    assert(fieldGroup.commit.isRight)

    val field = new TextField { value = "value" }
    field.validators += (_ => Valid)
    fieldGroup.bind(field, 'id)
    assert(fieldGroup.commit.isRight)

    field.validators += (_ => Invalid(List()))
    assert(!fieldGroup.commit.isRight)
  }

  test("buffered") {
    fieldGroup.buffered = true
    val person = Person("test", "tester")
    fieldGroup.item = new BeanItem(person)
    fieldGroup.bind(new TextField, "firstName")
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

  class MyFormLayout extends GridLayout {
    val firstName = new TextField
    @propertyId("lastName")
    val last = new TextField
  }

  test("bindMemberFields") {
    val myFormLayout = new MyFormLayout
    val item = new BeanItem[Person](Person("John", "Doe"))
    fieldGroup.item = item
    fieldGroup.bindMemberFields(myFormLayout)

    assert(Some("John") === myFormLayout.firstName.value)
    assert(Some("Doe") === myFormLayout.last.value)
  }
}