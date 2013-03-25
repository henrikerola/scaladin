package vaadin.scala.tests

import vaadin.scala._
import com.vaadin.server.{ Page => VaadinPage, VaadinRequest }
import org.mockito.Mockito

class PageTests extends ScaladinTestSuite {

  var page: Page = _
  var vaadinPage: VaadinPage = _

  before {
    vaadinPage = mock[VaadinPage]
    page = new Page {
      override val p = vaadinPage
    }
  }

  test("Page.current") {
    assert(Page.current === null)

    val myUI = new com.vaadin.ui.UI() {
      def init(request: VaadinRequest) {}
    }
    com.vaadin.ui.UI.setCurrent(myUI)

    assert(Page.current.p === myUI.getPage)
  }

  test("setUriFragment") {
    page.setUriFragment("test1", true)
    Mockito.verify(vaadinPage).setUriFragment("test1", true)

    page.setUriFragment(None, false)
    Mockito.verify(vaadinPage).setUriFragment(null, false)
  }

  test("uriFragment") {
    Mockito.when(vaadinPage.getUriFragment()).thenReturn("test123")
    assert(page.uriFragment === Some("test123"))

    page.uriFragment = None
    Mockito.verify(vaadinPage).setUriFragment(null)

    page.uriFragment = "myfragment"
    Mockito.verify(vaadinPage).setUriFragment("myfragment")
  }

  test("browserWindowSize") {
    Mockito.when(vaadinPage.getBrowserWindowWidth).thenReturn(1300)
    Mockito.when(vaadinPage.getBrowserWindowHeight).thenReturn(950)

    assert(page.browserWindowSize === (1300, 950))

    page.browserWindowSize = (1300, 950)
    Mockito.verify(vaadinPage).updateBrowserWindowSize(1300, 950)
  }

  test("browserWindowHeight") {
    Mockito.when(vaadinPage.getBrowserWindowHeight).thenReturn(950)
    assert(page.browserWindowHeight === 950)

    page.browserWindowHeight = 950
    Mockito.verify(vaadinPage).updateBrowserWindowSize(0, 950)
  }

  test("browserWindowWidth") {
    Mockito.when(vaadinPage.getBrowserWindowWidth).thenReturn(1300)
    assert(page.browserWindowWidth === 1300)

    page.browserWindowWidth = 1300
    Mockito.verify(vaadinPage).updateBrowserWindowSize(1300, 0)
  }
}