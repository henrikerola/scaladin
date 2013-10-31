package vaadin.scala

import vaadin.scala.mixins.ComponentMixin
import vaadin.scala.mixins.ComponentContainerMixin

object ScaladinWrapper {
  def wrapComponent[P <: com.vaadin.ui.Component with ComponentMixin](p: P): ScaladinComponentWrapper[P] = new ScaladinComponentWrapper(p)
  def wrapComponentContainer[P <: com.vaadin.ui.ComponentContainer with ComponentContainerMixin](p: P): ScaladinComponentContainerWrapper[P] = new ScaladinComponentContainerWrapper(p)

  //TODO need to investigate using macros for this in 2.10. 
  //Still, It would be somewhat hacky
  //  def componentWrapper[P <: com.vaadin.ui.Component](implicit m: ClassManifest[P]): Component = new ScaladinComponentWrapper(m.erasure.newInstance.asInstanceOf[P] with ComponentMixin)
  //  def componentContainerWrapper[P <: com.vaadin.ui.ComponentContainer](implicit m: ClassManifest[P]): ComponentContainer = new ScaladinComponentContainerWrapper(m.erasure.newInstance.asInstanceOf[P] with ComponentContainerMixin)
}

class ScaladinComponentWrapper[P <: com.vaadin.ui.Component with ComponentMixin](override val p: P) extends Component {
  p.wrapper = this
}

class ScaladinComponentContainerWrapper[P <: com.vaadin.ui.ComponentContainer with ComponentContainerMixin](override val p: P) extends ComponentContainer {
  p.wrapper = this
}