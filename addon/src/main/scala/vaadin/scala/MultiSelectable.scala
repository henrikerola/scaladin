package vaadin.scala

trait MultiSelectable { e: AbstractSelect =>
  def multiSelect = p.isMultiSelect
  def multiSelect_=(multiSelect: Boolean) = p.setMultiSelect(multiSelect)
}