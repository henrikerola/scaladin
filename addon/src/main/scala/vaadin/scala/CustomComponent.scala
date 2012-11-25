package vaadin.scala

import vaadin.scala.mixins.CustomComponentMixin

package mixins {
  trait CustomComponentMixin extends com.vaadin.ui.CustomComponent with AbstractComponentMixin with HasComponentsMixin {

    // make public so the wrapper can access:
    override def getCompositionRoot: com.vaadin.ui.Component = super.getCompositionRoot
    override def setCompositionRoot(root: com.vaadin.ui.Component): Unit = super.setCompositionRoot(root)
  }
}

/**
 * @see com.vaadin.ui.CustomComponent
 * @author Henri Kerola / Vaadin
 */
// Note that extends AbstractComponent instead of AbstractComponentContainer
class CustomComponent(override val p: com.vaadin.ui.CustomComponent with CustomComponentMixin = new com.vaadin.ui.CustomComponent with CustomComponentMixin)
  extends AbstractComponent(p) with HasComponents {

  protected def compositionRoot = wrapperFor[Component](p.getCompositionRoot)
  protected def compositionRoot_=(compositionRoot: Component) = p.setCompositionRoot(compositionRoot.p)
}