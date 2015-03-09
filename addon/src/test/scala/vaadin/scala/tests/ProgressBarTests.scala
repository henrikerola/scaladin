package vaadin.scala.tests

import vaadin.scala.ProgressBar

/**
 * @author Henri Kerola / Vaadin
 */
class ProgressBarTests extends ScaladinTestSuite {

    test("getType") {
      val progressBar = new ProgressBar

      assert(classOf[Float] == progressBar.getType)
      assert(classOf[java.lang.Float] == progressBar.p.getType)
    }
}
