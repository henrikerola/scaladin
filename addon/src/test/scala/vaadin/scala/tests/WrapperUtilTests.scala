package vaadin.scala.tests

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{ BeforeAndAfter, FunSuite }
import org.scalatest.mock.MockitoSugar
import vaadin.scala.internal.WrapperUtil
import vaadin.scala.Wrapper

@RunWith(classOf[JUnitRunner])
class WrapperUtilTests extends FunSuite with BeforeAndAfter with MockitoSugar {

  test("peerFor") {
    class MyPeer
    class MyWrapper(val p: Any) extends Wrapper

    val peer = new MyPeer
    val res: MyPeer = WrapperUtil.peerFor(Option(new MyWrapper(peer)))
    assert(res == peer)
    assert(WrapperUtil.peerFor(None) == null)
  }

}
