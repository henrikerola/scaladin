package vaadin.scala.tests

import vaadin.scala._
import vaadin.scala.event.DetachEvent
import org.mockito.Mockito._
import org.mockito.Matchers._
import vaadin.scala.internal.WrappedVaadinUI

class ConnectorTests extends ScaladinTestSuite {

  var connector: ClientConnector = _

  before {
    connector = new TextField
  }

  test("DetachListener add/remove") {
    val listenerMock: DetachEvent => Unit = mock[Function1[DetachEvent, Unit]]

    assert(connector.detachListeners.size === 0)
    connector.detachListeners += listenerMock
    assert(connector.detachListeners.size === 1)
    connector.detachListeners -= listenerMock
    assert(connector.detachListeners.size === 0)
  }

  test("detaching a ClientConnector calls DetachListeners") {
    //setup
    val mockScaladinUI = mock[UI]
    val mockVaadinUI = mock[WrappedVaadinUI]
    val mockConnectionTracker = mock[com.vaadin.ui.ConnectorTracker]

    when(mockScaladinUI.p).thenReturn(mockVaadinUI)
    when(mockVaadinUI.getConnectorTracker()).thenReturn(mockConnectionTracker)

    connector.parent = mockScaladinUI

    val listenerMock: DetachEvent => Unit = mock[Function1[DetachEvent, Unit]]
    connector.detachListeners += listenerMock

    connector.detach()

    verify(listenerMock).apply(any(classOf[DetachEvent]))
  }
}