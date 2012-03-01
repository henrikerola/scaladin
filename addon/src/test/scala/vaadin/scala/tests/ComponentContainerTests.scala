package vaadin.scala.tests
import org.scalatest.FunSuite
import vaadin.scala._
import com.vaadin.terminal.Sizeable

class ComponentContainerTests extends FunSuite {

  test("filtering empty component container returns empty list") {
    val layout = new VerticalLayout with FilterableComponentContainer

    val result = layout filter (c => true)

    assert(result === Nil)
  }

  test("filtering component container with matches returns results") {
    val layout = new VerticalLayout with FilterableComponentContainer {
      val one = add(new Label(style = "one").p)
      val two = add(new Label(style = "two").p)
      val three = add(new Label(style = "three").p)
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

  test("filtering component container children with matches returns results") {
    val layout = createTestLayout
    layout.add(createTestLayout)

    val result = layout filterRecursive (_.getStyleName == "one")

    assert(result.size === 2)
    result.foreach(c => assert(c.isInstanceOf[com.vaadin.ui.Label] === true))
  }

  test("filtering component container with matches from children returns results") {
    val layout = new VerticalLayout with FilterableComponentContainer
    layout.add(createTestLayout)

    val result = layout filterRecursive (_.getStyleName == "one")

    assert(result.size === 1)
    result.foreach(c => assert(c.isInstanceOf[com.vaadin.ui.Label] === true))
  }

  def createTestLayout: VerticalLayout with FilterableComponentContainer = {
    new VerticalLayout with FilterableComponentContainer {
      val one = add(new Label(style = "one").p)
      val two = add(new Label(style = "two").p)
      val three = add(new Label(style = "three").p)
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