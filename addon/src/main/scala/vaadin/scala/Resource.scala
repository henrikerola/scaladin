package vaadin.scala

trait Resource extends Wrapper {
  
  def p: com.vaadin.terminal.Resource
  
  def mimeType = p.getMIMEType
}

class ExternalResource(sourceUrl: String, mimeType: String = null) extends Resource {
  
  override val p = new com.vaadin.terminal.ExternalResource(sourceUrl, mimeType)
  WrapperRegistry.put(this)
  
  def url = p.getURL
}

class ThemeResource(resourceId: String) extends Resource {
  
  override val p = new com.vaadin.terminal.ThemeResource(resourceId)
  WrapperRegistry.put(this)
}