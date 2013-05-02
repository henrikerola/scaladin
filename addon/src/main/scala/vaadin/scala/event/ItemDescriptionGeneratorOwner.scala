package vaadin.scala.event

import vaadin.scala.internal.ItemDescriptionGenerator

// TODO: correct package?
trait ItemDescriptionGeneratorOwner {

  self: {
    def p: {
      def getItemDescriptionGenerator(): com.vaadin.ui.AbstractSelect.ItemDescriptionGenerator
      def setItemDescriptionGenerator(generator: com.vaadin.ui.AbstractSelect.ItemDescriptionGenerator)
    }
  } =>

  def itemDescriptionGenerator: Option[ItemDescriptionEvent => String] = p.getItemDescriptionGenerator match {
    case null => None
    case generator: ItemDescriptionGenerator => Some(generator.action)
  }

  def itemDescriptionGenerator_=(generator: ItemDescriptionEvent => String) {
    p.setItemDescriptionGenerator(new ItemDescriptionGenerator(generator))
  }

  def itemDescriptionGenerator_=(generator: Option[ItemDescriptionEvent => String]) {
    generator match {
      case None => p.setItemDescriptionGenerator(null)
      case Some(generator) => itemDescriptionGenerator = generator
    }
  }

}