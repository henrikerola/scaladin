package vaadin.scala.tests

import vaadin.scala.Alignment
import com.vaadin.ui.{ Alignment => VaadinAlignment }
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class AlignmentTests extends FunSuite {

  test("test aligments") {

    assert(Alignment.TopRight.id === VaadinAlignment.TOP_RIGHT.getBitMask())
    assert(Alignment.TopLeft.id === VaadinAlignment.TOP_LEFT.getBitMask())
    assert(Alignment.TopCenter.id === VaadinAlignment.TOP_CENTER.getBitMask())

    assert(Alignment.MiddleRight.id === VaadinAlignment.MIDDLE_RIGHT.getBitMask())
    assert(Alignment.MiddleLeft.id === VaadinAlignment.MIDDLE_LEFT.getBitMask())
    assert(Alignment.MiddleCenter.id === VaadinAlignment.MIDDLE_CENTER.getBitMask())

    assert(Alignment.BottomRight.id === VaadinAlignment.BOTTOM_RIGHT.getBitMask())
    assert(Alignment.BottomLeft.id === VaadinAlignment.BOTTOM_LEFT.getBitMask())
    assert(Alignment.BottomCenter.id === VaadinAlignment.BOTTOM_CENTER.getBitMask())
  }

}