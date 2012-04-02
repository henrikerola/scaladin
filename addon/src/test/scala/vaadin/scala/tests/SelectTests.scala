package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import com.vaadin.terminal.Sizeable
import com.vaadin.data.util.IndexedContainer

class SelectTests extends FunSuite {
  
  test("ComboBox, default constructor") {
    val comboBox = new ComboBox
    assert(comboBox.caption === None)
    assert(comboBox.p.getWidth === -1)
    assert(comboBox.p.getWidthUnits === Sizeable.UNITS_PIXELS)
    assert(comboBox.p.getHeight === -1)
    assert(comboBox.p.getHeightUnits === Sizeable.UNITS_PIXELS)
    assert(comboBox.p.getContainerDataSource != null)
    assert(comboBox.p.getPropertyDataSource === null)
    assert(comboBox.p.getValue === null)
    assert(comboBox.p.getStyleName === "")
    assert(comboBox.p.getInputPrompt === null)
    assert(comboBox.p.isNullSelectionAllowed)
  }

}