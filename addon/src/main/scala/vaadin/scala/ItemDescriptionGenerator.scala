package vaadin.scala

// TODO: move traits and classes to correct packages

case class ItemDescriptionEvent(component: Component, itemId: Any, propertyId: Any) extends Event

trait ItemDescriptionGenerator { self: { def p: { def getItemDescriptionGenerator(): com.vaadin.ui.AbstractSelect.ItemDescriptionGenerator; def setItemDescriptionGenerator(generator: com.vaadin.ui.AbstractSelect.ItemDescriptionGenerator); } } =>
  def itemDescriptionGenerator: Option[ItemDescriptionEvent => String] = p.getItemDescriptionGenerator match {
    case null => None
    case generator: ScaladinItemDescriptionGenerator => Some(generator.action)
  }

  def itemDescriptionGenerator_=(generator: ItemDescriptionEvent => String): Unit = {
    p.setItemDescriptionGenerator(new ScaladinItemDescriptionGenerator(generator))
  }

  def itemDescriptionGenerator_=(generator: Option[ItemDescriptionEvent => String]): Unit = generator match {
    case None => p.setItemDescriptionGenerator(null)
    case Some(generator) => itemDescriptionGenerator = generator
  }

}

// FIXME: should not extend Listener?
class ScaladinItemDescriptionGenerator(val action: ItemDescriptionEvent => String) extends com.vaadin.ui.AbstractSelect.ItemDescriptionGenerator with Listener {
  def generateDescription(source: com.vaadin.ui.Component, itemId: Any, propertyId: Any) = action(ItemDescriptionEvent(wrapperFor[Component](source).get, itemId, propertyId))
}