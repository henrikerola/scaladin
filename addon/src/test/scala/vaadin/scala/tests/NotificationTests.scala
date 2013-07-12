package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import org.scalatest.BeforeAndAfter
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito
import vaadin.scala.server.ThemeResource

@RunWith(classOf[JUnitRunner])
class NotificationTests extends FunSuite with BeforeAndAfter with MockitoSugar {

  var vaadinNotificationSpy: com.vaadin.ui.Notification = _
  var notification: Notification = _

  before {
    vaadinNotificationSpy = Mockito.spy(new com.vaadin.ui.Notification(null))
    notification = new Notification(vaadinNotificationSpy)
  }

  test("Types") {
    assert(Notification.Type.Humanized.id === com.vaadin.ui.Notification.Type.HUMANIZED_MESSAGE.ordinal)
    assert(Notification.Type.Warning.id === com.vaadin.ui.Notification.Type.WARNING_MESSAGE.ordinal)
    assert(Notification.Type.Error.id === com.vaadin.ui.Notification.Type.ERROR_MESSAGE.ordinal)
    assert(Notification.Type.Tray.id === com.vaadin.ui.Notification.Type.TRAY_NOTIFICATION.ordinal)
  }

  test("Positions") {
    assert(Notification.Position.TopLeft.id === com.vaadin.shared.Position.TOP_LEFT.ordinal)
    assert(Notification.Position.TopCenter.id === com.vaadin.shared.Position.TOP_CENTER.ordinal)
    assert(Notification.Position.TopRight.id === com.vaadin.shared.Position.TOP_RIGHT.ordinal)

    assert(Notification.Position.MiddleLeft.id === com.vaadin.shared.Position.MIDDLE_LEFT.ordinal)
    assert(Notification.Position.MiddleCenter.id === com.vaadin.shared.Position.MIDDLE_CENTER.ordinal)
    assert(Notification.Position.MiddleRight.id === com.vaadin.shared.Position.MIDDLE_RIGHT.ordinal)

    assert(Notification.Position.BottomLeft.id === com.vaadin.shared.Position.BOTTOM_LEFT.ordinal)
    assert(Notification.Position.BottomCenter.id === com.vaadin.shared.Position.BOTTOM_CENTER.ordinal)
    assert(Notification.Position.BottomRight.id === com.vaadin.shared.Position.BOTTOM_RIGHT.ordinal)
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
    assert(notification.position === Notification.Position.MiddleCenter)

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