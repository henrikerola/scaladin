package vaadin.scala.tests
import org.scalatest.FunSuite
import vaadin.scala._
import com.vaadin.terminal.Sizeable

class ComponentContainerTests extends FunSuite {

  implicit val wr = new WrapperRegistry

  test("ComponentContainer.components.contains returns true for added Component") {
    val layout = new VerticalLayout

    val label = new Label
    layout.components += label

    assert(layout.components.contains(label))
  }

  test("ComponentContainer.components.contains return false for non-added Component") {
    val layout = new VerticalLayout

    layout.components += new Label

    assert(!layout.components.contains(new Label))
  }

  test("ComponentContainer.components.iterator returns added components") {
    val layout = new VerticalLayout

    val label1 = new Label
    layout.components += label1
    val label2 = new Label
    layout add label2

    val iter = layout.components.iterator
    assert(iter.next() == label1)
    assert(iter.next() == label2)
    assert(!iter.hasNext)
  }

  test("filtering empty component container returns empty list") {
    val layout = new VerticalLayout with FilterableComponentContainer[VerticalLayout]

    val result = layout filter (c => true)

    assert(result === Nil)
  }

  ignore("filtering component container with matches returns results") {
    val layout = new VerticalLayout with FilterableComponentContainer[VerticalLayout] {
      val one = add(new Label(style = "one"))
      val two = add(new Label(style = "two"))
      val three = add(new Label(style = "three"))
    }

    val result = layout filter (c => List("one", "two") contains c.getStyleName)
    assert(result contains layout.one)
    assert(result contains layout.two)
  }

  test("filtering component container with no matches returns empty list") {
    val layout = createTestLayout

    val result = layout filter (_.getStyleName == "four")

    assert(result === Nil)
  }

  ignore("filtering component container children with matches returns results") {
    val layout = createTestLayout
    layout.add(createTestLayout)

    val result = layout filterRecursive (_.getStyleName == "one")

    assert(result.size === 2)
    result.foreach(c => assert(c.isInstanceOf[com.vaadin.ui.Label] === true))
  }

  ignore("filtering component container with matches from children returns results") {
    val layout = new VerticalLayout with FilterableComponentContainer[VerticalLayout]
    layout.add(createTestLayout)

    val result = layout filterRecursive (_.getStyleName == "one")

    assert(result.size === 1)
    result.foreach(c => assert(c.isInstanceOf[com.vaadin.ui.Label] === true))
  }

  def createTestLayout: VerticalLayout with FilterableComponentContainer[VerticalLayout] = {
    new VerticalLayout with FilterableComponentContainer[VerticalLayout] {
      val one = add(new Label(style = "one"))
      val two = add(new Label(style = "two"))
      val three = add(new Label(style = "three"))
    }
  }

  test("Accordion, constructor with all params but without names") {
    val accordion = new Accordion(10 px, 100 em, "Caption", "Style")
    assert(accordion.getWidth == 10)
    assert(accordion.getWidthUnits() == Sizeable.UNITS_PIXELS)
    assert(accordion.getHeight == 100)
    assert(accordion.getHeightUnits() == Sizeable.UNITS_EM)
    assert(accordion.getCaption == "Caption")
    assert(accordion.getStyleName == "Style")
  }

  test("Accordion, test SelectedTabChangeListener") {
    val accordion = new Accordion() {
      def fireSelectedTabChangeEvent() = {
        fireSelectedTabChange;
      }
    }

    var cnt = 0;
    accordion.addListener(_ => cnt = cnt + 1)
    accordion.fireSelectedTabChangeEvent
    assert(cnt == 1)
  }

}