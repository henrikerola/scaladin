package vaadin.scala

import vaadin.scala.mixins.AbstractSelectMixin
import vaadin.scala.mixins.AbstractFieldMixin
import vaadin.scala.mixins.ContainerMixin
import vaadin.scala.mixins.ContainerViewerMixin
import vaadin.scala.internal.WrapperUtil
import vaadin.scala.mixins.NewItemHandlerMixin

package mixins {
  trait AbstractSelectMixin extends AbstractFieldMixin with ContainerMixin with ContainerViewerMixin
  trait NewItemHandlerMixin extends TypedScaladinMixin[NewItemHandler]
}

object AbstractSelect {

  object ItemCaptionMode extends Enumeration {
    import com.vaadin.ui.AbstractSelect._
    val Id = Value(ITEM_CAPTION_MODE_ID)
    val Item = Value(ITEM_CAPTION_MODE_ITEM)
    val Index = Value(ITEM_CAPTION_MODE_INDEX)
    val ExplicitDefaultsId = Value(ITEM_CAPTION_MODE_EXPLICIT_DEFAULTS_ID)
    val Explicit = Value(ITEM_CAPTION_MODE_EXPLICIT)
    val IconOnly = Value(ITEM_CAPTION_MODE_ICON_ONLY)
    val Property = Value(ITEM_CAPTION_MODE_PROPERTY)
  }
}

abstract class AbstractSelect(override val p: com.vaadin.ui.AbstractSelect with AbstractSelectMixin)
    extends AbstractField(p) with Container with Container.Viewer {

  //initial setup of the default newItemHandler
  newItemHandler = new DefaultNewItemHandler(this)

  def newItemHandler: Option[NewItemHandler] = WrapperUtil.wrapperFor[NewItemHandler](p.getNewItemHandler)
  def newItemHandler_=(newItemHandler: NewItemHandler): Unit = p.setNewItemHandler(newItemHandler.p)
  def newItemHandler_=(newItemHandler: Option[NewItemHandler]): Unit = if (newItemHandler.isDefined) p.setNewItemHandler(newItemHandler.get.p) else p.setNewItemHandler(null)

  def itemCaptionMode = AbstractSelect.ItemCaptionMode(p.getItemCaptionMode)
  def itemCaptionMode_=(itemCaptionMode: AbstractSelect.ItemCaptionMode.Value) = p.setItemCaptionMode(itemCaptionMode.id)

  def itemCaptionPropertyId: Option[Any] = Option(p.getItemCaptionPropertyId)
  def itemCaptionPropertyId_=(itemCaptionPropertyId: Option[Any]) = p.setItemCaptionPropertyId(itemCaptionPropertyId.orNull)
  def itemCaptionPropertyId_=(itemCaptionPropertyId: Any) = p.setItemCaptionPropertyId(itemCaptionPropertyId)

  def itemIconPropertyId: Option[Any] = Option(p.getItemIconPropertyId)
  def itemIconPropertyId_=(itemIconPropertyId: Option[Any]) = p.setItemIconPropertyId(itemIconPropertyId.orNull)
  def itemIconPropertyId_=(itemIconPropertyId: Any) = p.setItemIconPropertyId(itemIconPropertyId)

  def nullSelectionAllowed = p.isNullSelectionAllowed
  def nullSelectionAllowed_=(nullSelectionAllowed: Boolean) = p.setNullSelectionAllowed(nullSelectionAllowed)

  def nullSelectionItemId: Option[Any] = Option(p.getNullSelectionItemId)
  def nullSelectionItemId_=(nullSelectionItemId: Option[Any]) = p.setNullSelectionItemId(nullSelectionItemId.orNull)
  def nullSelectionItemId_=(nullSelectionItemId: Any) = p.setNullSelectionItemId(nullSelectionItemId)

  def selected(itemId: Any) = p.isSelected(itemId)
  def select(itemId: Any) = p.select(itemId)
  def unselect(itemId: Any) = p.unselect(itemId)

  // Container.Container:
  protected def wrapItem(unwrapped: com.vaadin.data.Item): Item = new IndexedContainerItem(unwrapped)
}

trait NewItemHandler extends Wrapper {
  override val p: com.vaadin.ui.AbstractSelect.NewItemHandler with NewItemHandlerMixin = new NewItemHandlerDelegator
  p.wrapper = this

  def addNewItem(newItemCaption: String): Unit
}

class NewItemHandlerDelegator extends com.vaadin.ui.AbstractSelect.NewItemHandler with NewItemHandlerMixin {
  def addNewItem(newItemCaption: String): Unit = wrapper.addNewItem(newItemCaption)
}

//copied here because original is a non-static inner class
class DefaultNewItemHandler(select: AbstractSelect) extends NewItemHandler {
  def addNewItem(newItemCaption: String) = {
    if (select.readOnly) throw new com.vaadin.data.Property.ReadOnlyException()

    if (select.addItem(newItemCaption).isDefined) {

      // Sets the caption property, if used
      if (select.itemCaptionPropertyId.isDefined) {
        try {
          select.property(newItemCaption, select.itemCaptionPropertyId.get).get.value = newItemCaption
        } catch {
          case ignored: com.vaadin.data.Property.ConversionException =>
          // The conversion exception is safely ignored, the caption is just missing
        }
      }
      if (select.isInstanceOf[MultiSelectable] && select.asInstanceOf[MultiSelectable].multiSelect) {
        var values: Set[Any] = select.value.get.asInstanceOf[Iterable[Any]].toSet
        values += newItemCaption
        select.value = values
      } else {
        select.value = newItemCaption
      }
    }
  }
}
