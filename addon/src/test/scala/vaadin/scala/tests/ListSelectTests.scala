package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import com.vaadin.terminal.Sizeable
import com.vaadin.data.util.IndexedContainer
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ListSelectTests extends FunSuite {

  test("rows") {
    val listSelect = new ListSelect

    assert(listSelect.rows === 0)

    listSelect.rows = 7
    assert(listSelect.rows === 7)
  }

  test("columns") {
    val listSelect = new ListSelect

    assert(listSelect.columns === 0)

    listSelect.columns = 5
    assert(listSelect.columns === 5)
  }

}