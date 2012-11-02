package vaadin.scala.tests

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import vaadin.scala._
import vaadin.scala.mixins.ComponentMixin
import vaadin.scala.mixins.ComponentContainerMixin
import org.scalatest.FunSuite
import vaadin.scala.implicits._

@RunWith(classOf[JUnitRunner])
class ScaladinWrapperTests extends FunSuite {

  test("wrapped Component has correct wrapped type") {
    val wrapper = ScaladinWrapper.wrapComponent(new com.vaadin.ui.TextField with ComponentMixin)
    assert(wrapper.p.isInstanceOf[com.vaadin.ui.TextField])

    //can call without cast
    wrapper.getInputPrompt
  }

  test("wrapped ComponentContainer has correct wrapped type") {
    val wrapper = ScaladinWrapper.wrapComponentContainer(new com.vaadin.ui.VerticalLayout with ComponentContainerMixin)
    assert(wrapper.p.isInstanceOf[com.vaadin.ui.VerticalLayout])

    //can call without cast
    wrapper.getExpandRatio(null)
  }
}