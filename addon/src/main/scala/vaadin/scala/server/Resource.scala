package vaadin.scala.server

import vaadin.scala.Wrapper
import vaadin.scala.mixins.ScaladinMixin
import vaadin.scala.server.mixins.ResourceMixin

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
