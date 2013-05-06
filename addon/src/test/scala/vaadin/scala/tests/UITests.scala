package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import internal.WrappedVaadinUI
import org.scalatest.BeforeAndAfter
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.io.{ ByteArrayOutputStream, ObjectOutputStream }
import org.mockito.Mockito.when
import org.mockito.Mockito.verify
import org.mockito.Mockito.times
import org.mockito.Matchers.any

class UITests extends ScaladinTestSuite {

  var ui: UI = _

  before {
    ui = new UI {
      override def init(request: ScaladinRequest) {
      }
    }
  }

  test("UI.current") {
    val ui = new UI {}

    assert(UI.current === null)

    UI.current = ui
    assert(UI.current === ui)

    UI.current = None
    assert(UI.current === null)
  }

  test("Constructor params") {
    var ui = new UI {}
    assert(ui.title === None)
    assert(ui.theme === null)
    assert(ui.widgetset === None)
    assert(ui.preserveOnRefresh === false)
    assert(ui.p != null)

    val wrappedVaadinUI = new WrappedVaadinUI
    ui = new UI("mytitle", "mytheme", "mywidgetset", true, wrappedVaadinUI) {}

    assert(ui.title === Some("mytitle"))
    assert(ui.theme === "mytheme")
    assert(ui.widgetset === Some("mywidgetset"))
    assert(ui.preserveOnRefresh === true)
    assert(ui.p === wrappedVaadinUI)
  }

  test("initialization without init(request: ScaladinRequest)") {

    var methodCallCnt = 0

    class TestUI extends UI {
      method()
      def method() {
        methodCallCnt = methodCallCnt + 1
      }
    }

    val testUI = new TestUI
    testUI.doInit(null)

    assert(methodCallCnt === 1)
  }

  test("initialization with init(request: ScaladinRequest)") {

    var methodCallCnt = 0
    var method2CallCnt = 0

    class TestUI extends UI {

      method()

      override def init(request: ScaladinRequest) {
        method2()
      }

      def method() {
        assert(method2CallCnt === 0) // method2 must not be called before this one
        methodCallCnt = methodCallCnt + 1
      }

      def method2() {
        assert(methodCallCnt === 1) // method1 must called before this one
        method2CallCnt = method2CallCnt + 1
      }
    }

    val testUI = new TestUI
    testUI.doInit(null)

    assert(methodCallCnt === 1)
    assert(method2CallCnt === 1)
  }

  test("uiId") {
    assert(ui.uiId === -1)
  }

  test("content") {
    val layout = new VerticalLayout

    assert(ui.content.get.isInstanceOf[VerticalLayout])
    //assert(ui.content.asInstanceOf[VerticalLayout].margin === Margin(true, true, true, true))

    ui.content = layout
    assert(ui.content === Some(layout))
  }

  test("resizeLazy") {
    assert(!ui.resizeLazy)
    ui.resizeLazy = true
    assert(ui.resizeLazy)
  }

  test("navigator") {
    assert(ui.navigator == null)
    assert(ui.p.getNavigator == null)

    val nui = new UI {
      override def init(request: ScaladinRequest) {
      }
      def setNavigator(n: Option[Navigator]) {
        super.navigator_=(n)
      }
    }
    nui.setNavigator(Some(new Navigator(nui, new VerticalLayout {})))
    assert(nui.navigator != null)
    assert(nui.p.getNavigator != null)
    nui.setNavigator(None)
    assert(nui.navigator == null)
    assert(nui.p.getNavigator == null)
  }

  ignore("serialization") {
    new ObjectOutputStream(new ByteArrayOutputStream()).writeObject(ui)
  }

  test("access") {
    //needs some special setup because of the session requirement
    val mockSession = mock[com.vaadin.server.VaadinSession]
    val mockWrapped = mock[WrappedVaadinUI]
    
    when(mockWrapped.getSession()).thenReturn(mockSession)
    
    val testui = new MockUI(mockWrapped)

    val layout = new VerticalLayout
    testui.access(() => layout.spacing)
    testui.access(() => layout.spacing = true)
    testui.access(() => {
      layout.data = "foobar"
    })
    
    verify(mockWrapped, times(3)).access(any(classOf[java.lang.Runnable]))
  }
  
}

class MockUI(val mock: WrappedVaadinUI) extends UI(mock)  