package vaadin.scala.tests

import vaadin.scala.HierarchicalContainer
import vaadin.scala.mixins.HierarchicalContainerMixin
import org.mockito.Mockito

class HierarchicalContainerTests extends ScaladinTestSuite with ContainerTestHierarchical with ContainerTestIndexed {

  class VaadinHierarchicalContainer extends com.vaadin.data.util.HierarchicalContainer with HierarchicalContainerMixin

  var vaadinContainer: VaadinHierarchicalContainer = _
  var spy: VaadinHierarchicalContainer = _
  var scaladinContainer: HierarchicalContainer = _

  var mockedVaadinContainer: VaadinHierarchicalContainer = _
  var containerWithMock: HierarchicalContainer = _

  var container: HierarchicalContainer = _

  before {
    vaadinContainer = new VaadinHierarchicalContainer
    spy = Mockito.spy(vaadinContainer)
    scaladinContainer = new HierarchicalContainer(spy)

    mockedVaadinContainer = mock[VaadinHierarchicalContainer]
    containerWithMock = new HierarchicalContainer(mockedVaadinContainer)

    container = new HierarchicalContainer
  }

  test("moveAfterSibling") {
    containerWithMock.moveAfterSibling('itemId, 'siblingId)
    Mockito.verify(mockedVaadinContainer).moveAfterSibling('itemId, 'siblingId)
  }

  test("removeItemRecursively") {
    containerWithMock.removeItemRecursively('itemId)
    Mockito.verify(mockedVaadinContainer).removeItemRecursively('itemId)
  }

  test("includeParentsWhenFiltering") {
    assert(scaladinContainer.includeParentsWhenFiltering)
    scaladinContainer.includeParentsWhenFiltering = false
    assert(!scaladinContainer.includeParentsWhenFiltering)
    Mockito.verify(spy).setIncludeParentsWhenFiltering(false)
    Mockito.verify(spy, Mockito.times(2)).isIncludeParentsWhenFiltering()
  }

  test("getType") {
    container.addContainerProperty("prop", classOf[Long], None)
    assert(classOf[Long] === container.getType("prop"))
    assert(classOf[java.lang.Long] === container.p.getType("prop"))
  }

  test("property.getType") {
    container.addContainerProperty("prop", classOf[Long], None)
    container.addItem("itemId")

    assert(classOf[Long] === container.getItem("itemId").getProperty("prop").getType)
    assert(classOf[Long] === container.getProperty("itemId", "prop").getType)
    assert(classOf[java.lang.Long] === container.p.getItem("itemId").getItemProperty("prop").getType)
  }

}