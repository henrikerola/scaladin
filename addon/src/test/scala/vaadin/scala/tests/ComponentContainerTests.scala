package vaadin.scala.tests
import org.scalatest.FunSuite
import vaadin.scala._

class ComponentContainerTests extends FunSuite {

  test("filtering empty component container returns empty list") {
    val layout = new VerticalLayout with FilterableComponentContainer

    val result = layout filter (c => true)

    assert(result === Nil)
  }

  test("filtering component container with matches returns results") {
    val layout = new VerticalLayout with FilterableComponentContainer {
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

  test("filtering component container children with matches returns results") {
    val layout = createTestLayout
    layout.add(createTestLayout)

    val result = layout filterRecursive (_.getStyleName == "one")

    assert(result.size === 2)
    result.foreach(c => assert(c.isInstanceOf[Label] === true))
  }

  test("filtering component container with matches from children returns results") {
    val layout = new VerticalLayout with FilterableComponentContainer
    layout.add(createTestLayout)

    val result = layout filterRecursive (_.getStyleName == "one")

    assert(result.size === 1)
    result.foreach(c => assert(c.isInstanceOf[Label] === true))
  }

  def createTestLayout: VerticalLayout with FilterableComponentContainer = {
    new VerticalLayout with FilterableComponentContainer {
      val one = add(new Label(style = "one"))
      val two = add(new Label(style = "two"))
      val three = add(new Label(style = "three"))
    }
  }
}