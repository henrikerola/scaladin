package vaadin.scala.tests

import vaadin.scala._
import com.vaadin.server.{ Page => VaadinPage }
import internal.{ AfterViewChangeListener, BeforeViewChangeListener }
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.mockito.Mockito

object ClassBasedTestView {
  var count = 0
  private def inc = { count += 1; count }
}
class ClassBasedTestView extends Navigator.PanelView {
  ClassBasedTestView.inc
  override def enter(event: Navigator.ViewChangeEvent) {}
}

@RunWith(classOf[JUnitRunner])
class NavigatorTests extends ScaladinTestSuite {

  var scaladinPage: Page = _
  var vaadinPage: VaadinPage = _
  val ui = new UI {
    override def page = scaladinPage
  }
  val contentLayout = new VerticalLayout {}
  val testView2 = new TestView2
  var navigator: Navigator = null

  class TestView1 extends Navigator.PanelView {
    content = Label("Hello from DemoView1")
  }

  class TestView2 extends TestView1 {
    content = Label("Hello from DemoView2")

    var nr: Option[Int] = None

    override def enter(event: Navigator.ViewChangeEvent) {
      nr = Some(1)
    }
  }

  before {
    vaadinPage = mock[VaadinPage]
    scaladinPage = new Page {
      override val p = vaadinPage
    }
    Mockito.when(vaadinPage.getUriFragment).thenReturn("")
    navigator = Navigator(ui, contentLayout)
    navigator.addView("", new TestView1)
    navigator.addView("TestView2", testView2)
  }

  test("Navigator construct") {

    assert(navigator.stateManager.isInstanceOf[Navigator.UriFragmentManager])

  }

  test("Navigator navigateTo") {
    assert(navigator.stateManager.state === "")

    Mockito.when(vaadinPage.getUriFragment).thenReturn("!TestView2")
    navigator.navigateTo("TestView2")
    assert(navigator.stateManager.state === "TestView2")
    assert(testView2.nr === Some(1))

  }

  test("ComponentContainerViewDisplay.showView") {
    val componentMock = mock[ComponentContainer]
    val testView = new TestView1
    val cc = new Navigator.ComponentContainerViewDisplay(componentMock)
    cc.showView(testView)
    Mockito.verify(componentMock).p
    Mockito.verify(componentMock).removeAllComponents()
    Mockito.verify(componentMock).addComponent(testView)
  }

  test("SingleComponentContainerViewDisplay.showView") {
    val componentMock = mock[Panel]
    val testView = new TestView1
    val cc = new Navigator.SingleComponentContainerViewDisplay(componentMock)
    cc.showView(testView)
    Mockito.verify(componentMock).content_=(testView)
  }

  test("UriFragmentManager.state") {
    assert(navigator.state === "")
    Mockito.when(vaadinPage.getUriFragment).thenReturn("MyPage")
    assert(navigator.state === "")
    Mockito.when(vaadinPage.getUriFragment).thenReturn("!MyPage")
    assert(navigator.state === "MyPage")
  }

  test("Navigator.ClassBasedViewProvider") {
    navigator.addView("ClassBasedTestView", classOf[ClassBasedTestView])
    assert(ClassBasedTestView.count === 0)
    navigator.navigateTo("ClassBasedTestView")
    assert(ClassBasedTestView.count === 1)
    navigator.navigateTo("ClassBasedTestView")
    assert(ClassBasedTestView.count === 2)
  }

  test("Navigator.beforeViewChangeListeners.listeners with ClassBasedViewProvider errorView") {
    var listenerVar = 0
    navigator.errorView(classOf[ClassBasedTestView])
    navigator.beforeViewChangeListeners.listenerSet.add(new BeforeViewChangeListener((e) => {
      listenerVar += 1
      listenerVar > 1
    }))
    navigator.navigateTo("TestView2")
    assert(listenerVar === 1)
    navigator.navigateTo("TestView2")
    assert(listenerVar === 2)
  }

  test("Navigator.afterViewChangeListeners.listeners") {
    var listenerVar = 0
    navigator.afterViewChangeListeners.listenerSet.add(new AfterViewChangeListener((e) => {
      listenerVar += 1
    }))
    navigator.navigateTo("TestView2")
    assert(listenerVar === 1)
  }
}