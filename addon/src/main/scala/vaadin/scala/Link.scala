package vaadin.scala

object Link {
  object TargetBorder extends Enumeration {
    import com.vaadin.ui.Link._

    val none = Value(TARGET_BORDER_NONE)
    val minimal = Value(TARGET_BORDER_MINIMAL)
    val default = Value(TARGET_BORDER_DEFAULT)
  }
}

class Link(implicit wrapper: WrapperRegistry) extends AbstractComponent {

  override val p = new com.vaadin.ui.Link()
  wr.put(this)

  def this(caption: String = null, resource: Resource = null, targetName: String = null, targetWidth: Int = -1, targetHeight: Int = -1, targetBorder: Link.TargetBorder.Value = Link.TargetBorder.default)(implicit wrapper: WrapperRegistry) = {
    this()
    p.setCaption(caption)
    if (resource != null) p.setResource(resource.p)
    p.setTargetName(targetName)
    p.setTargetWidth(targetWidth)
    p.setTargetHeight(targetHeight)
    p.setTargetBorder(targetBorder.id)
  }

  def targetName = Option(p.getTargetName)
  def targetName_=(targetName: Option[String]) = p.setTargetName(caption.getOrElse(null))
  def targetName_=(targetName: String) = p.setTargetName(targetName)

  def targetBorder = Link.TargetBorder(p.getTargetBorder)
  def targetBorder_=(targetBorder: Link.TargetBorder.Value) = p.setTargetBorder(targetBorder.id)

  def targetWidth = p.getTargetWidth()
  def targetWidth_=(targetWidth: Int) = p.setTargetWidth(targetWidth)

  def targetHeight = p.getTargetHeight()
  def targetHeight_=(targetHeight: Int) = p.setTargetHeight(targetHeight)

  def resource: Option[Resource] = wr.get[Resource](p.getResource)
  def resource_=(resource: Option[Resource]) = if (resource.isDefined) p.setResource(resource.get.p) else p.setResource(null)
  def resource_=(resource: Resource) = if (resource == null) p.setResource(null) else p.setResource(resource.p)
}