package vaadin.scala.tests

import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import vaadin.scala._

@RunWith(classOf[JUnitRunner])
class FieldFactoryTests extends ScaladinTestSuite {

  test("FormFieldFactoryDelegator calls Scala function") {

    val item = new BeanItem[Any]
    val field = new FieldGroup
    field.item = item

    var resultDataType: Class[_] = null
    var resultFieldType: Class[_] = null

    val factoryMock = FieldGroupFieldFactory((datatype, fieldtype) => {
      resultDataType = datatype
      resultFieldType = fieldtype
      None
    })

    factoryMock.p.createField(classOf[Integer], classOf[com.vaadin.ui.TextField])

    assert(resultFieldType != null)
    assert(resultDataType != null)
    assert(resultDataType == classOf[Integer])
    assert(resultFieldType == classOf[TextField])
  }

  test("TableFieldFactoryDelegator calls Scala function") {

    val container = new BeanItemContainer[Any]
    val field = new Table
    field.container = container

    var result: TableFieldIngredients = null

    val factoryMock = TableFieldFactory(ingr => {
      result = ingr
      None
    })

    factoryMock.p.createField(container.p, 'itemId, 'propertyId, field.p)

    assert(result != null)
    assert(result.container.p === container.p)
    assert(result.uiContext === field)
    assert(result.itemId === 'itemId)
    assert(result.propertyId === 'propertyId)
  }
}