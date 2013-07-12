package vaadin.scala

import com.vaadin.ui.{ Notification => VaadinNotification }
import com.vaadin.shared.{ Position => VaadinPosition }
import vaadin.scala.server.Resource

object Notification {

  object Type extends Enumeration {
    import VaadinNotification.Type._
    val Humanized = Value(HUMANIZED_MESSAGE.ordinal)
    val Warning = Value(WARNING_MESSAGE.ordinal)
    val Error = Value(ERROR_MESSAGE.ordinal)
    val Tray = Value(TRAY_NOTIFICATION.ordinal)
  }

  object Position extends Enumeration {
    import com.vaadin.shared.Position._
    val TopLeft = Value(TOP_LEFT.ordinal)
    val TopCenter = Value(TOP_CENTER.ordinal)
    val TopRight = Value(TOP_RIGHT.ordinal)
    val MiddleLeft = Value(MIDDLE_LEFT.ordinal)
    val MiddleCenter = Value(MIDDLE_CENTER.ordinal)
    val MiddleRight = Value(MIDDLE_RIGHT.ordinal)
    val BottomLeft = Value(BOTTOM_LEFT.ordinal)
    val BottomCenter = Value(BOTTOM_CENTER.ordinal)
    val BottomRight = Value(BOTTOM_RIGHT.ordinal)
  }

  object Delay {
    val Forever = VaadinNotification.DELAY_FOREVER
    val None = VaadinNotification.DELAY_NONE
  }

  def apply(caption: String): Notification = {
    new Notification(new VaadinNotification(caption))
  }

  def apply(caption: String, notificationType: Notification.Type.Value): Notification = {
    new Notification(new VaadinNotification(caption, VaadinNotification.Type.values.apply(notificationType.id)))
  }

  def apply(caption: String, description: String): Notification = {
    new Notification(new VaadinNotification(caption, description))
  }

  def apply(caption: String, description: String, notificationType: Notification.Type.Value): Notification = {
    val t = VaadinNotification.Type.values.apply(notificationType.id)
    new Notification(new VaadinNotification(caption, description, t))
  }

  def apply(caption: String,
            description: String,
            notificationType: Notification.Type.Value,
            htmlContentAllowed: Boolean): Notification = {
    val t = VaadinNotification.Type.values.apply(notificationType.id)
    new Notification(new VaadinNotification(caption, description, t, htmlContentAllowed))
  }

  def show(caption: String) {
    VaadinNotification.show(caption)
  }

  def show(caption: String, notificationType: Notification.Type.Value) {
    VaadinNotification.show(caption, VaadinNotification.Type.values.apply(notificationType.id))
  }

}

/**
 * @see com.vaadin.ui.Window.Notification
 * @author Henri Kerola / Vaadin
 */
class Notification(val p: VaadinNotification = new VaadinNotification(null)) extends Wrapper {

  def caption: Option[String] = Option(p.getCaption)
  def caption_=(caption: Option[String]) { p.setCaption(caption.orNull) }
  def caption_=(caption: String) { p.setCaption(caption) }

  def description: Option[String] = Option(p.getDescription)
  def description_=(description: Option[String]) { p.setDescription(description.orNull) }
  def description_=(description: String) { p.setDescription(description) }

  def position = Notification.Position(p.getPosition.ordinal)
  def position_=(position: Notification.Position.Value) { p.setPosition(VaadinPosition.values.apply(position.id)) }

  def icon: Option[Resource] = { wrapperFor(p.getIcon) }
  def icon_=(icon: Option[Resource]) { p.setIcon(peerFor(icon)) }
  def icon_=(icon: Resource) { p.setIcon(icon.p) }

  def delayMsec: Int = p.getDelayMsec
  def delayMsec_=(delayMsec: Int) { p.setDelayMsec(delayMsec) }

  def styleName: Option[String] = Option(p.getStyleName)
  def styleName_=(styleName: Option[String]) { p.setStyleName(styleName.orNull) }
  def styleName_=(styleName: String) { p.setStyleName(styleName) }

  def htmlContentAllowed: Boolean = p.isHtmlContentAllowed
  def htmlContentAllowed_=(htmlContentAllowed: Boolean) { p.setHtmlContentAllowed(htmlContentAllowed) }

  def show(page: com.vaadin.server.Page) { p.show(page) }

}