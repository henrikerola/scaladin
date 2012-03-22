package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite

class CheckBoxTests extends FunSuite {

  implicit val testWr = new WrapperRegistry
  
  test("Constructor with all params but without names") {
    val icon = new ThemeResource("icon.png");
    // TODO listener
    val checkBox = new CheckBox("Caption", true, true, null, icon, "Style", false, "Description")
    assert(checkBox.caption === Some("Caption"))
    assert(checkBox.value === Some(true))
    assert(checkBox.immediate === true)
    assert(checkBox.icon === Some(icon))
    assert(checkBox.p.getStyleName === "Style")
    assert(checkBox.enabled === false)
    assert(checkBox.description === Some("Description"))
  }

  test("Default constructor") {
    val checkBox = new CheckBox()
    assert(checkBox.caption === None)
    assert(checkBox.value === Some(false))
    assert(checkBox.immediate === false)
    assert(checkBox.icon === None)
    assert(checkBox.p.getStyleName === "")
    assert(checkBox.enabled === true)
    assert(checkBox.description === None)
  }
  
  test("value") {
    val checkBox = new CheckBox()
    
    checkBox.value = true
    assert(checkBox.value === Some(true))
  }
  
  // TODO test focus and blur listeners
}