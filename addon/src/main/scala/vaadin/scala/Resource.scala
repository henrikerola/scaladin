package vaadin.scala

trait Resource extends Wrapper {

  def p: com.vaadin.terminal.Resource

  def mimeType = p.getMIMEType
}

class ExternalResource(sourceUrl: String, mimeType: String = null)(implicit wr: WrapperRegistry) extends Resource {

  override val p = new com.vaadin.terminal.ExternalResource(sourceUrl, mimeType)
  wr.put(this)

  def url = p.getURL
}

class ThemeResource(resourceId: String)(implicit wr: WrapperRegistry) extends Resource {

  override val p = new com.vaadin.terminal.ThemeResource(resourceId)
  wr.put(this)
}