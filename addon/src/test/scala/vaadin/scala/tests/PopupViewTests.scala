package vaadin.scala.tests

import vaadin.scala._
import com.vaadin.server.PaintTarget
import org.mockito.Mockito
import org.mockito.ArgumentCaptor

class PopupViewTests extends ScaladinTestSuite {

  var popupView: PopupView = _

  before {
    popupView = new PopupView
  }

  test("popupVisible") {
    popupView.popupContent = new Label // popupVisible = true requires that popup component has been set 
    
    assert(popupView.popupVisible === false)

    popupView.popupVisible = true
    assert(popupView.popupVisible === true)
  }

  test("hideOnMouseOut") {
    assert(popupView.hideOnMouseOut === true)

    popupView.hideOnMouseOut = false
    assert(popupView.hideOnMouseOut === false)
  }

  test("minimizedHtmlValue") {
    val label = new Label
    assert(popupView.minimizedHtmlValue === "")

    popupView.popupContent = label
    popupView.minimizedHtmlValue = "test"
    assert(popupView.minimizedHtmlValue === "test")
    assert(popupView.popupContent === Some(label))
  }

  test("popupContent") {
    val label = new Label
    assert(popupView.popupContent === None)

    popupView.minimizedHtmlValue = "test"
    popupView.popupContent = label
    assert(popupView.minimizedHtmlValue === "test")
    assert(popupView.popupContent === Some(label))
  }

  ignore("painting should not throw StackOverflowError") {
    //val label = new Label
    //popupView.popupContent = label
    //popupView.p.paintContent(mock[PaintTarget])
  }

  test("popupContent_=(() => component) should init component lazily when popup gets visible") {
    var cnt = 0
    class MyLabel extends Label { cnt = cnt + 1 }

    popupView.popupContent = () => new MyLabel
    assert(cnt === 0)

    popupView.popupVisible = true
    assert(cnt === 1)
    popupView.popupVisible = false
    popupView.popupVisible = true
    assert(cnt === 2)
  }
  
  test("PopupVisibilityEvent") {
    class PopupVisibilityListenerFunction extends Function1[PopupView.PopupVisibilityEvent, Unit] {
      def apply(x: PopupView.PopupVisibilityEvent) {}
    }
    
    popupView.popupContent = new Label

    val spy = Mockito.spy(new PopupVisibilityListenerFunction)
    popupView.popupVisibilityListeners += spy
    
    popupView.popupVisible = true

    val argument = ArgumentCaptor.forClass(classOf[PopupView.PopupVisibilityEvent]);
    Mockito.verify(spy).apply(argument.capture())
    assert(argument.getValue.popupView === popupView)
    assert(argument.getValue.popupVisible)
  }

}