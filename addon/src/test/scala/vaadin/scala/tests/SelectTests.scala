package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import com.vaadin.server.Sizeable
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SelectTests extends FunSuite {

  test("ComboBox, default constructor") {
    val comboBox = new ComboBox
    assert(comboBox.caption === None)
    assert(comboBox.p.getWidth === -1)
    assert(comboBox.p.getWidthUnits === Sizeable.Unit.PIXELS)
    assert(comboBox.p.getHeight === -1)
    assert(comboBox.p.getHeightUnits === Sizeable.Unit.PIXELS)
    assert(comboBox.p.getContainerDataSource != null)
    assert(comboBox.p.getPropertyDataSource === null)
    assert(comboBox.p.getValue === null)
    assert(comboBox.p.getStyleName === "")
    assert(comboBox.p.getInputPrompt === null)
    assert(comboBox.p.isNullSelectionAllowed)
  }

  test("container") {
    val comboBox = new ComboBox
    assert(comboBox.container.get.isInstanceOf[IndexedContainer])
  }

}