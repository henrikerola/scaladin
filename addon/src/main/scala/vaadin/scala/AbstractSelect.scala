package vaadin.scala

import vaadin.scala.mixins.AbstractSelectMixin
import vaadin.scala.mixins.AbstractFieldMixin
import vaadin.scala.mixins.ContainerMixin
import vaadin.scala.mixins.ContainerViewerMixin
import vaadin.scala.internal.WrapperUtil
import vaadin.scala.mixins.NewItemHandlerMixin

package mixins {
  trait AbstractSelectMixin extends AbstractFieldMixin[AnyRef] with ContainerMixin with ContainerViewerMixin {
    self: com.vaadin.ui.AbstractSelect =>
  }
  trait NewItemHandlerMixin extends TypedScaladinMixin[NewItemHandler]
}

object AbstractSelect {

  object ItemCaptionMode extends Enumeration {
    import com.vaadin.ui.AbstractSelect.ItemCaptionMode._
    val Id = Value(ID.ordinal)
    val Item = Value(ITEM.ordinal)
    val Index = Value(INDEX.ordinal)
    val ExplicitDefaultsId = Value(EXPLICIT_DEFAULTS_ID.ordinal)
    val Explicit = Value(EXPLICIT.ordinal)
    val IconOnly = Value(ICON_ONLY.ordinal)
    val Property = Value(PROPERTY.ordinal)
  }
}

abstract class AbstractSelect(override val p: com.vaadin.ui.AbstractSelect with AbstractSelectMixin)
    extends AbstractField[Object](p) with Container with Container.Viewer {

  // TODO: Move newItemHandler and newItemsAllowed to a trait because not
  // all subclasses of AbstractSelect support new items.

  //initial setup of the default newItemHandler
  newItemHandler = new DefaultNewItemHandler(this)
  container = new IndexedContainer

  def newItemHandler: Option[NewItemHandler] = WrapperUtil.wrapperFor[NewItemHandler](p.getNewItemHandler)
  def newItemHandler_=(newItemHandler: NewItemHandler): Unit = p.setNewItemHandler(newItemHandler.p)
  def newItemHandler_=(newItemHandler: Option[NewItemHandler]): Unit = p.setNewItemHandler(peerFor(newItemHandler))

  def newItemsAllowed: Boolean = p.isNewItemsAllowed
  def newItemsAllowed_=(newItemsAllowed: Boolean): Unit = p.setNewItemsAllowed(newItemsAllowed)

  def itemCaptionMode: AbstractSelect.ItemCaptionMode.Value =
    AbstractSelect.ItemCaptionMode(p.getItemCaptionMode.ordinal)
  def itemCaptionMode_=(itemCaptionMode: AbstractSelect.ItemCaptionMode.Value) {
    p.setItemCaptionMode(com.vaadin.ui.AbstractSelect.ItemCaptionMode.values.apply(itemCaptionMode.id))
  }

  def itemCaptionPropertyId: Option[Any] = Option(p.getItemCaptionPropertyId)
  def itemCaptionPropertyId_=(itemCaptionPropertyId: Option[Any]) {
    p.setItemCaptionPropertyId(itemCaptionPropertyId.orNull)
  }
  def itemCaptionPropertyId_=(itemCaptionPropertyId: Any) { p.setItemCaptionPropertyId(itemCaptionPropertyId) }

  def getItemCaption(item: Any): String = p.getItemCaption(item)
  def setItemCaption(item: Any, caption: Option[String]) { p.setItemCaption(item, caption.orNull) }
  def setItemCaption(item: Any, caption: String) { p.setItemCaption(item, caption) }

  def itemIconPropertyId: Option[Any] = Option(p.getItemIconPropertyId)
  def itemIconPropertyId_=(itemIconPropertyId: Option[Any]) { p.setItemIconPropertyId(itemIconPropertyId.orNull) }
  def itemIconPropertyId_=(itemIconPropertyId: Any) { p.setItemIconPropertyId(itemIconPropertyId) }

  def getItemIcon(item: Any): Option[Resource] = wrapperFor(p.getItemIcon(item))
  def setItemIcon(item: Any, icon: Option[Resource]) { p.setItemIcon(item, peerFor(icon)) }
  def setItemIcon(item: Any, icon: Resource) { p.setItemIcon(item, icon.p) }

  def nullSelectionAllowed: Boolean = p.isNullSelectionAllowed
  def nullSelectionAllowed_=(nullSelectionAllowed: Boolean) { p.setNullSelectionAllowed(nullSelectionAllowed) }

  def nullSelectionItemId: Option[Any] = Option(p.getNullSelectionItemId)
  def nullSelectionItemId_=(nullSelectionItemId: Option[Any]) { p.setNullSelectionItemId(nullSelectionItemId.orNull) }
  def nullSelectionItemId_=(nullSelectionItemId: Any) { p.setNullSelectionItemId(nullSelectionItemId) }

  def isSelected(itemId: Any): Boolean = p.isSelected(itemId)

  def select(itemId: Any) { p.select(itemId) }

  def unselect(itemId: Any) { p.unselect(itemId) }

  // Container.Container:
  def wrapItem(unwrapped: com.vaadin.data.Item): Item = container.get.wrapItem(unwrapped)
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
        //try {
        select.property(newItemCaption, select.itemCaptionPropertyId.get).get.value = newItemCaption
        //} catch {
        // TODO
        //case ignored: com.vaadin.data.Property.ConversionException =>
        // The conversion exception is safely ignored, the caption is just missing
        //}
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
