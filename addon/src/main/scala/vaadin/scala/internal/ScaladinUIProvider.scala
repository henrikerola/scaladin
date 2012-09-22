package vaadin.scala.internal

import com.vaadin.server.DefaultUIProvider

class ScaladinUIProvider extends DefaultUIProvider {

  override def createInstance(request: com.vaadin.server.VaadinRequest, clazz: Class[_ <: com.vaadin.ui.UI]): com.vaadin.ui.UI = {
    val className = request.getVaadinService().getDeploymentConfiguration().getInitParameters().getProperty("ScaladinUI")
    val classLoader = Some(request.getVaadinService.getClassLoader).getOrElse(getClass.getClassLoader)
    val instance = Class.forName(className, true, classLoader).newInstance.asInstanceOf[vaadin.scala.UI]
    instance.p.asInstanceOf[com.vaadin.ui.UI]
  }

  override def getUIClass(request: com.vaadin.server.VaadinRequest): Class[_ <: com.vaadin.ui.UI] = classOf[WrappedVaadinUI]

}