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

  test("resizable") {
    assert(window.resizable)

    window.resizable = false
    assert(!window.resizable)
  }

  test("child window handling") {
    val anotherWindow = new Window

    assert(window.childWindows.isEmpty)

    window.childWindows += anotherWindow

    assert(window.childWindows.contains(anotherWindow))

    window.childWindows -= anotherWindow

    assert(window.childWindows.isEmpty)
  }

  test("theme") {
    assert(window.theme === None)

    window.theme = "mytheme"
    assert(window.theme === Some("mytheme"))

    window.theme = None
    assert(window.theme === None)

    window.theme = Some("mytheme")
    assert(window.theme === Some("mytheme"))
  }

  test("center()") {
    window.center()
    Mockito.verify(vaadinWindowSpy).center()
  }

  test("modal") {
    assert(!window.modal)

    window.modal = false
    assert(!window.modal)
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

  test("name") {
    assert(window.name === None)

    window.name = "mywindow"
    assert(window.name === Some("mywindow"))

    window.name = None
    assert(window.name === None)

    window.name = Some("mywindow")
    assert(window.name === Some("mywindow"))
  }

  test("scrollIntoView") {
    val label = new Label
    window.addComponent(label)
    window.scrollIntoView(label)
    Mockito.verify(vaadinWindowSpy).scrollIntoView(label.p)
  }

  test("browserWindowHeight") {
    window.browserWindowHeight
    Mockito.verify(vaadinWindowSpy).getBrowserWindowHeight
  }

  test("browserWindowWidth") {
    window.browserWindowWidth
    Mockito.verify(vaadinWindowSpy).getBrowserWindowWidth
  }

  test("javascript") {
    window.executeJavaScript("javascript")
    Mockito.verify(vaadinWindowSpy).executeJavaScript("javascript")
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

  test("closeShortcut") {
    val window = new Window

    val clickShortcut = KeyShortcut(KeyCode.Enter, KeyModifier.Alt);

    assert(window.closeShortcut === None)

    window.closeShortcut = clickShortcut
    assert(window.closeShortcut === Some(clickShortcut))

    window.closeShortcut = None
    assert(window.closeShortcut === None)

    window.closeShortcut = Some(clickShortcut)
    assert(window.closeShortcut === Some(clickShortcut))
  }
}