package vaadin.scala.server

import com.vaadin.server.{ UIProviderEvent, DefaultUIProvider, UIClassSelectionEvent, UICreateEvent }
import collection.mutable
import vaadin.scala.internal.WrappedVaadinUI

object UIConfiguration extends Enumeration {
  type ConfigurationType = Value
  val Title, Widgetset, Theme, PreservedOnRefresh, PushMode = Value
}

abstract class ScaladinUIProvider extends DefaultUIProvider {

  import UIConfiguration._

  private val uiMap = mutable.Map.empty[String, vaadin.scala.UI]
  // (ui-class, config key) -> config value
  private val configurationCache = mutable.Map.empty[Tuple2[String, UIConfiguration.ConfigurationType], Any]

  protected def getUiClassName(e: UIProviderEvent) =
    e.getService.getDeploymentConfiguration.getInitParameters.getProperty("ScaladinUI")

  protected def createScaladinUiInstance(e: UIProviderEvent): vaadin.scala.UI = {
    getScaladinUIClass(e).newInstance.asInstanceOf[vaadin.scala.UI]
  }

  protected def getScaladinUIClass(e: UIProviderEvent): Class[_]

  private def getConfigurationUiInstance(e: UIProviderEvent) =
    uiMap.getOrElseUpdate(getUiClassName(e), createScaladinUiInstance(e))

  private def getConfigurationValue[T](configType: ConfigurationType, e: UICreateEvent): T = configurationCache.getOrElseUpdate((getUiClassName(e), configType), loadConfigValue(configType, e)).asInstanceOf[T]

  private def loadConfigValue(configType: ConfigurationType, e: UICreateEvent) = configType match {
    case Title => getConfigurationUiInstance(e).title.orNull
    case Widgetset => getConfigurationUiInstance(e).widgetset.orNull
    case Theme => getConfigurationUiInstance(e).theme
    case PreservedOnRefresh => getConfigurationUiInstance(e).preserveOnRefresh
    case PushMode => com.vaadin.shared.communication.PushMode.values.apply(getConfigurationUiInstance(e).pushMode.id)
  }

  override def createInstance(e: UICreateEvent): com.vaadin.ui.UI = {
    uiMap.get(getUiClassName(e)) match {
      case Some(cachedClass) => uiMap.remove(getUiClassName(e)).get.p.asInstanceOf[com.vaadin.ui.UI]
      case None => createScaladinUiInstance(e).p.asInstanceOf[com.vaadin.ui.UI]
    }
  }

  override def getUIClass(e: UIClassSelectionEvent): Class[_ <: com.vaadin.ui.UI] = classOf[WrappedVaadinUI]

  override def getPageTitle(e: UICreateEvent): String = getConfigurationValue(Title, e)

  override def getWidgetset(e: UICreateEvent): String = getConfigurationValue(Widgetset, e)

  override def getTheme(e: UICreateEvent): String = getConfigurationValue(Theme, e)

  override def isPreservedOnRefresh(e: UICreateEvent): Boolean = getConfigurationValue(PreservedOnRefresh, e)

  override def getPushMode(e: UICreateEvent): com.vaadin.shared.communication.PushMode = getConfigurationValue(PushMode, e)

}