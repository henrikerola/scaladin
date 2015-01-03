package vaadin.scala.server

import vaadin.scala.{ InterfaceWrapper, Wrapper }
import vaadin.scala.mixins.ScaladinInterfaceMixin
import vaadin.scala.server.mixins.ResourceMixin

package mixins {
  trait ResourceMixin extends ScaladinInterfaceMixin
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

trait Resource extends InterfaceWrapper {

  def pResource: com.vaadin.server.Resource with ResourceMixin
  pResource.wrapper = this

  def mimeType = pResource.getMIMEType
}
