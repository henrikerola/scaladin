package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import org.scalatest.BeforeAndAfter
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import vaadin.scala.mixins.WindowMixin
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito
import vaadin.scala.Layout.Margin

@RunWith(classOf[JUnitRunner])
class WindowTests extends FunSuite with BeforeAndAfter with MockitoSugar {

  class VaadinWindow extends com.vaadin.ui.Window with WindowMixin

  var vaadinWindowSpy: VaadinWindow = _
  var window: Window = _

  before {
    vaadinWindowSpy = Mockito.spy(new VaadinWindow)
    window = new Window(vaadinWindowSpy)
  }

  test("content, VerticalLayout is Window's default content") {
    assert(window.content.get.isInstanceOf[VerticalLayout])
    assert(window.content.get.asInstanceOf[VerticalLayout].margin === Margin(true, true, true, true))
  }

  test("positionX") {
    assert(window.positionX === -1)

    window.positionX = 200
    assert(window.positionX === 200)
  }

  test("positionY") {
    assert(window.positionY === -1)

    window.positionY = 200
    assert(window.positionY === 200)
  }

  test("position") {
    assert((-1, -1) === window.position)

    window.position = (10, 20)

    Mockito.verify(vaadinWindowSpy).setPositionX(10)
    Mockito.verify(vaadinWindowSpy).setPositionY(20)

    assert((10, 20) === window.position)
  }

  test("resizable") {
    assert(window.resizable)

    window.resizable = false
    assert(!window.resizable)
  }

  test("resizeLazy") {
    assert(!window.resizeLazy)

    window.resizeLazy = true
    Mockito.verify(vaadinWindowSpy).setResizeLazy(true)
    assert(window.resizeLazy)
  }

  test("center()") {
    window.center()
    Mockito.verify(vaadinWindowSpy).center()
  }

  test("bringToFront()") {
    intercept[IllegalStateException] {
      window.bringToFront()
    }
    Mockito.verify(vaadinWindowSpy).bringToFront()
  }

  test("modal") {
    assert(!window.modal)

    window.modal = true
    assert(window.modal)
  }

  test("closable") {
    assert(window.closable)

    window.closable = false
    assert(!window.closable)
  }

  test("draggable") {
    assert(window.draggable)

    window.draggable = false
    assert(!window.draggable)
  }

  test("windowMode") {
    assert(Window.WindowMode.Normal == window.windowMode)

    window.windowMode = Window.WindowMode.Maximized

    assert(Window.WindowMode.Maximized == window.windowMode)
  }

  test("closeShortcut") {
    val window = new Window

    val clickShortcut = KeyShortcut(KeyCode.Enter, KeyModifier.Alt)

    assert(window.closeShortcut === None)

    window.closeShortcut = clickShortcut
    assert(window.closeShortcut === Some(clickShortcut))

    window.closeShortcut = None
    assert(window.closeShortcut === None)

    window.closeShortcut = Some(clickShortcut)
    assert(window.closeShortcut === Some(clickShortcut))
  }
}