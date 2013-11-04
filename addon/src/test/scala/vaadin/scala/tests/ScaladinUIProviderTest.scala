package vaadin.scala.tests

import vaadin.scala.server.ScaladinUIProvider
import com.vaadin.server.UICreateEvent
import com.vaadin.server.VaadinRequest
import vaadin.scala._
import org.mockito.Mockito._
import vaadin.scala.internal.DefaultScaladinUIProvider

class ScaladinUIProviderTest extends ScaladinTestSuite {

  test("configuration value caching") {
    val provider = new DefaultScaladinUIProvider
    val mockRequest = mock[VaadinRequest]
    val event = mock[UICreateEvent](RETURNS_DEEP_STUBS)

    when(event.getService.getDeploymentConfiguration.getInitParameters.getProperty("ScaladinUI")).thenReturn(classOf[UIProviderTestUI].getCanonicalName())
    when(event.getService.getClassLoader).thenReturn(this.getClass().getClassLoader())

    val title = provider.getPageTitle(event)
    assert("title" === title)
    assert("theme" === provider.getTheme(event))
    assert("widgetset" === provider.getWidgetset(event))
    assert(true === provider.isPreservedOnRefresh(event))
    assert(com.vaadin.shared.communication.PushMode.AUTOMATIC === provider.getPushMode(event))

    assert(1 === UIProviderTestUI.created)

    provider.createInstance(event)

    assert(1 === UIProviderTestUI.created)

    assert("theme" === provider.getTheme(event))

    assert(1 === UIProviderTestUI.created)
  }
}

object UIProviderTestUI {
  var created = 0
}

class UIProviderTestUI() extends UI(title = "title", theme = "theme", widgetset = "widgetset", preserveOnRefresh = true, pushMode = PushMode.Automatic) {

  override def delayedInit(body: => Unit) = {
    UIProviderTestUI.created = UIProviderTestUI.created + 1
    super.delayedInit(body)
  }
}