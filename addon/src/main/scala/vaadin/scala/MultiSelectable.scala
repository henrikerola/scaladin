package vaadin.scala

trait MultiSelectable { self: AbstractSelect =>
  def multiSelect: Boolean = p.isMultiSelect
  def multiSelect_=(multiSelect: Boolean) { p.setMultiSelect(multiSelect) }
}