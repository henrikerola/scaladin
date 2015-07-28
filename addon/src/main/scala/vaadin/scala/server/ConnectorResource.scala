package vaadin.scala.server

import vaadin.scala.server.mixins.ResourceMixin

/**
 *
 * @author Henri Kerola / Vaadin
 */
trait ConnectorResource extends Resource {

  def pResource: com.vaadin.server.ConnectorResource with ResourceMixin

  def fileName: String = pResource.getFilename

}
