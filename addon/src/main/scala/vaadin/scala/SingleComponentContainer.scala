package vaadin.scala

import vaadin.scala.mixins.SingleComponentContainerMixin

package mixins {
  trait SingleComponentContainerMixin extends HasComponentsMixin { self: com.vaadin.ui.SingleComponentContainer => }
}

/**
 *
 * @author Henri Kerola / Vaadin
 */
trait SingleComponentContainer extends HasComponents { // TODO: implements also ComponentAttachDetachNotifier

  def p: com.vaadin.ui.SingleComponentContainer with SingleComponentContainerMixin

  def componentCount: Int = p.getComponentCount

  def content: Option[Component] = wrapperFor[Component](p.getContent)
  def content_=(content: Component) { p.setContent(content.p) }
  // TODO: content_=(Option[Component]) ?
}
