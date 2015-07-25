package vaadin.scala.tests

import vaadin.scala.Container.Filter
import vaadin.scala.IndexedContainer

/**
 *
 * @author Henri Kerola / Vaadin
 */
class FilterTests extends ScaladinTestSuite {

  var container: IndexedContainer = _

  before {
    container = new IndexedContainer
  }

  test("Adding and removing a container filter") {
    assert(container.filters.isEmpty)

    val filter = Filter(e => true)

    container.filters += filter
    assert(1 == container.filters.size)
    assert(container.filters.contains(filter))

    container.filters -= filter

    assert(container.filters.isEmpty)
    assert(!container.filters.contains(filter))
  }

  test("Filter is run when added to container") {
    val item = container.addItem("itemId").get

    var filterCnt = 0
    container.filters += Filter(e => {
      filterCnt = filterCnt + 1

      assert("itemId" == e.itemId)
      assert(item.p == e.item.p)

      false
    })

    // One item in the container so the filter is
    // run once when added to the container..
    assert(1 == filterCnt)

    // The filter returns false which means that
    // no visible items in container => size is 0
    assert(0 == container.size)
  }

}
