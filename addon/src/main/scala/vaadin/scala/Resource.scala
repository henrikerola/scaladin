package vaadin.scala

import vaadin.scala.mixins.ResourceMixin

package mixins {
  trait ResourceMixin extends ScaladinMixin
}

object Resource {
  def mapResource(vaadinResource: Option[com.vaadin.terminal.Resource]): Option[Resource] =
    vaadinResource map {
      _ match {
        case er: com.vaadin.terminal.ExternalResource => new ExternalResource(er.getURL(), er.getMIMEType())
        case tr: com.vaadin.terminal.ThemeResource => new ThemeResource(tr.getResourceId())
        case _ => null
      }
    }
}

trait Resource extends Wrapper {

  def p: com.vaadin.terminal.Resource with ResourceMixin
  p.wrapper = this

  def mimeType = p.getMIMEType
}

object ExternalResource {
  def apply(sourceUrl: String): ExternalResource = new ExternalResource(sourceUrl)
  def apply(sourceUrl: String, mimeType: String): ExternalResource = new ExternalResource(sourceUrl, mimeType)
}

class ExternalResource(override val p: com.vaadin.terminal.ExternalResource with ResourceMixin) extends Resource {

  def this(sourceUrl: String, mimeType: String = null) {
    this(new com.vaadin.terminal.ExternalResource(sourceUrl, mimeType) with ResourceMixin)
  }

  def url = p.getURL

}

object ThemeResource {
  def apply(resourceId: String): ThemeResource = new ThemeResource(resourceId)
}

class ThemeResource(override val p: com.vaadin.terminal.ThemeResource with ResourceMixin) extends Resource {

  def this(resourceId: String) {
    this(new com.vaadin.terminal.ThemeResource(resourceId) with ResourceMixin)
  }
}