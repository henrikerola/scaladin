package vaadin.scala.internal

import com.vaadin.server.{ UIProviderEvent, DefaultUIProvider, UIClassSelectionEvent, UICreateEvent }
import collection.mutable

class ScaladinUIProvider extends DefaultUIProvider {

  private val uiMap = mutable.Map.empty[String, vaadin.scala.UI]

  private def getUiClassName(e: UIProviderEvent) =
    e.getService.getDeploymentConfiguration.getInitParameters.getProperty("ScaladinUI")

  private def createScaladinUiInstance(e: UIProviderEvent): vaadin.scala.UI = {
    val classLoader = Some(e.getService.getClassLoader).getOrElse(getClass.getClassLoader)
    Class.forName(getUiClassName(e), true, classLoader).newInstance.asInstanceOf[vaadin.scala.UI]
  }

  private def getScaladinUiInstance(e: UIProviderEvent) =
    uiMap.getOrElseUpdate(getUiClassName(e), createScaladinUiInstance(e))

  override def createInstance(e: UICreateEvent): com.vaadin.ui.UI =
    createScaladinUiInstance(e).p.asInstanceOf[com.vaadin.ui.UI]

  override def getUIClass(e: UIClassSelectionEvent): Class[_ <: com.vaadin.ui.UI] = classOf[WrappedVaadinUI]

  override def getPageTitle(e: UICreateEvent) = getScaladinUiInstance(e).title.orNull

  override def getWidgetset(e: UICreateEvent) = getScaladinUiInstance(e).widgetset.orNull

  override def getTheme(e: UICreateEvent) = getScaladinUiInstance(e).theme

  override def isPreservedOnRefresh(e: UICreateEvent) = getScaladinUiInstance(e).preserveOnRefresh

  override def getPushMode(e: UICreateEvent) = com.vaadin.shared.communication.PushMode.values.apply(getScaladinUiInstance(e).pushMode.id)

}