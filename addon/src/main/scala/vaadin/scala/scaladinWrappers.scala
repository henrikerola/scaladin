package vaadin.scala

import vaadin.scala.mixins.ComponentMixin
import vaadin.scala.mixins.ComponentContainerMixin

object ScaladinWrapper {
  def apply(p: com.vaadin.ui.Component with ComponentMixin): Component = new ScaladinComponentWrapper(p)
  def apply(p: com.vaadin.ui.ComponentContainer with ComponentContainerMixin): ComponentContainer = new ScaladinComponentContainerWrapper(p)

  //TODO need to investigate using macros for this in 2.10. 
  //Still, It would be somewhat hacky
  //  def componentWrapper[P <: com.vaadin.ui.Component](implicit m: ClassManifest[P]): Component = new ScaladinComponentWrapper(m.erasure.newInstance.asInstanceOf[P] with ComponentMixin)
  //  def componentContainerWrapper[P <: com.vaadin.ui.ComponentContainer](implicit m: ClassManifest[P]): ComponentContainer = new ScaladinComponentContainerWrapper(m.erasure.newInstance.asInstanceOf[P] with ComponentContainerMixin)
}

class ScaladinComponentWrapper(override val p: com.vaadin.ui.Component with ComponentMixin) extends Component
class ScaladinComponentContainerWrapper(override val p: com.vaadin.ui.ComponentContainer with ComponentContainerMixin) extends ComponentContainer