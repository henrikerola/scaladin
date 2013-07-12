package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import vaadin.scala.server.ThemeResource

@RunWith(classOf[JUnitRunner])
class EmbeddedTests extends FunSuite {

  test("parameters") {
    val embedded = new Embedded
    embedded.parameters += "param1" -> "value1"
    embedded.parameters += "param2" -> "value2"
    embedded.parameters += "param3" -> "value3"
    assert(embedded.parameters.size === 3)
    assert(embedded.parameters === Map("param1" -> "value1", "param2" -> "value2", "param3" -> "value3"))

    embedded.parameters += "param2" -> "new value 2"
    assert(embedded.parameters === Map("param1" -> "value1", "param2" -> "new value 2", "param3" -> "value3"))

    embedded.parameters -= "param1"
    assert(embedded.parameters.size === 2)
    assert(embedded.parameters === Map("param2" -> "new value 2", "param3" -> "value3"))
  }

  test("alternateText") {
    val embedded = new Embedded
    assert(embedded.alternateText === None)

    embedded.alternateText = "alternate"
    assert(embedded.alternateText === Some("alternate"))

    embedded.alternateText = None
    assert(embedded.alternateText === None)

    embedded.alternateText = Some("alternate")
    assert(embedded.alternateText === Some("alternate"))
  }

  test("source, Resource") {
    val embedded = new Embedded

    val res = new ThemeResource("img.png")
    embedded.source = res
    assert(embedded.source === Some(res))
  }

  test("source, None") {
    val embedded = new Embedded
    embedded.source = None
    assert(embedded.source === None)
  }

  test("codebase, String") {
    val embedded = new Embedded
    embedded.codebase = "codebase"
    assert(embedded.codebase === Some("codebase"))
  }

  test("codebase, Option") {
    val embedded = new Embedded
    embedded.codebase = Option("codebase")
    assert(embedded.codebase === Some("codebase"))
  }

  test("codebase, None") {
    val embedded = new Embedded
    embedded.codebase = None
    assert(embedded.codebase === None)
  }

  test("codetype, String") {
    val embedded = new Embedded
    embedded.codetype = "codetype"
    assert(embedded.codetype === Some("codetype"))
  }

  test("codetype, Option") {
    val embedded = new Embedded
    embedded.codetype = Option("codetype")
    assert(embedded.codetype === Some("codetype"))
  }

  test("codetype, None") {
    val embedded = new Embedded
    embedded.codetype = None
    assert(embedded.codetype === None)
  }

  test("standby, String") {
    val embedded = new Embedded
    embedded.standby = "standby"
    assert(embedded.standby === Some("standby"))
  }

  test("standby, Option") {
    val embedded = new Embedded
    embedded.standby = Option("standby")
    assert(embedded.standby === Some("standby"))
  }

  test("standby, None") {
    val embedded = new Embedded
    embedded.standby = None
    assert(embedded.standby === None)
  }

  test("classId, String") {
    val embedded = new Embedded
    embedded.classId = "classId"
    assert(embedded.classId === Some("classId"))
  }

  test("classId, Option") {
    val embedded = new Embedded
    embedded.classId = Option("classId")
    assert(embedded.classId === Some("classId"))
  }

  test("classId, None") {
    val embedded = new Embedded
    embedded.classId = None
    assert(embedded.classId === None)
  }

  test("archive, String") {
    val embedded = new Embedded
    embedded.archive = "archive"
    assert(embedded.archive === Some("archive"))
  }

  test("archive, Option") {
    val embedded = new Embedded
    embedded.archive = Option("archive")
    assert(embedded.archive === Some("archive"))
  }

  test("archive, None") {
    val embedded = new Embedded
    embedded.archive = None
    assert(embedded.archive === None)
  }
}