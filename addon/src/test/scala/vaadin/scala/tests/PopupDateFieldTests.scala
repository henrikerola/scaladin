package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import com.vaadin.terminal.Sizeable
import vaadin.scala.Property
import com.vaadin.data.util.ObjectProperty
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PopupDateFieldTests extends FunSuite {

  test("prompt") {
    val popupDateField = new PopupDateField
    assert(popupDateField.prompt === None)

    popupDateField.prompt = "prompt"
    assert(popupDateField.prompt === Some("prompt"))

    popupDateField.prompt = None
    assert(popupDateField.prompt === None)

    popupDateField.prompt = Some("input date")
    assert(popupDateField.prompt === Some("input date"))
  }

  test("unparsableDateStringHandler") {
    var i = 0;
    val dateField = new PopupDateField
    dateField.unparsableDateStringHandler = { e =>
      i = i + 1
      None
    }
    dateField.p.handleUnparsableDateString("test")
    assert(i === 1)
  }
}