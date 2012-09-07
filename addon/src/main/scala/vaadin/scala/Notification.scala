package vaadin.scala

object Notification {

  object Type extends Enumeration {
    import com.vaadin.ui.Notification.Type._
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

  // TODO: Delay constants

  def apply(caption: String): Notification = {
    new Notification(new com.vaadin.ui.Notification(caption))
  }

  def apply(caption: String, notificationType: Notification.Type.Value): Notification = {
    new Notification(new com.vaadin.ui.Notification(caption, com.vaadin.ui.Notification.Type.values.apply(notificationType.id)))
  }

  def apply(caption: String, description: String): Notification = {
    new Notification(new com.vaadin.ui.Notification(caption, description))
  }

  def apply(caption: String, description: String, notificationType: Notification.Type.Value): Notification = {
    new Notification(new com.vaadin.ui.Notification(caption, description, com.vaadin.ui.Notification.Type.values.apply(notificationType.id)))
  }

  def apply(caption: String, description: String, notificationType: Notification.Type.Value, htmlContentAllowed: Boolean): Notification = {
    new Notification(new com.vaadin.ui.Notification(caption, description, com.vaadin.ui.Notification.Type.values.apply(notificationType.id), htmlContentAllowed))
  }

  def show(caption: String) = com.vaadin.ui.Notification.show(caption)
  def show(caption: String, notificationType: Notification.Type.Value) = com.vaadin.ui.Notification.show(caption, com.vaadin.ui.Notification.Type.values.apply(notificationType.id))

}

/**
 * @see com.vaadin.ui.Window.Notification
 * @author Henri Kerola / Vaadin
 */
class Notification(val p: com.vaadin.ui.Notification = new com.vaadin.ui.Notification(null)) extends Wrapper {

  def caption = Option(p.getCaption)
  def caption_=(caption: Option[String]) = p.setCaption(caption.orNull)
  def caption_=(caption: String) = p.setCaption(caption)

  def description = Option(p.getDescription)
  def description_=(description: Option[String]) = p.setDescription(description.orNull)
  def description_=(description: String) = p.setDescription(description)

  def position = Notification.Position(p.getPosition.ordinal)
  def position_=(position: Notification.Position.Value) = p.setPosition(com.vaadin.shared.Position.values.apply(position.id))

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

  def show(page: com.vaadin.server.Page) = p.show(page)

}