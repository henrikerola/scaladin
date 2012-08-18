package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._
import org.scalatest.BeforeAndAfter
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.mockito.Mockito

@RunWith(classOf[JUnitRunner])
class RootTests extends FunSuite with BeforeAndAfter {

  var root: Root = _

  before {
    root = new Root {
      def init(request: WrappedRequest) {
      }
    }
  }

  test("Root.current") {
    val root = new Root {
      def init(request: WrappedRequest) {
      }
    }

    assert(Root.current === null)

    Root.current = root
    assert(Root.current === root)

    Root.current = None
    assert(Root.current === null)
  }

  test("application") {
    val app = new Application

    assert(root.application === null)
    root.application = app
    assert(root.application === app)
  }

  test("rootId") {
    assert(root.rootId === -1)

    root.rootId = 2
    assert(root.rootId === 2)
  }

  test("content") {
    val layout = new VerticalLayout

    assert(root.content.isInstanceOf[VerticalLayout])
    assert(root.content.asInstanceOf[VerticalLayout].margin === Margin(true, true, true, true))

    root.content = layout
    assert(root.content === layout)
  }
  
  test("resizeLazy") {
    assert(!root.resizeLazy)
    root.resizeLazy = true
    assert(root.resizeLazy)
  }
}