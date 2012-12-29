package vaadin.scala

import vaadin.scala.mixins.LinkMixin

package mixins {
  trait LinkMixin extends AbstractComponentMixin
}

object Link {
  object TargetBorder extends Enumeration {
    import com.vaadin.ui.Link._

    val None = Value(TARGET_BORDER_NONE)
    val Minimal = Value(TARGET_BORDER_MINIMAL)
    val Default = Value(TARGET_BORDER_DEFAULT)
  }
}

class Link(override val p: com.vaadin.ui.Link with LinkMixin = new com.vaadin.ui.Link with LinkMixin) extends AbstractComponent(p) {

  def targetName: Option[String] = Option(p.getTargetName)
  def targetName_=(targetName: Option[String]): Unit = p.setTargetName(caption.orNull)
  def targetName_=(targetName: String): Unit = p.setTargetName(targetName)

  def targetBorder: Link.TargetBorder.Value = Link.TargetBorder(p.getTargetBorder)
  def targetBorder_=(targetBorder: Link.TargetBorder.Value): Unit = p.setTargetBorder(targetBorder.id)

  def targetWidth: Int = p.getTargetWidth
  def targetWidth_=(targetWidth: Int): Unit = p.setTargetWidth(targetWidth)

  def targetHeight: Int = p.getTargetHeight
  def targetHeight_=(targetHeight: Int): Unit = p.setTargetHeight(targetHeight)

  def resource: Option[Resource] = wrapperFor[Resource](p.getResource)
  def resource_=(resource: Option[Resource]): Unit = if (resource.isDefined) p.setResource(resource.get.p) else p.setResource(null)
  def resource_=(resource: Resource): Unit = p.setResource(resource.p)
}