package vaadin.scala.tests
import org.scalatest.FeatureSpec
import org.scalatest.GivenWhenThen
import vaadin.scala.VerticalLayout
import vaadin.scala.FilterableComponentContainer
import vaadin.scala._
import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test

class ComponentContainerSpec extends AssertionsForJUnit {

  @Test
  def filteringEmptyContainerReturnsEmptyList() = {

    val layout = new VerticalLayout with FilterableComponentContainer

    val result = layout \ (c => true)

    assert(result == Nil)
  }

  @Test
  def filteringComponentContainerWithMatchesReturnsResults() = {
    val layout = new VerticalLayout with FilterableComponentContainer

    val one = new Label(style = "one")
    val two = new Label(style = "two")
    val three = new Label(style = "three")

    layout.add(one)
      .add(two)
      .add(three)

    val result = layout \ (c => List("one", "two") contains c.getStyleName)

    assert(result contains one)
    assert(result contains two)
  }

  @Test
  def filteringComponentContainerWithNoMatchesReturnsEmptyList() = {
    val layout = createTestLayout

    val result = layout \ (c => c.getStyleName == "four")

    assert(result == Nil)
  }

  @Test
  def filteringComponentContainerChildrenWithMatches() = {
    val layout = createTestLayout
    layout.add(createTestLayout)

    val result = layout \\ (c => c.getStyleName == "one")

    assert(result.size == 2)
    result.foreach(c => assert(c.isInstanceOf[Label] === true))
  }

  @Test
  def filterComponentContainerWithMatchesFromChildren() = {
    val layout = new VerticalLayout with FilterableComponentContainer
    layout.add(createTestLayout)

    val result = layout \\ (c => c.getStyleName == "one")

    assert(result.size == 1)
    result.foreach(c => assert(c.isInstanceOf[Label] === true))
  }

  def createTestLayout: VerticalLayout with FilterableComponentContainer = {
    val layout = new VerticalLayout with FilterableComponentContainer

    val one = new Label(style = "one")
    val two = new Label(style = "two")
    val three = new Label(style = "three")

    layout.add(one)
      .add(two)
      .add(three)

    layout
  }
}