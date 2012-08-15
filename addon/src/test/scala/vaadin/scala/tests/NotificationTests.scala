package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import org.scalatest.BeforeAndAfter
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito

@RunWith(classOf[JUnitRunner])
class NotificationTests extends FunSuite with BeforeAndAfter with MockitoSugar {

  var vaadinNotificationSpy: com.vaadin.ui.Notification = _
  var notification: Notification = _

  before {
    vaadinNotificationSpy = Mockito.spy(new com.vaadin.ui.Notification(null))
    notification = new Notification(vaadinNotificationSpy)
  }
  
  test("Types") {
    assert(Notification.Type.Humanized.id === com.vaadin.ui.Notification.TYPE_HUMANIZED_MESSAGE)
    assert(Notification.Type.Warning.id === com.vaadin.ui.Notification.TYPE_WARNING_MESSAGE)
    assert(Notification.Type.Error.id === com.vaadin.ui.Notification.TYPE_ERROR_MESSAGE)
    assert(Notification.Type.Tray.id === com.vaadin.ui.Notification.TYPE_TRAY_NOTIFICATION)
  }
  
  test("Positions") {
    assert(Notification.Position.Centered.id === com.vaadin.ui.Notification.POSITION_CENTERED)
    assert(Notification.Position.CenteredTop.id === com.vaadin.ui.Notification.POSITION_CENTERED_TOP)
    assert(Notification.Position.CenteredBottom.id === com.vaadin.ui.Notification.POSITION_CENTERED_BOTTOM)
    assert(Notification.Position.TopLeft.id === com.vaadin.ui.Notification.POSITION_TOP_LEFT)
    assert(Notification.Position.TopRight.id === com.vaadin.ui.Notification.POSITION_TOP_RIGHT)
    assert(Notification.Position.BottomLeft.id === com.vaadin.ui.Notification.POSITION_BOTTOM_LEFT)
    assert(Notification.Position.BottomRight.id === com.vaadin.ui.Notification.POSITION_BOTTOM_RIGHT)
  }

  test("Notification(caption)") {
    val notification: Notification = Notification("Caption")
    assert(notification.caption === Some("Caption"))
    assert(notification.styleName === None)
    assert(!notification.htmlContentAllowed)
  }

  test("Notification(caption, notificationType)") {
    val notification: Notification = Notification("Caption", Notification.Type.Error)
    assert(notification.caption === Some("Caption"))
    assert(notification.styleName === Some("error"))
    assert(!notification.htmlContentAllowed)
  }

  test("Notification(caption, description)") {
    val notification: Notification = Notification("Caption", "Some text...")
    assert(notification.caption === Some("Caption"))
    assert(notification.styleName === None)
    assert(notification.description === Some("Some text..."))
    assert(!notification.htmlContentAllowed)
  }

  test("Notification(caption, description, notificationType)") {
    val notification: Notification = Notification("Caption", "Some text...", Notification.Type.Error)
    assert(notification.caption === Some("Caption"))
    assert(notification.styleName === Some("error"))
    assert(notification.description === Some("Some text..."))
    assert(!notification.htmlContentAllowed)
  }

  test("Notification(caption, description, notificationType, htmlContentAllowed)") {
    val notification: Notification = Notification("Caption", "Some text...", Notification.Type.Error, true)
    assert(notification.caption === Some("Caption"))
    assert(notification.styleName === Some("error"))
    assert(notification.description === Some("Some text..."))
    assert(notification.htmlContentAllowed)
  }
  
  test("caption") {
    assert(notification.caption === None)
    
    
    notification.caption = "123"
    assert(notification.caption === Some("123"))
    
    notification.caption = None
    assert(notification.caption === None)
    
    notification.caption = Some("1234")
    assert(notification.caption === Some("1234"))
  }
  
  test("description") {
    assert(notification.description === None)
    
    
    notification.description = "123"
    assert(notification.description === Some("123"))
    
    notification.description = None
    assert(notification.description === None)
    
    notification.description = Some("1234")
    assert(notification.description === Some("1234"))
  }
  
  test("position") {
    assert(notification.position === Notification.Position.Centered)
    
    notification.position = Notification.Position.TopLeft
    assert(notification.position === Notification.Position.TopLeft)
  }
  
  test("icon") {
    assert(notification.icon === None)

    val icon = new ThemeResource("foobar.png")

    notification.icon = icon
    assert(notification.icon === Some(icon))

    notification.icon = None
    assert(notification.icon === None)

    notification.icon = Some(icon)
    assert(notification.icon === Some(icon))
  }
  

  test("delayMsec") {
    assert(notification.delayMsec === 0)

    notification.delayMsec = 999
    assert(notification.delayMsec === 999)
  }
  
  test("styleName") {
    assert(notification.styleName === None)

    notification.styleName = "style1 style2"
    assert(notification.styleName === Some("style1 style2"))

    notification.styleName = None
    assert(notification.styleName === None)

    notification.styleName = Some("style1 style2")
    assert(notification.styleName === Some("style1 style2"))
  }
  
  test("htmlContentAllowed") {
    assert(!notification.htmlContentAllowed)
    notification.htmlContentAllowed = true
    assert(notification.htmlContentAllowed)
  }
  
}