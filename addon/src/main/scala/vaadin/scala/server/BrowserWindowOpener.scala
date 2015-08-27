package vaadin.scala.server

import com.vaadin.server.{ BrowserWindowOpener => VaadinBrowserWindowOpener, VaadinSession, UIProviderEvent }
import com.vaadin.shared.ApplicationConstants
import vaadin.scala.AbstractComponent
import vaadin.scala.server.BrowserWindowOpener.BrowserWindowOpenerUIProvider
import vaadin.scala.server.mixins.BrowserWindowOpenerMixin

package mixins {
  trait BrowserWindowOpenerMixin extends AbstractExtensionMixin {
    self: VaadinBrowserWindowOpener =>
  }
}

object BrowserWindowOpener {

  private class BrowserWindowOpenerUIProvider(uiClass: Class[_ <: vaadin.scala.UI], path: String)
      extends ScaladinUIProvider {

    private val pathWithInitialSlash = ensureInitialSlash(path)

    private def ensureInitialSlash(path: String): String = {
      if (path == null) null
      else if (!path.startsWith("/")) '/' + path
      else path
    }

    override protected def getScaladinUIClass(e: UIProviderEvent): Class[_] = {
      val p = e.getRequest.getPathInfo
      if (pathWithInitialSlash == p) uiClass
      else null
    }
  }

  def apply(uiClass: Class[_ <: vaadin.scala.UI]): BrowserWindowOpener = {
    apply(uiClass, "popup/" + uiClass.getSimpleName)
  }

  def apply(uiClass: Class[_ <: vaadin.scala.UI], path: String) = {
    val er = new com.vaadin.server.ExternalResource(ApplicationConstants.APP_PROTOCOL_PREFIX + path)
    val v = new VaadinBrowserWindowOpener(er) with BrowserWindowOpenerMixin
    new BrowserWindowOpener(v, new BrowserWindowOpenerUIProvider(uiClass, path))
  }

  def apply(url: String): BrowserWindowOpener = {
    val v = new VaadinBrowserWindowOpener(url) with BrowserWindowOpenerMixin
    new BrowserWindowOpener(v, null)
  }

  def apply(resource: Resource): BrowserWindowOpener = {
    val v = new VaadinBrowserWindowOpener(resource.pResource) with BrowserWindowOpenerMixin
    new BrowserWindowOpener(v, null)
  }

}

/**
 *
 * @author Henri Kerola / Vaadin
 */
class BrowserWindowOpener(override val p: VaadinBrowserWindowOpener with BrowserWindowOpenerMixin, private val uiProvider: BrowserWindowOpenerUIProvider)
    extends AbstractExtension(p) {

  def extend(target: AbstractComponent): Unit = p.extend(target.p)

  attachListeners += { e =>
    val session = VaadinSession.getCurrent
    if (uiProvider != null && !session.getUIProviders.contains(uiProvider)) {
      session.addUIProvider(uiProvider)
    }
  }

  detachListeners += { e =>
    if (uiProvider != null) {
      val session = VaadinSession.getCurrent
      session.removeUIProvider(uiProvider)
    }
  }
}
