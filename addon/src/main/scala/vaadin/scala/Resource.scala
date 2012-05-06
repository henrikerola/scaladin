package vaadin.scala

trait Resource extends Wrapper {

  def p: com.vaadin.terminal.Resource

  def mimeType = p.getMIMEType
}

class ExternalResource(override val p: com.vaadin.terminal.ExternalResource) extends Resource {
  WrapperRegistry.put(this)

  def this(sourceUrl: String, mimeType: String = null) {
    this(new com.vaadin.terminal.ExternalResource(sourceUrl, mimeType))
  }

  def url = p.getURL

}

class ThemeResource(override val p: com.vaadin.terminal.ThemeResource) extends Resource {
  WrapperRegistry.put(this)

  def this(resourceId: String) {
    this(new com.vaadin.terminal.ThemeResource(resourceId))
  }
}