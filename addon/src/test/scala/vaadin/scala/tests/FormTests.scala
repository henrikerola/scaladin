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

  test("item datasource")(pending)

  private def assertSameFactory(expected: FormFieldFactory, result: Option[FormFieldFactory]) = {
    assert(result.isDefined, "No fieldfactory as result")
    assert(expected === result.get)
    assert(expected.p === result.get.p)
  }
}