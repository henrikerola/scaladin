package vaadin.scala

abstract class AbstractSelect(override val p: com.vaadin.ui.AbstractSelect) extends AbstractField(p) with Container.Viewer {

  // NewItemHandler

  def itemCaptionMode = ItemCaptionMode(p.getItemCaptionMode)
  def itemCaptionMode_=(itemCaptionMode: ItemCaptionMode.Value) = p.setItemCaptionMode(itemCaptionMode.id)

  def itemCaptionPropertyId: Option[Any] = Option(p.getItemCaptionPropertyId)
  def itemCaptionPropertyId_=(itemCaptionPropertyId: Option[Any]) = p.setItemCaptionPropertyId(itemCaptionPropertyId.getOrElse(null))
  def itemCaptionPropertyId_=(itemCaptionPropertyId: Any) = p.setItemCaptionPropertyId(itemCaptionPropertyId)

  def itemIconPropertyId: Option[Any] = Option(p.getItemIconPropertyId)
  def itemIconPropertyId_=(itemIconPropertyId: Option[Any]) = p.setItemIconPropertyId(itemIconPropertyId.getOrElse(null))
  def itemIconPropertyId_=(itemIconPropertyId: Any) = p.setItemIconPropertyId(itemIconPropertyId)

  def nullSelectionAllowed = p.isNullSelectionAllowed
  def nullSelectionAllowed_=(nullSelectionAllowed: Boolean) = p.setNullSelectionAllowed(nullSelectionAllowed)

  def nullSelectionItemId: Option[Any] = Option(p.getNullSelectionItemId)
  def nullSelectionItemId_=(nullSelectionItemId: Option[Any]) = p.setNullSelectionItemId(nullSelectionItemId.getOrElse(null))
  def nullSelectionItemId_=(nullSelectionItemId: Any) = p.setNullSelectionItemId(nullSelectionItemId)

  def selected(itemId: Any) = p.isSelected(itemId)
  def select(itemId: Any) = p.select(itemId)
  def unselect(itemId: Any) = p.unselect(itemId)
}