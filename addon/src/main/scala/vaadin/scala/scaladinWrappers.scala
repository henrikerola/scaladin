package vaadin.scala

import vaadin.scala.mixins.ComponentMixin
import vaadin.scala.mixins.CustomComponentMixin
import vaadin.scala.mixins.ComponentContainerMixin

trait ScaladinComponentWrapper(override val p: com.vaadin.ui.ComponentContainer) extends Component

//trait ScaladinComponentContainerWrapper extends com.vaadin.ui.AbstractComponentContainer with ComponentContainer with ComponentContainerMixin {
//  override val p: this.type = this
//  p.wrapper = this
//  
//  override def removeAllComponents: Unit = p.removeAllComponents
//}

object ScaladinWrapper{
  def apply(p:com.vaadin.ui.ComponentContainer): ComponentContainer = new ScaladinComponentContainerWrapper(p)
  def create[P <: com.vaadin.ui.Component)(implicit m: ClassManifest[P]): Component= new ScaladinComponentWrapper(m.erasure.create)
}
class ScaladinComponentContainerWrapper(override val p: com.vaadin.ui.ComponentContainer) extends ComponentContainer