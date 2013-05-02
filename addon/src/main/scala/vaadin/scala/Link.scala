package vaadin.scala

import vaadin.scala.mixins.LinkMixin
import com.vaadin.shared.ui.BorderStyle
import com.vaadin.shared.ui.BorderStyle._

package mixins {
  trait LinkMixin extends AbstractComponentMixin { self: com.vaadin.ui.Link => }
}

object Link {
  object TargetBorder extends Enumeration {
    val None = Value(NONE.ordinal)
    val Minimal = Value(MINIMAL.ordinal)
    val Default = Value(DEFAULT.ordinal)
  }
}

class Link(override val p: com.vaadin.ui.Link with LinkMixin = new com.vaadin.ui.Link with LinkMixin)
    extends AbstractComponent(p) {

  def targetName: Option[String] = Option(p.getTargetName)
  def targetName_=(targetName: Option[String]) { p.setTargetName(caption.orNull) }
  def targetName_=(targetName: String) { p.setTargetName(targetName) }

  def targetBorder: Link.TargetBorder.Value = Link.TargetBorder(p.getTargetBorder.ordinal)
  def targetBorder_=(targetBorder: Link.TargetBorder.Value) {
    p.setTargetBorder(BorderStyle.values.apply(targetBorder.id))
  }

  def targetWidth: Int = p.getTargetWidth
  def targetWidth_=(targetWidth: Int) { p.setTargetWidth(targetWidth) }

  def targetHeight: Int = p.getTargetHeight
  def targetHeight_=(targetHeight: Int) { p.setTargetHeight(targetHeight) }

  def resource: Option[Resource] = wrapperFor(p.getResource)
  def resource_=(resource: Option[Resource]) { p.setResource(peerFor(resource)) }
  def resource_=(resource: Resource) { p.setResource(resource.p) }
}