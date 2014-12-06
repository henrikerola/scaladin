package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.BeforeAndAfter
import org.scalatest.mock.MockitoSugar
import vaadin.scala.mixins.GridLayoutMixin
import org.mockito.Mockito

@RunWith(classOf[JUnitRunner])
class GridLayoutTests extends FunSuite with BeforeAndAfter with MockitoSugar {

  class VaadinGridLayout extends com.vaadin.ui.GridLayout with GridLayoutMixin

  var layout: GridLayout = _
  var spy: VaadinGridLayout = _

  before {
    val vaadinGridLayout = new VaadinGridLayout
    spy = Mockito.spy(vaadinGridLayout)
    layout = new GridLayout(spy)
    layout.rows = 10
    layout.columns = 10
  }

  test("add(component)") {
    val label = new Label
    layout.add(label)
    Mockito.verify(spy).addComponent(label.p)
  }

  test("add(component, alignment)") {
    val label = new Label
    layout.add(label, alignment = Alignment.BottomCenter)
    Mockito.verify(spy).setComponentAlignment(label.p, com.vaadin.ui.Alignment.BOTTOM_CENTER)
  }

  test("add(component, row, col)") {
    val label = new Label
    layout.add(label, 0, 0)
    Mockito.verify(spy).addComponent(label.p, 0, 0)
  }

  test("add(component, row, col, row2, col2)") {
    val label = new Label
    layout.add(label, 0, 0, 0, 0)
    Mockito.verify(spy).addComponent(label.p, 0, 0, 0, 0)
  }

  test("defaultComponentAlignment") {
    assert(Alignment.TopLeft == layout.defaultComponentAlignment)

    layout.defaultComponentAlignment = Alignment.BottomCenter
    assert(Alignment.BottomCenter == layout.defaultComponentAlignment)
  }
}
