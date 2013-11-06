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

    val label = new Label
    wrapper.addComponent(label)

    //can call without cast
    wrapper.getExpandRatio(label.p)
  }

  test("wrapped has wrapper reference") {
    val wrapperComponent = ScaladinWrapper.wrapComponent(new com.vaadin.ui.TextField with ComponentMixin)
    assert(wrapperComponent === wrapperComponent.p.wrapper)

    val wrapperContainer = ScaladinWrapper.wrapComponentContainer(new com.vaadin.ui.VerticalLayout with ComponentContainerMixin)
    assert(wrapperContainer === wrapperContainer.p.wrapper)
  }
}