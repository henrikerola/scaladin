package vaadin.scala.internal

import com.vaadin.server.DefaultUIProvider
import com.vaadin.server.UIClassSelectionEvent
import com.vaadin.server.UICreateEvent

class ScaladinUIProvider extends DefaultUIProvider {

  override def createInstance(e: UICreateEvent): com.vaadin.ui.UI = {
    val className = e.getService.getDeploymentConfiguration.getInitParameters.getProperty("ScaladinUI")
    val classLoader = Some(e.getService.getClassLoader).getOrElse(getClass.getClassLoader)
    val instance = Class.forName(className, true, classLoader).newInstance.asInstanceOf[vaadin.scala.UI]
    instance.p.asInstanceOf[com.vaadin.ui.UI]
  }

  override def getUIClass(e: UIClassSelectionEvent): Class[_ <: com.vaadin.ui.UI] = classOf[WrappedVaadinUI]

}