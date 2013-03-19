package vaadin.scala

import vaadin.scala.mixins.AbstractComponentMixin

package mixins {
  trait AbstractComponentSuperCalls {
    def attach(): Unit
    def detach(): Unit
  }

  trait AbstractComponentMixin extends ComponentMixin with AbstractComponentSuperCalls {

    self: com.vaadin.ui.AbstractComponent =>

    override def wrapper = super.wrapper.asInstanceOf[AbstractComponent]

    abstract override def attach(): Unit = wrapper.attach()
    def __super__attach() { super.attach() }

    abstract override def detach(): Unit = wrapper.detach()
    def __super__detach() { super.detach() }
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

  def attach() { p.__super__attach() }

  def detach() { p.__super__detach() }

}

