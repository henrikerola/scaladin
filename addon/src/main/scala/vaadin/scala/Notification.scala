package vaadin.scala

object Notification {

  object Type extends Enumeration {
    import com.vaadin.ui.Window.Notification._
    val Humanized = Value(TYPE_HUMANIZED_MESSAGE)
    val Warning = Value(TYPE_WARNING_MESSAGE)
    val Error = Value(TYPE_ERROR_MESSAGE)
    val Tray = Value(TYPE_TRAY_NOTIFICATION)
  }

  object Position extends Enumeration {
    import com.vaadin.ui.Window.Notification._
    val Centered = Value(POSITION_CENTERED)
    val CenteredTop = Value(POSITION_CENTERED_TOP)
    val CenteredBottom = Value(POSITION_CENTERED_BOTTOM)
    val TopLeft = Value(POSITION_TOP_LEFT)
    val TopRight = Value(POSITION_TOP_RIGHT)
    val BottomLeft = Value(POSITION_BOTTOM_LEFT)
    val BottomRight = Value(POSITION_BOTTOM_RIGHT)
  }

  // TODO: Delay constants

  def apply(caption: String): Notification = {
    new Notification(new com.vaadin.ui.Window.Notification(caption))
  }

  def apply(caption: String, notificationType: Notification.Type.Value): Notification = {
    new Notification(new com.vaadin.ui.Window.Notification(caption, notificationType.id))
  }

  def apply(caption: String, description: String): Notification = {
    new Notification(new com.vaadin.ui.Window.Notification(caption, description))
  }

  def apply(caption: String, description: String, notificationType: Notification.Type.Value): Notification = {
    new Notification(new com.vaadin.ui.Window.Notification(caption, description, notificationType.id))
  }

  def apply(caption: String, description: String, notificationType: Notification.Type.Value, htmlContentAllowed: Boolean): Notification = {
    new Notification(new com.vaadin.ui.Window.Notification(caption, description, notificationType.id, htmlContentAllowed))
  }

}

/**
 * @see com.vaadin.ui.Window.Notification
 * @author Henri Kerola / Vaadin
 */
class Notification(val p: com.vaadin.ui.Window.Notification = new com.vaadin.ui.Window.Notification(null)) extends Wrapper {

  def caption = Option(p.getCaption)
  def caption_=(caption: Option[String]) = p.setCaption(caption.orNull)
  def caption_=(caption: String) = p.setCaption(caption)

  def description = Option(p.getDescription)
  def description_=(description: Option[String]) = p.setDescription(description.orNull)
  def description_=(description: String) = p.setDescription(description)

  def position = Notification.Position(p.getPosition)
  def position_=(position: Notification.Position.Value) = p.setPosition(position.id)

  def icon: Option[Resource] = wrapperFor[Resource](p.getIcon)
  def icon_=(icon: Option[Resource]) = if (icon.isDefined) p.setIcon(icon.get.p) else p.setIcon(null)
  def icon_=(icon: Resource) = p.setIcon(icon.p)

  def delayMsec = p.getDelayMsec
  def delayMsec_=(delayMsec: Int) = p.setDelayMsec(delayMsec)

  def styleName = Option(p.getStyleName)
  def styleName_=(styleName: Option[String]) = p.setStyleName(styleName.orNull)
  def styleName_=(styleName: String) = p.setStyleName(styleName)

  def htmlContentAllowed = p.isHtmlContentAllowed
  def htmlContentAllowed_=(htmlContentAllowed: Boolean) = p.setHtmlContentAllowed(htmlContentAllowed)

}