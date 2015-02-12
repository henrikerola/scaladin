package vaadin.scala.server

import vaadin.scala.PushMode

/**
 * @see com.vaadin.server.DeploymentConfiguration
 * @author Henri Kerola / Vaadin
 */
trait DeploymentConfiguration {

  val p: com.vaadin.server.DeploymentConfiguration

  def isProductionMode: Boolean = p.isProductionMode

  def isXsrfProtectionEnabled: Boolean = p.isXsrfProtectionEnabled

  def isSyncIdCheckEnabled: Boolean = p.isSyncIdCheckEnabled

  def resourceCacheTime: Int = p.getResourceCacheTime

  def heartbeatInterval: Int = p.getHeartbeatInterval

  def isSendUrlsAsParameters: Boolean = p.isSendUrlsAsParameters

  def isCloseIdleSessions: Boolean = p.isCloseIdleSessions

  def pushMode: PushMode.Value = PushMode(p.getPushMode.ordinal)

  def initParameters: java.util.Properties = p.getInitParameters

  def getApplicationOrSystemProperty(propertyName: String, defaultValue: String): String =
    p.getApplicationOrSystemProperty(propertyName, defaultValue)

  def uiClassName: Option[String] = Option(getApplicationOrSystemProperty("ScaladinUI", null))

  def uiProviderClassName: Option[String] = Option(getApplicationOrSystemProperty("ScaladinUIProvider", null))

  def widgetset: Option[String] = Option(p.getWidgetset(null))

  def resourcesPath: Option[String] = Option(p.getResourcesPath)

  def classLoaderName: Option[String] = Option(p.getClassLoaderName)

}
