package vaadin.scala

/**
 * @see com.vaadin.server.DeploymentConfiguration
 * @author Henri Kerola / Vaadin
 */
trait DeploymentConfiguration {

  val p: com.vaadin.server.DeploymentConfiguration

  def isProductionMode: Boolean = p.isProductionMode

  def isXsrfProtectionEnabled: Boolean = p.isXsrfProtectionEnabled

  def resourceCacheTime: Int = p.getResourceCacheTime

  def heartbeatInterval: Int = p.getHeartbeatInterval

  def isCloseIdleSessions: Boolean = p.isCloseIdleSessions

  def initParameters: java.util.Properties = p.getInitParameters

  def getApplicationOrSystemProperty(propertyName: String, defaultValue: String): String =
    p.getApplicationOrSystemProperty(propertyName, defaultValue)
}
