package vaadin.scala.tests

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import org.scalatest.mock.MockitoSugar
import vaadin.scala._
import org.mockito.Mockito._

@RunWith(classOf[JUnitRunner])
class FormTests extends FunSuite with BeforeAndAfter with MockitoSugar {

  var form: Form = _

  before {
    form = new Form
  }

  test("setting TableFieldFactory") {
    val factory = DefaultFieldFactory

    form.formFieldFactory = factory
    assertSameFactory(factory, form.formFieldFactory)

    form.formFieldFactory = Option(factory)
    assertSameFactory(factory, form.formFieldFactory)

    val factoryFunctionMock = mock[Function1[FormFieldIngredients, Option[Field]]]
    val itemMock = mock[Item]
    form.formFieldFactory = factoryFunctionMock

    assert(form.formFieldFactory.isDefined, "No field factory created for field function")

    val testIngredients = FormFieldIngredients(itemMock, "testPropertyId", form)

    form.formFieldFactory.get.createField(testIngredients)

    verify(factoryFunctionMock)(testIngredients)
  }

  test("add field") {
    val field = new TextField
    form.addField('testId, field)
    val addedField = form.field('testId)
    assert(addedField.isDefined)
    assert(field === addedField.get)
    assert(field.p === addedField.get.p)
  }

  test("layout") {
    val layout = new VerticalLayout
    form.layout = layout
    assert(layout === form.layout)
    assert(layout.p === form.layout.p)
  }

  test("visible item properties") {
    form.visibleItemProperties = List('test1, 'test2)
    assert(form.visibleItemProperties === List('test1, 'test2))
  }

  test("footer") {
    val footer = new VerticalLayout
    form.footer = footer
    assert(footer === form.footer)
    assert(footer.p === form.footer.p)
  }

  test("item datasource"){
    val item = new IndexedContainer().addItem('id).get
    form.item = item
    assert(form.item.get.p === item.p)

    form.item = Some(item)
    assert(form.item.get.p === item.p)
  }
  
  test("item datasource, None") {
    form.item = None
    assert(form.item == None)
  }

  test("set visible item properties") {
    val container = new IndexedContainer
    container.addContainerProperty("test2", classOf[String])
    container.addContainerProperty("test1", classOf[String])
    form.item = container.addItem('id)
    form.visibleItemProperties = Seq("test1","test2")
    assert("test1" === form.visibleItemProperties.head)
    assert("test2" === form.visibleItemProperties.tail.head)
  }
  
  test("form layout") {
    assert(form.layout.isInstanceOf[FormLayout])
    form.layout = new VerticalLayout
    assert(form.layout.isInstanceOf[VerticalLayout])
  }
  
  test("add field option") {
    val field = new TextField
    //these shouldn't throw exceptions
    form.addField(None,None)
    form.addField(Some('id),None)
    form.addField(None, Some(new TextField))
    form.addField(Some('id), Some(field))
    
    assert(1 === form.layout.components.size)
    assert(Some(field) === form.field('id) )
  }
  
  private def assertSameFactory(expected: FormFieldFactory, result: Option[FormFieldFactory]) = {
    assert(result.isDefined, "No fieldfactory as result")
    assert(expected === result.get)
    assert(expected.p === result.get.p)
  }
}