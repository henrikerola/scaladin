package vaadin.scala.internal

import vaadin.scala.server.ScaladinUIProvider
import com.vaadin.server.UIProviderEvent

class DefaultScaladinUIProvider extends ScaladinUIProvider {

  override protected def getScaladinUIClass(e: UIProviderEvent): Class[_] = {
    val classLoader = Some(e.getService.getClassLoader).getOrElse(getClass.getClassLoader)
    Class.forName(getUiClassName(e), true, classLoader)
  }
}