package vaadin.scala.tests

import vaadin.scala._
import com.vaadin.server.{Page => VaadinPage}
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
  
  test("setFragment") {
    page.setFragment("test1", true)
    Mockito.verify(vaadinPage).setFragment("test1", true)
    
    page.setFragment(None, false)
    Mockito.verify(vaadinPage).setFragment(null, false)
  }
  
  test("fragment") {
    Mockito.when(vaadinPage.getFragment()).thenReturn("test123")
    assert(page.fragment === Some("test123"))
    
    page.fragment = None
    Mockito.verify(vaadinPage).setFragment(null)
    
    page.fragment = "myfragment"
    Mockito.verify(vaadinPage).setFragment("myfragment")
  }
  
  test("browserWindowSize") {
    Mockito.when(vaadinPage.getBrowserWindowWidth).thenReturn(1300)
    Mockito.when(vaadinPage.getBrowserWindowHeight).thenReturn(950)
    
    assert(page.browserWindowSize === (1300, 950))
    
    page.browserWindowSize = (1300, 950)
    Mockito.verify(vaadinPage).setBrowserWindowSize(1300, 950)
  }
  
  test("browserWindowHeight") {
    Mockito.when(vaadinPage.getBrowserWindowHeight).thenReturn(950)
    assert(page.browserWindowHeight === 950)
    
    page.browserWindowHeight = 950
    Mockito.verify(vaadinPage).setBrowserWindowSize(0, 950)
  }
  
  test("browserWindowWidth") {
    Mockito.when(vaadinPage.getBrowserWindowWidth).thenReturn(1300)
    assert(page.browserWindowWidth === 1300)
    
    page.browserWindowWidth = 1300
    Mockito.verify(vaadinPage).setBrowserWindowSize(1300, 0)
  }
}