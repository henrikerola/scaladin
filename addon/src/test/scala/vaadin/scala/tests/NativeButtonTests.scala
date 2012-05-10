package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class NativeButtonTests extends FunSuite {

  test("construction") {
    val nativeButton = new Button
  }
}