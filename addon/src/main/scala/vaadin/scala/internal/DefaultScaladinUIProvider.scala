package vaadin.scala.internal

import vaadin.scala.server.ScaladinUIProvider
import com.vaadin.server.UIProviderEvent

class DefaultScaladinUIProvider extends ScaladinUIProvider {

  protected def createScaladinUiInstance(e: UIProviderEvent): vaadin.scala.UI = {
    val classLoader = Some(e.getService.getClassLoader).getOrElse(getClass.getClassLoader)
    Class.forName(getUiClassName(e), true, classLoader).newInstance.asInstanceOf[vaadin.scala.UI]
  }
}