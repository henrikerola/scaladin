package vaadin.scala

import vaadin.scala.mixins.AbstractComponentMixin

package mixins {
  trait AbstractComponentMixin extends ComponentMixin {

    self: com.vaadin.ui.AbstractComponent =>

    override def wrapper = super.wrapper.asInstanceOf[AbstractComponent]
  }
}

abstract class AbstractComponent(val p: com.vaadin.ui.AbstractComponent with AbstractComponentMixin)
    extends Component with Sizeable {

  p.wrapper = this

  def description: Option[String] = Option(p.getDescription)
  def description_=(description: Option[String]) { p.setDescription(description.orNull) }
  def description_=(description: String) { p.setDescription(description) }

  def immediate: Boolean = p.isImmediate
  def immediate_=(immediate: Boolean) { p.setImmediate(immediate) }

  def data: Any = p.getData
  def data_=(data: Any) { p.setData(data) }

  def markAsDirty() { p.markAsDirty() }
}

