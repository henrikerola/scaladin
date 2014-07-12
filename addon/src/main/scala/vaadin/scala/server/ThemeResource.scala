package vaadin.scala.server

import vaadin.scala.server.mixins.ResourceMixin

object ThemeResource {
  def apply(resourceId: String): ThemeResource = new ThemeResource(resourceId)
}

class ThemeResource(override val pResource: com.vaadin.server.ThemeResource with ResourceMixin) extends Resource {

  def this(resourceId: String) {
    this(new com.vaadin.server.ThemeResource(resourceId) with ResourceMixin)
  }
}
