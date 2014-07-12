package vaadin.scala.server

import vaadin.scala.server.mixins.FontIconMixin

package mixins {
  trait FontIconMixin extends ResourceMixin
}

/**
 * @see com.vaadin.server.FontIcon
 * @author Henri Kerola / Vaadin
 */
trait FontIcon extends Resource {

  override def pResource: com.vaadin.server.FontIcon with FontIconMixin

  def html: String = pResource.getHtml

}