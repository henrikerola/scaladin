package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import com.vaadin.data.util.IndexedContainer
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TextAreaTests extends FunSuite {

  test("rows") {
    val textarea = new TextArea

    assert(textarea.rows === 5)

    textarea.rows = 7
    assert(textarea.rows === 7)
  }

  test("wordwrap") {
    val textarea = new TextArea

    assert(textarea.wordwrap === true)

    textarea.wordwrap = false
    assert(textarea.wordwrap === false)
  }
}