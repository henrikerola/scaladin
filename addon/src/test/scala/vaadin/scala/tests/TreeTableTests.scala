package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.BeforeAndAfter
import org.scalatest.mock.MockitoSugar
import vaadin.scala.mixins.TreeTableMixin
import org.mockito.Mockito

@RunWith(classOf[JUnitRunner])
class TreeTableTests extends FunSuite with BeforeAndAfter with MockitoSugar {

  class VaadinTreeTable extends com.vaadin.ui.TreeTable with TreeTableMixin

  var treeTable: TreeTable = _
  var spy: VaadinTreeTable = _

  before {
    val vaadinTree = new VaadinTreeTable
    spy = Mockito.spy(vaadinTree)
    treeTable = new TreeTable(spy)
  }

  test("setCollapsed(itemId, collapsed)") {
    treeTable.setCollapsed('itemId, true)
    Mockito.verify(spy).setCollapsed('itemId, true)
  }

  test("hierarchyColumn") {
    assert(treeTable.hierarchyColumn === None)

    treeTable.hierarchyColumn = 'col1
    assert(treeTable.hierarchyColumn === Some('col1))

    treeTable.hierarchyColumn = None
    assert(treeTable.hierarchyColumn === None)

    treeTable.hierarchyColumn = Some('col1)
    assert(treeTable.hierarchyColumn === Some('col1))
  }

  test("isCollapsed(itemId)") {
    assert(treeTable.isCollapsed('itemId))
    Mockito.verify(spy).isCollapsed('itemId)
  }

  test("animationsEnabled") {
    assert(!treeTable.animationsEnabled)

    treeTable.animationsEnabled = true
    assert(treeTable.animationsEnabled)
  }

  test("container") {
    assert(treeTable.container.get.getClass === classOf[HierarchicalContainer])
  }
}