package vaadin.scala

import vaadin.scala.mixins.ResourceMixin
import java.io.File

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

object ExternalResource {
  def apply(sourceUrl: String): ExternalResource = new ExternalResource(sourceUrl)
  def apply(sourceUrl: String, mimeType: String): ExternalResource = new ExternalResource(sourceUrl, mimeType)
}

class ExternalResource(override val p: com.vaadin.server.ExternalResource with ResourceMixin) extends Resource {

  def this(sourceUrl: String, mimeType: String = null) {
    this(new com.vaadin.server.ExternalResource(sourceUrl, mimeType) with ResourceMixin)
  }

  def url = p.getURL

}

object ThemeResource {
  def apply(resourceId: String): ThemeResource = new ThemeResource(resourceId)
}

class ThemeResource(override val p: com.vaadin.server.ThemeResource with ResourceMixin) extends Resource {

  def this(resourceId: String) {
    this(new com.vaadin.server.ThemeResource(resourceId) with ResourceMixin)
  }
}

object FileResource {
  def apply(sourceFile: File): FileResource = new FileResource(sourceFile)
}

// TODO: should extend ApplicationResource
class FileResource(override val p: com.vaadin.server.FileResource with ResourceMixin) extends Resource {

  def this(sourceFile: File) {
    this(new com.vaadin.server.FileResource(sourceFile) with ResourceMixin)
  }
}