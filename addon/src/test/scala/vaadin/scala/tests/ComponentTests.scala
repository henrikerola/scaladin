package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import vaadin.scala.mixins.ComponentMixin
import org.scalatest.BeforeAndAfter
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito
import vaadin.scala.mixins.LabelMixin
import vaadin.scala.mixins.LabelMixin

@RunWith(classOf[JUnitRunner])
class ComponentTests extends FunSuite with BeforeAndAfter with MockitoSugar {

  abstract class VaadinComponent extends com.vaadin.ui.Component with ComponentMixin

  var component: Component = _
  var mockedVaadinComponent: VaadinComponent = _

  before {
    mockedVaadinComponent = mock[VaadinComponent]
    component = new Component {
      override val p = mockedVaadinComponent
    }
  }

  test("id") {
    Mockito.when(mockedVaadinComponent.getId).thenReturn("test-id")
    assert(component.id === Some("test-id"))
    Mockito.verify(mockedVaadinComponent).getId

    Mockito.reset(mockedVaadinComponent)

    Mockito.when(mockedVaadinComponent.getId).thenReturn(null)
    assert(component.id === None)
    Mockito.verify(mockedVaadinComponent).getId

    component.id = None
    Mockito.verify(mockedVaadinComponent).setId(null)

    component.id = "myId"
    Mockito.verify(mockedVaadinComponent).setId("myId")
  }

  ignore("requestRepaint()") {
    //component.requestRepaint()
    //Mockito.verify(mockedVaadinComponent).requestRepaint()
  }

  test("Link, default constructor") {
    val link = new Link
    assert(link.caption === None)
    assert(link.resource === None)
    assert(link.targetName === None)
    assert(link.targetWidth === -1)
    assert(link.targetHeight === -1)
    assert(link.targetBorder === Link.TargetBorder.Default)
  }

  test("Component.styleNames.contains") {
    val label = new Label
    label.styleNames += "style1"
    label.styleNames += "styleName2"

    assert(label.styleNames.contains("style1"))
  }

  test("Component.styleNames.iterator") {
    val label = new Label
    label.styleNames += "style1"
    label.styleNames += "styleName2"
    label.styleNames += "stylez"

    val iter = label.styleNames.iterator
    assert(iter.next === "style1")
    assert(iter.next === "styleName2")
    assert(iter.next === "stylez")
    assert(!iter.hasNext)
  }

  test("Label, default constructor") {
    val label = new Label
    label.styleNames += "style1"
    label.styleNames += "styleName2"

    assert(label.p.getStyleName === "style1 styleName2")
  }

  test("Component.styleNames +=, spaces are splitted") {
    val label = new Label
    label.styleNames += "  style1  "
    label.styleNames += "  styleName2    foobar  "
    label.styleNames += " stylez "

    assert(label.p.getStyleName === "style1 styleName2 foobar stylez")
    assert(label.styleNames.size === 4)
  }

  test("Component.styleNames -=") {
    val label = new Label
    label.styleNames += "style1"
    label.styleNames += "styleName2"
    label.styleNames += "stylez"

    label.styleNames -= "styleName2"

    assert(label.p.getStyleName === "style1 stylez")
  }

  test("Component.styleNames.size") {
    val label = new Label
    label.styleNames += "style1"
    label.styleNames += "style2"
    label.styleNames += "style3"

    assert(label.styleNames.size === 3)
  }

  test("width, defined size") {
    val label = new Label()
    label.width = 25 px

    assert(label.width.get === Measure(25, Units.px))
  }

  test("width, undefined size") {
    val label = new Label()
    label.width = None
    assert(label.width === None)
  }

  test("height, defined size") {
    val label = new Label()
    label.height = 25 px

    assert(label.height.get === Measure(25, Units.px))
  }

  test("height, undefined size") {
    val label = new Label()
    label.height = None
    assert(label.height === None)
  }

  test("sizeFull()") {
    val label = new Label
    label.sizeFull()
    assert(label.width.get === Measure(100, Units.pct))
    assert(label.height.get === Measure(100, Units.pct))
  }

  test("sizeUndefined()") {
    val label = new Label
    label.sizeUndefined()
    assert(label.width === None)
    assert(label.height === None)
  }

  test("size(width, height)") {
    val label = new Label
    label.size(Measure(200, Units.px), Measure(50, Units.pct))
    assert(label.width.get === Measure(200, Units.px))
    assert(label.height.get === Measure(50, Units.pct))
  }

  test("size(Option[width], Option[height])") {
    val label = new Label
    label.size(200 px, None)
    assert(label.width.get === Measure(200, Units.px))
    assert(label.height === None)
  }
}