package vaadin.scala

import vaadin.scala.mixins.ComponentMixin

trait Focusable extends Component {

  def p: com.vaadin.ui.Component.Focusable with ComponentMixin

  def focus() { p.focus() }

  def tabIndex: Int = p.getTabIndex
  def tabIndex_=(tabIndex: Int) { p.setTabIndex(tabIndex) }
}
