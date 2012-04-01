package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import org.scalatest.BeforeAndAfter

class WindowTests extends FunSuite with BeforeAndAfter {

  var window: Window = _

  before {
    window = new Window
  }

  test("child window handling") {
    val anotherWindow = new Window

    assert(window.childWindows.isEmpty)

    window.childWindows += anotherWindow

    assert(window.childWindows.contains(anotherWindow))

    window.childWindows -= anotherWindow

    assert(window.childWindows.isEmpty)
  }
}