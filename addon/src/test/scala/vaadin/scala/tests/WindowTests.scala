package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import org.scalatest.BeforeAndAfter
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class WindowTests extends FunSuite with BeforeAndAfter {

  var window: Window = _

  before {
    window = new Window
  }
  
  test("content, VerticalLayout is Window's default content") {
    assert(window.content.isInstanceOf[VerticalLayout])
    assert(window.content.asInstanceOf[VerticalLayout].margin === Margin(true, true, true, true))
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