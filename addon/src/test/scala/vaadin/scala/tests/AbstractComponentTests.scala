package vaadin.scala.tests

import vaadin.scala.AbstractComponent
import vaadin.scala.mixins.AbstractComponentMixin
import org.mockito.Mockito
import java.util.Locale

/**
 *
 * @author Henri Kerola / Vaadin
 */
class AbstractComponentTests extends ScaladinTestSuite {

  abstract class VaadinAbstractComponent extends com.vaadin.ui.AbstractComponent with AbstractComponentMixin

  var vaadinAbstractComponent: VaadinAbstractComponent = _
  var abstractComponent: AbstractComponent = _

  before {
    vaadinAbstractComponent = mock[VaadinAbstractComponent]
    abstractComponent = new AbstractComponent(vaadinAbstractComponent) {}
  }

  test("captionAsHtml") {
    assert(!abstractComponent.captionAsHtml)
    Mockito.verify(vaadinAbstractComponent).isCaptionAsHtml

    abstractComponent.captionAsHtml = true
    Mockito.verify(vaadinAbstractComponent).setCaptionAsHtml(true)
  }

  test("locale") {
    assert(abstractComponent.locale.isEmpty)

    val locale = Locale.ENGLISH
    Mockito.when(vaadinAbstractComponent.getLocale).thenReturn(locale)
    assert(Some(locale) === abstractComponent.locale)

    Mockito.reset(vaadinAbstractComponent)
    abstractComponent.locale = Some(Locale.US)
    Mockito.verify(vaadinAbstractComponent).setLocale(Locale.US)

    Mockito.reset(vaadinAbstractComponent)
    abstractComponent.locale = None
    Mockito.verify(vaadinAbstractComponent).setLocale(null)

    Mockito.reset(vaadinAbstractComponent)
    abstractComponent.locale = Locale.US
    Mockito.verify(vaadinAbstractComponent).setLocale(Locale.US)
  }

}
