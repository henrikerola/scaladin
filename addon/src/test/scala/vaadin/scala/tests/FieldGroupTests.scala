package vaadin.scala.tests

import vaadin.scala._

class FieldGroupTests extends ScaladinTestSuite{

  var fieldGroup: FieldGroup = _
  
  before {
    fieldGroup = new FieldGroup
  }
  
  test("setting FieldGroupFieldFactory") {
    val factory = DefaultFieldFactory

    fieldGroup.formFieldFactory = factory
    assertSameFactory(factory, fieldGroup.formFieldFactory)

    fieldGroup.formFieldFactory = Option(factory)
    assertSameFactory(factory, fieldGroup.formFieldFactory)

    val factoryFunctionMock = mock[Function1[FormFieldIngredients, Option[Field[_]]]]
    val itemMock = mock[Item]
    fieldGroup.formFieldFactory = factoryFunctionMock

    assert(fieldGroup.formFieldFactory.isDefined, "No field factory created for field function")

    val testIngredients = FormFieldIngredients(itemMock, "testPropertyId", form)

    fieldGroup.formFieldFactory.get.createField(testIngredients)

    verify(factoryFunctionMock)(testIngredients)
  }

  test("add field") {
    val field = new TextField
    fieldGroup.addField('testId, field)
    val addedField = fieldGroup.field('testId)
    assert(addedField.isDefined)
    assert(field === addedField.get)
    assert(field.p === addedField.get.p)
  }

  test("layout") {
    fieldGroup.layout // this should not throw an exception

    val layout = new VerticalLayout
    fieldGroup.layout = layout
    assert(layout === fieldGroup.layout)
    assert(layout.p === fieldGroup.layout.p)
  }

  test("visible item properties") {
    fieldGroup.visibleItemProperties = List('test1, 'test2)
    assert(fieldGroup.visibleItemProperties === List('test1, 'test2))
  }

  test("footer") {
    fieldGroup.footer // this should not throw an exception

    val footer = new VerticalLayout
    fieldGroup.footer = footer
    assert(footer === fieldGroup.footer)
    assert(footer.p === fieldGroup.footer.p)
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

  test("set visible item properties") {
    val container = new IndexedContainer
    container.addContainerProperty("test2", classOf[String])
    container.addContainerProperty("test1", classOf[String])
    fieldGroup.item = container.addItem('id)
    fieldGroup.visibleItemProperties = Seq("test1", "test2")
    assert("test1" === fieldGroup.visibleItemProperties.head)
    assert("test2" === fieldGroup.visibleItemProperties.tail.head)
  }

  test("form layout") {
    assert(fieldGroup.layout.isInstanceOf[FormLayout])
    fieldGroup.layout = new VerticalLayout
    assert(fieldGroup.layout.isInstanceOf[VerticalLayout])
  }

  test("add field option") {
    val field = new TextField
    //these shouldn't throw exceptions
    fieldGroup.addField(None, None)
    fieldGroup.addField(Some('id), None)
    fieldGroup.addField(None, Some(new TextField))
    fieldGroup.addField(Some('id), Some(field))

    assert(1 === fieldGroup.layout.components.size)
    assert(Some(field) === fieldGroup.field('id))
  }

  test("commit") {
    assert(fieldGroup.commit.isValid)

    val field = new TextField { value = "value" }
    field.validators += (_ => Valid)
    fieldGroup.addField('id, field)
    assert(fieldGroup.commit.isValid)

    field.validators += (_ => Invalid(List()))
    assert(!fieldGroup.commit.isValid)
  }

  test("buffered") {
    fieldGroup.buffered = true
    val person = Person("test", "tester")
    fieldGroup.item = new BeanItem(person)
    fieldGroup.field("firstName").foreach(_.value = "newValue")

    assert("test" === person.firstName)
    assert(fieldGroup.commit.isValid)
    assert("newValue" === person.firstName)
  }

  test("validation visible") {
    assert(!fieldGroup.validationVisible)
    fieldGroup.validationVisible = true
    assert(fieldGroup.validationVisible)
  }

  test("validation visible after commit") {
    val person = Person(null, null)

    fieldGroup.item = new BeanItem(person)
    assert(!fieldGroup.validationVisible)

    fieldGroup.field("firstName").foreach(_.required = true)
    fieldGroup.buffered = false
    fieldGroup.commit
    assert(fieldGroup.validationVisible)

    fieldGroup.validationVisible = false
    fieldGroup.validationVisibleOnCommit = false

    fieldGroup.commit
    assert(!fieldGroup.validationVisible)
  }

  private def assertSameFactory(expected: FormFieldFactory, result: Option[FormFieldFactory]) = {
    assert(result.isDefined, "No fieldfactory as result")
    assert(expected === result.get)
    assert(expected.p === result.get.p)
  }

  case class Person(@BeanProperty var firstName: String, @BeanProperty var lastName: String)
}