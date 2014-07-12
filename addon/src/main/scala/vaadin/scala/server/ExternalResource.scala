package vaadin.scala.server

import vaadin.scala.server.mixins.ResourceMixin

object ExternalResource {
  def apply(sourceUrl: String): ExternalResource = new ExternalResource(sourceUrl)
  def apply(sourceUrl: String, mimeType: String): ExternalResource = new ExternalResource(sourceUrl, mimeType)
}

class ExternalResource(override val pResource: com.vaadin.server.ExternalResource with ResourceMixin) extends Resource {

  def this(sourceUrl: String, mimeType: String = null) {
    this(new com.vaadin.server.ExternalResource(sourceUrl, mimeType) with ResourceMixin)
  }

  def url = pResource.getURL

}
