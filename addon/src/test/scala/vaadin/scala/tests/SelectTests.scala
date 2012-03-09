package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import com.vaadin.terminal.Sizeable
import com.vaadin.data.util.IndexedContainer

class SelectTests extends FunSuite {

  implicit val wr = new WrapperRegistry

  test("ComboBox, constructor with all params but without names") {
    val container = new IndexedContainer;
    container.addItem("Value")
    val comboBox = new ComboBox("Caption", 10 px, 100 em, container, "Value", "Style", "input prompt", false)
    assert(comboBox.caption.get === "Caption")
    assert(comboBox.p.getWidth === 10)
    assert(comboBox.p.getWidthUnits === Sizeable.UNITS_PIXELS)
    assert(comboBox.p.getHeight === 100)
    assert(comboBox.p.getHeightUnits === Sizeable.UNITS_EM)
    assert(comboBox.p.getContainerDataSource === container)
    assert(comboBox.p.getPropertyDataSource === null)
    assert(comboBox.p.getValue === "Value")
    assert(comboBox.p.getStyleName === "Style")
    assert(comboBox.p.getInputPrompt === "input prompt")
    assert(!comboBox.p.isNullSelectionAllowed)
  }

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