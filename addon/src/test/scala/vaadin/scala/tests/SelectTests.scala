package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import com.vaadin.terminal.Sizeable
import com.vaadin.data.util.IndexedContainer

class SelectTests extends FunSuite {
  
  test("ComboBox, constructor with all params but without names") {
    val container = new IndexedContainer;
    container.addItem("Value")
    val comboBox = new ComboBox("Caption", 10 px, 100 em, container, "Value", "Style", "input prompt", false)
    assert(comboBox.getCaption === "Caption")
    assert(comboBox.getWidth === 10)
    assert(comboBox.getWidthUnits === Sizeable.UNITS_PIXELS)
    assert(comboBox.getHeight === 100)
    assert(comboBox.getHeightUnits === Sizeable.UNITS_EM)
    assert(comboBox.getContainerDataSource === container)
    assert(comboBox.getPropertyDataSource === null)
    assert(comboBox.getValue === "Value")
    assert(comboBox.getStyleName === "Style")
    assert(comboBox.getInputPrompt === "input prompt")
    assert(!comboBox.isNullSelectionAllowed)
  }
  
  test("ComboBox, default constructor") {
    val comboBox = new ComboBox
    assert(comboBox.getCaption === null)
    assert(comboBox.getWidth === -1)
    assert(comboBox.getWidthUnits === Sizeable.UNITS_PIXELS)
    assert(comboBox.getHeight === -1)
    assert(comboBox.getHeightUnits === Sizeable.UNITS_PIXELS)
    assert(comboBox.getContainerDataSource != null)
    assert(comboBox.getPropertyDataSource === null)
    assert(comboBox.getValue === null)
    assert(comboBox.getStyleName === "")
    assert(comboBox.getInputPrompt === null)
    assert(comboBox.isNullSelectionAllowed)
  }

}