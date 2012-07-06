package vaadin.scala.tests

import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import vaadin.scala.FormFieldFactory
import org.mockito.Mockito._
import vaadin.scala.FormFieldIngredients
import vaadin.scala.BeanItem
import vaadin.scala.TextField
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import vaadin.scala.Form
import vaadin.scala.TableFieldFactory
import vaadin.scala.BeanItemContainer
import vaadin.scala.BeanItemContainer
import vaadin.scala.TableFieldIngredients
import vaadin.scala.Table

@RunWith(classOf[JUnitRunner])
class FieldFactoryTests extends FunSuite with BeforeAndAfter with MockitoSugar {

  test("FormFieldFactoryDelegator calls Scala function") {

    val item = new BeanItem[Any]
    val field = new Form
    field.item = item

    var result: FormFieldIngredients = null

    val factoryMock = FormFieldFactory(ingr => {
      result = ingr
      None
    })

    factoryMock.p.createField(item.p, 'propertyId, field.p)

    assert(result != null)
    assert(result.item.p === item.p)
    assert(result.uiContext === field)
    assert(result.propertyId === 'propertyId)
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