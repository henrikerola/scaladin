package vaadin.scala.internal

import com.vaadin.server.{ UIProviderEvent, DefaultUIProvider, UIClassSelectionEvent, UICreateEvent }
import collection.mutable
import scala.reflect.ClassTag
import com.vaadin.annotations.PreserveOnRefresh
import vaadin.scala.UI

object UIConfiguration extends Enumeration {
  type ConfigurationType = Value
  val Title, Widgetset, Theme, PreservedOnRefresh, PushMode = Value
}

class ScaladinUIProvider extends DefaultUIProvider {

  import UIConfiguration._

  private val uiMap = mutable.Map.empty[String, vaadin.scala.UI]
  // (ui-class, config key) -> config value
  private val configurationCache = mutable.Map.empty[Tuple2[String, UIConfiguration.ConfigurationType], Any]

  private def getUiClassName(e: UIProviderEvent) =
    e.getService.getDeploymentConfiguration.getInitParameters.getProperty("ScaladinUI")

  private def createScaladinUiInstance(e: UIProviderEvent): vaadin.scala.UI = {
    val classLoader = Some(e.getService.getClassLoader).getOrElse(getClass.getClassLoader)
    Class.forName(getUiClassName(e), true, classLoader).newInstance.asInstanceOf[vaadin.scala.UI]
  }

  private def getConfigurationUiInstance(e: UIProviderEvent) =
    uiMap.getOrElseUpdate(getUiClassName(e), createScaladinUiInstance(e))

  private def getConfigurationValue[T](configType: ConfigurationType, e: UICreateEvent): T = configurationCache.getOrElseUpdate((getUiClassName(e), configType), loadConfigValue(configType, e)).asInstanceOf[T]

  private def loadConfigValue(configType: ConfigurationType, e: UICreateEvent) = configType match {
    case Title => getConfigurationUiInstance(e).title.orNull
    case Widgetset => getConfigurationUiInstance(e).widgetset.orNull
    case Theme => getConfigurationUiInstance(e).theme
    case PreservedOnRefresh => getConfigurationUiInstance(e).preserveOnRefresh
    case PushMode => com.vaadin.shared.communication.PushMode.values.apply(getConfigurationUiInstance(e).pushConfiguration.pushMode.id)
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