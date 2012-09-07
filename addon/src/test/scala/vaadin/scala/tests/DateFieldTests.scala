package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import vaadin.scala.Property
import com.vaadin.data.util.ObjectProperty
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DateFieldTests extends FunSuite {

  test("Resolutions") {
    assert(DateField.Resolution.Second.id === com.vaadin.ui.DateField.Resolution.SECOND.ordinal)
    assert(DateField.Resolution.Minute.id === com.vaadin.ui.DateField.Resolution.MINUTE.ordinal)
    assert(DateField.Resolution.Hour.id === com.vaadin.ui.DateField.Resolution.HOUR.ordinal)
    assert(DateField.Resolution.Day.id === com.vaadin.ui.DateField.Resolution.DAY.ordinal)
    assert(DateField.Resolution.Month.id === com.vaadin.ui.DateField.Resolution.MONTH.ordinal)
    assert(DateField.Resolution.Year.id === com.vaadin.ui.DateField.Resolution.YEAR.ordinal)
  }

  test("unparsableDateStringHandler") {
    val dateField = new DateField

    val date = new java.util.Date
    val handler = { e: DateField.UnparsableDateStringEvent =>
      Some(date)
    }

    assert(dateField.unparsableDateStringHandler === Some(DateField.DefaultUnparsableDateStringHandler))
    intercept[RuntimeException] {
      dateField.p.handleUnparsableDateString("test")
    }

    dateField.unparsableDateStringHandler = handler
    assert(dateField.unparsableDateStringHandler === Some(handler))
    assert(dateField.p.handleUnparsableDateString("test") === date)

    dateField.unparsableDateStringHandler = None
    assert(dateField.unparsableDateStringHandler === None)
    assert(dateField.p.handleUnparsableDateString("test") === null)

    dateField.unparsableDateStringHandler = Some(handler)
    assert(dateField.unparsableDateStringHandler === Some(handler))
    assert(dateField.p.handleUnparsableDateString("test") === date)
  }

  test("DateField.UnparsableDateStringEvent") {
    val dateField = new DateField
    var dateFieldFromHandlerEvent: Option[DateField] = None
    var dateStringFromHandlerEvent: Option[String] = None
    dateField.unparsableDateStringHandler = { e: DateField.UnparsableDateStringEvent =>
      dateFieldFromHandlerEvent = Option(e.dateField)
      dateStringFromHandlerEvent = Option(e.dateString)
      None
    }
    dateField.p.handleUnparsableDateString("dateString")
    assert(dateFieldFromHandlerEvent === Some(dateField))
    assert(dateStringFromHandlerEvent === Some("dateString"))

  }

  test("resolution") {
    val dateField = new DateField
    assert(dateField.resolution === DateField.Resolution.Second)

    dateField.resolution = DateField.Resolution.Month
    assert(dateField.resolution === DateField.Resolution.Month)
  }

  test("dateFormat") {
    val dateField = new DateField
    assert(dateField.dateFormat === None)

    dateField.dateFormat = "yyyy.MM.dd"
    assert(dateField.dateFormat === Some("yyyy.MM.dd"))

    dateField.dateFormat = None
    assert(dateField.dateFormat === None)

    dateField.dateFormat = Some("yyMMddHHmmssZ")
    assert(dateField.dateFormat === Some("yyMMddHHmmssZ"))
  }

  test("lenient") {
    val dateField = new DateField
    assert(!dateField.lenient)

    dateField.lenient = true
    assert(dateField.lenient)
  }

  test("showISOWeekNumbers") {
    val dateField = new DateField
    assert(!dateField.showISOWeekNumbers)

    dateField.showISOWeekNumbers = true
    assert(dateField.showISOWeekNumbers)
  }

  test("parseErrorMessage") {
    val dateField = new DateField
    assert(dateField.parseErrorMessage === Some("Date format not recognized"))

    dateField.parseErrorMessage = "parse error msg"
    assert(dateField.parseErrorMessage === Some("parse error msg"))

    dateField.parseErrorMessage = None
    assert(dateField.parseErrorMessage === None)

    dateField.parseErrorMessage = Some("My error message")
    assert(dateField.parseErrorMessage === Some("My error message"))
  }

  test("timeZone") {
    val dateField = new DateField
    assert(dateField.timeZone === None)

    val timeZone = java.util.TimeZone.getTimeZone("GMT+2")
    dateField.timeZone = timeZone
    assert(dateField.timeZone === Some(timeZone))

    dateField.timeZone = None
    assert(dateField.timeZone === None)

    dateField.timeZone = Some(timeZone)
    assert(dateField.timeZone === Some(timeZone))
  }

  test("Example") {
    new DateField {
      immediate = true
      resolution = DateField.Resolution.Day
      showISOWeekNumbers = true
      unparsableDateStringHandler = { e =>
        None
      }
    }
  }
}