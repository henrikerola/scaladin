package vaadin.scala.tests

import vaadin.scala.converter.Converter
import vaadin.scala.server.mixins.VaadinSessionMixin
import com.vaadin.server.{ BootstrapPageResponse, BootstrapFragmentResponse }
import org.mockito.Mockito
import org.jsoup.nodes.Element
import org.jsoup.parser.Tag
import vaadin.scala.server.ScaladinSession

class ScaladinSessionTests extends ScaladinTestSuite {

  var vaadinSession: com.vaadin.server.VaadinSession with VaadinSessionMixin = _
  var session: ScaladinSession = _

  before {
    vaadinSession = new com.vaadin.server.VaadinSession(null) with VaadinSessionMixin
    session = new ScaladinSession(vaadinSession)
  }

  test("bootstrapFragmentListeners: modifying nodes should modify original Java List") {
    val mockedResponse = mock[BootstrapFragmentResponse]
    val list = mock[java.util.List[org.jsoup.nodes.Node]]
    Mockito.when(mockedResponse.getSession).thenReturn(vaadinSession)
    Mockito.when(mockedResponse.getFragmentNodes).thenReturn(list)

    val element = new Element(Tag.valueOf("div"), "")
    session.bootstrapFragmentListeners += { e =>
      e.nodes += element
    }

    vaadinSession.modifyBootstrapResponse(mockedResponse)

    Mockito.verify(list).add(element)
  }

  test("bootstrapPageListeners: adding headers") {
    val mockedResponse = mock[BootstrapPageResponse]
    Mockito.when(mockedResponse.getSession).thenReturn(vaadinSession)

    session.bootstrapPageListeners += { e =>
      e.headers += "key" -> "value"
    }

    vaadinSession.modifyBootstrapResponse(mockedResponse)

    Mockito.verify(mockedResponse).setHeader("key", "value")
  }

  test("converterFactory") {

    val myConverterFactory = new vaadin.scala.converter.ConverterFactory {
      override def createConverter[Presentation, Model](implicit presentationType: ClassManifest[Presentation], modelType: ClassManifest[Model]): Option[Converter[Presentation, Model]] = ???
    }

    session.converterFactory = myConverterFactory
    assert(myConverterFactory === session.converterFactory)
  }

}