package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import org.scalatest.BeforeAndAfter
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.mockito.Mockito

@RunWith(classOf[JUnitRunner])
class UITests extends FunSuite with BeforeAndAfter {

  var ui: UI = _

  before {
    ui = new UI {
      def init(request: WrappedRequest) {
      }
    }
  }

  test("UI.current") {
    val ui = new UI {
      def init(request: WrappedRequest) {
      }
    }

    assert(UI.current === null)

    UI.current = ui
    assert(UI.current === ui)

    UI.current = None
    assert(UI.current === null)
  }

  test("uiId") {
    assert(ui.uiId === -1)
  }

  test("content") {
    val layout = new VerticalLayout

    assert(ui.content.isInstanceOf[VerticalLayout])
    //assert(ui.content.asInstanceOf[VerticalLayout].margin === Margin(true, true, true, true))

    ui.content = layout
    assert(ui.content === layout)
  }
  
  test("resizeLazy") {
    assert(!ui.resizeLazy)
    ui.resizeLazy = true
    assert(ui.resizeLazy)
  }
}