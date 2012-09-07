package vaadin.scala

import vaadin.scala.mixins.ResourceMixin

package mixins {
  trait ResourceMixin extends ScaladinMixin
}

object Resource {
  def mapResource(vaadinResource: Option[com.vaadin.server.Resource]): Option[Resource] =
    vaadinResource map {
      _ match {
        case er: com.vaadin.server.ExternalResource => new ExternalResource(er.getURL(), er.getMIMEType())
        case tr: com.vaadin.server.ThemeResource => new ThemeResource(tr.getResourceId())
        case _ => null
      }
    }
}

trait Resource extends Wrapper {

  def p: com.vaadin.server.Resource with ResourceMixin
  p.wrapper = this

  def mimeType = p.getMIMEType
}

class ExternalResource(override val p: com.vaadin.server.ExternalResource with ResourceMixin) extends Resource {

  def this(sourceUrl: String, mimeType: String = null) {
    this(new com.vaadin.server.ExternalResource(sourceUrl, mimeType) with ResourceMixin)
  }

  def url = p.getURL

}

class ThemeResource(override val p: com.vaadin.server.ThemeResource with ResourceMixin) extends Resource {

  def this(resourceId: String) {
    this(new com.vaadin.server.ThemeResource(resourceId) with ResourceMixin)
  }
}