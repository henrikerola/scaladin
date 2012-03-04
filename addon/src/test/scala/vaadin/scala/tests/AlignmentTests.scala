package vaadin.scala.tests

import vaadin.scala.Alignment
import com.vaadin.ui. { Alignment => VaadinAlignment }
import org.scalatest.FunSuite

class AlignmentTests extends FunSuite {

  test("test aligments") {
    
    assert(Alignment.topRight.id === VaadinAlignment.TOP_RIGHT.getBitMask())
    assert(Alignment.topLeft.id === VaadinAlignment.TOP_LEFT.getBitMask())
    assert(Alignment.topCenter.id === VaadinAlignment.TOP_CENTER.getBitMask())
    
    assert(Alignment.middleRight.id === VaadinAlignment.MIDDLE_RIGHT.getBitMask())
    assert(Alignment.middleLeft.id === VaadinAlignment.MIDDLE_LEFT.getBitMask())
    assert(Alignment.middleCenter.id === VaadinAlignment.MIDDLE_CENTER.getBitMask())
    
    assert(Alignment.bottomRight.id === VaadinAlignment.BOTTOM_RIGHT.getBitMask())
    assert(Alignment.bottomLeft.id === VaadinAlignment.BOTTOM_LEFT.getBitMask())
    assert(Alignment.bottomCenter.id === VaadinAlignment.BOTTOM_CENTER.getBitMask())
  }

}