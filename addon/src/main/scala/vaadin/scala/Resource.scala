package vaadin.scala

import vaadin.scala.mixins.ResourceMixin

package mixins {
  trait ResourceMixin extends ScaladinMixin
}

trait Resource extends Wrapper {

  def p: com.vaadin.terminal.Resource with ResourceMixin
  p.wrapper = this

  def mimeType = p.getMIMEType
}

class ExternalResource(override val p: com.vaadin.terminal.ExternalResource with ResourceMixin) extends Resource {

  def this(sourceUrl: String, mimeType: String = null) {
    this(new com.vaadin.terminal.ExternalResource(sourceUrl, mimeType) with ResourceMixin)
  }

  def url = p.getURL

}

class ThemeResource(override val p: com.vaadin.terminal.ThemeResource with ResourceMixin) extends Resource {

  def this(resourceId: String) {
    this(new com.vaadin.terminal.ThemeResource(resourceId) with ResourceMixin)
  }
}