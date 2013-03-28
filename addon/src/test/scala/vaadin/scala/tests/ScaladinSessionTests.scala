package vaadin.scala.tests

import vaadin.scala.ScaladinSession
import vaadin.scala.mixins.VaadinSessionMixin
import com.vaadin.server.BootstrapFragmentResponse
import org.mockito.Mockito
import org.jsoup.nodes.Element
import org.jsoup.parser.Tag

class ScaladinSessionTests extends ScaladinTestSuite {

  test("bootstrapFragmentListeners: modifying nodes should modify original Java List") {
    val vaadinSession = new com.vaadin.server.VaadinSession(null) with VaadinSessionMixin
    val session = new ScaladinSession(vaadinSession)

    val mockedResponse = mock[BootstrapFragmentResponse]
    val list = mock[java.util.List[org.jsoup.nodes.Node]]

    Mockito.when(mockedResponse.getFragmentNodes).thenReturn(list)

    val element = new Element(Tag.valueOf("div"), "")
    session.bootstrapFragmentListeners += { e =>
      e.nodes += element
    }

    vaadinSession.modifyBootstrapResponse(mockedResponse)

    Mockito.verify(list).add(element)
  }

}