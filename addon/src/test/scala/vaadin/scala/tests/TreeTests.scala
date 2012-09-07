package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.BeforeAndAfter
import org.scalatest.mock.MockitoSugar
import vaadin.scala.mixins.TreeMixin
import org.mockito.Mockito

@RunWith(classOf[JUnitRunner])
class TreeTests extends FunSuite with BeforeAndAfter with MockitoSugar {

  class VaadinTree extends com.vaadin.ui.Tree with TreeMixin

  var tree: Tree = _
  var spy: VaadinTree = _

  before {
    val vaadinTree = new VaadinTree
    spy = Mockito.spy(vaadinTree)
    tree = new Tree(spy)
  }
  
  test("expanded") {
    assert(!tree.expanded('itemId))
    Mockito.verify(spy).isExpanded('itemId)
  }
  
  test("expandItem") {
    assert(!tree.expandItem('itemId))
    Mockito.verify(spy).expandItem('itemId)
  }
  
  test("expandItemsRecursively") {
	  assert(tree.expandItemsRecursively('itemId))
	  Mockito.verify(spy).expandItemsRecursively('itemId)
  }
  
  test("collapseItem") {
    assert(tree.collapseItem('itemId))
    Mockito.verify(spy).collapseItem('itemId)
  }
  
  test("collapseItemsRecursively") {
	  assert(tree.collapseItemsRecursively('itemId))
	  Mockito.verify(spy).collapseItemsRecursively('itemId)
  }

  test("itemStyleGenerator") {
    val generator = { e: Tree.ItemStyleEvent =>
      None
    }

    assert(tree.itemStyleGenerator === None)

    tree.itemStyleGenerator = generator
    assert(tree.itemStyleGenerator === Some(generator))

    tree.itemStyleGenerator = None
    assert(tree.itemStyleGenerator === None)

    tree.itemStyleGenerator = Some(generator)
    assert(tree.itemStyleGenerator === Some(generator))
  }
  
  test("itemStyleGenerator, style generation") {
    tree.itemStyleGenerator = { e: Tree.ItemStyleEvent =>
      e.itemId match {
        case "test1" => Some("test1")
        case _ => None
      }
    }
    
    assert(tree.p.getItemStyleGenerator.getStyle(tree.p, "test1") === "test1")
    assert(tree.p.getItemStyleGenerator.getStyle(tree.p, "test2") === null)
    assert(tree.p.getItemStyleGenerator.getStyle(tree.p, null) === null)
  }

}