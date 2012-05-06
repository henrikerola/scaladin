package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import com.vaadin.terminal.Sizeable
import vaadin.scala.Property
import com.vaadin.data.util.ObjectProperty
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DateFieldTests extends FunSuite {

  test("Resolutions") {
    assert(DateField.Resolution.Second.id === com.vaadin.ui.DateField.RESOLUTION_SEC)
    assert(DateField.Resolution.Minute.id === com.vaadin.ui.DateField.RESOLUTION_MIN)
    assert(DateField.Resolution.Hour.id === com.vaadin.ui.DateField.RESOLUTION_HOUR)
    assert(DateField.Resolution.Day.id === com.vaadin.ui.DateField.RESOLUTION_DAY)
    assert(DateField.Resolution.Month.id === com.vaadin.ui.DateField.RESOLUTION_MONTH)
    assert(DateField.Resolution.Year.id === com.vaadin.ui.DateField.RESOLUTION_YEAR)
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
}