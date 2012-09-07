package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ComponentContainerTests extends FunSuite {

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
    val layout = new VerticalLayout with FilterableComponentContainer

    val result = layout filter (c => true)

    assert(result === Nil)
  }

  test("filtering component container with matches returns results") {
    val layout = new VerticalLayout with FilterableComponentContainer {
      val one = add(new Label { styleNames += "one" })
      val two = add(new Label { styleNames += "two" })
      val three = add(new Label { styleNames += "three" })
    }

    val result = layout filter (c => List("one", "two") contains c.styleName)
    assert(result contains layout.one)
    assert(result contains layout.two)
  }

  test("filtering component container with no matches returns empty list") {
    val layout = createTestLayout

    val result = layout filter (_.styleName == "four")

    assert(result === Nil)
  }

  test("filtering component container children with matches returns results") {
    val layout = createTestLayout
    layout.add(createTestLayout)

    val result = layout filterRecursive (_.styleName == "one")

    assert(result.size === 2)
    result.foreach(c => assert(c.isInstanceOf[Label]))
  }

  test("filtering component container with matches from children returns results") {
    val layout = new VerticalLayout with FilterableComponentContainer
    layout.add(createTestLayout)

    val result = layout filterRecursive (_.styleName == "one")

    assert(result.size === 1)
    result.foreach(c => assert(c.isInstanceOf[Label]))
  }

  def createTestLayout: VerticalLayout with FilterableComponentContainer = {
    new VerticalLayout with FilterableComponentContainer {
      val one = add(new Label { styleName = "one" })
      val two = add(new Label { styleName = "two" })
      val three = add(new Label { styleName = "three" })
    }
  }
}