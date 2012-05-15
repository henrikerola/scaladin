package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import org.scalatest.BeforeAndAfter
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import vaadin.scala.mixins.WindowMixin
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito

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
  
  test("showNotification(caption)") {
    window.showNotification("Caption")
    Mockito.verify(vaadinWindowSpy).showNotification("Caption")
  }
  
  test("showNotification(caption, notificationType)") {
    window.showNotification("Caption", Notification.Type.Warning)
    Mockito.verify(vaadinWindowSpy).showNotification("Caption", com.vaadin.ui.Window.Notification.TYPE_WARNING_MESSAGE)
  }
  
  test("showNotification(caption, description)") {
    window.showNotification("Caption", "Some text...")
    Mockito.verify(vaadinWindowSpy).showNotification("Caption", "Some text...")
  }
  
  test("showNotification(caption, description, notificationType)") {
    window.showNotification("Caption", "Some text...", Notification.Type.Warning)
    Mockito.verify(vaadinWindowSpy).showNotification("Caption", "Some text...", com.vaadin.ui.Window.Notification.TYPE_WARNING_MESSAGE)
  }
  
  test("showNotification(caption, description, notificationType, htmlContentAllowed)") {
    window.showNotification("Caption", "Some text...", Notification.Type.Warning, true)
    Mockito.verify(vaadinWindowSpy).showNotification("Caption", "Some text...", com.vaadin.ui.Window.Notification.TYPE_WARNING_MESSAGE, true)
  }
  
  test("showNotification(notification)") {
    val notification = Notification("Caption")
    window.showNotification(notification)
    Mockito.verify(vaadinWindowSpy).showNotification(notification.p)
  }
}