package vaadin.scala.tests

import vaadin.scala.internal.WrapperUtil
import vaadin.scala._
import vaadin.scala.mixins.ScaladinInterfaceMixin
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent

class WrapperUtilTests extends ScaladinTestSuite {

  trait ViewMixin extends ScaladinInterfaceMixin {
    self: com.vaadin.navigator.View =>
    override def wrapper = super.wrapper.asInstanceOf[View]

    def enter(event: ViewChangeEvent) {
      wrapper.enter(event)
    }
  }

  trait View extends InterfaceWrapper {
    val pView = new com.vaadin.navigator.View with ViewMixin
    pView.wrapper = this
    def enter(event: ViewChangeEvent)
  }

  trait TestTrait extends InterfaceWrapper {
    val pTest = new Object with ScaladinInterfaceMixin
    pTest.wrapper = this
  }

  class MyClass extends CustomComponent with View with TestTrait {
    def enter(event: ViewChangeEvent) {

    }
  }

  class MyClass2 extends TestTrait

  test("wrapperFor with multiple InterfaceWrappers") {
    val myClass = new MyClass

    assert(WrapperUtil.wrapperFor[View](myClass.p) === Some(myClass))
    assert(WrapperUtil.wrapperFor[MyClass](myClass.p) === Some(myClass))
    assert(WrapperUtil.wrapperFor(myClass.p) === Some(myClass))
    assert(WrapperUtil.wrapperFor[TestTrait](myClass.p) === Some(myClass))
  }

  test("wrapperFor InterfaceWrapper") {
    val myClass2 = new MyClass2

    assert(WrapperUtil.wrapperFor[TestTrait](myClass2.pTest) === Some(myClass2))
  }

  test("peerFor") {
    class MyPeer
    class MyWrapper(val p: Any) extends Wrapper

    val peer = new MyPeer
    val res: MyPeer = WrapperUtil.peerFor(Option(new MyWrapper(peer)))
    assert(res == peer)

    val wrapper: Option[Wrapper] = None
    assert(WrapperUtil.peerFor[Any](wrapper) === null)
  }

}
