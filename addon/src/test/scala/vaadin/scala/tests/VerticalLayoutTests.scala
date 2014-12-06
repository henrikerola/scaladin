package vaadin.scala.tests

import vaadin.scala._

/**
 *
 * @author Henri Kerola / Vaadin
 */
class VerticalLayoutTests extends ScaladinTestSuite {

  var layout: VerticalLayout = _

  before {
    layout = new VerticalLayout
  }

  test("defaultComponentAlignment") {
    assert(Alignment.TopLeft == layout.defaultComponentAlignment)

    layout.defaultComponentAlignment = Alignment.BottomCenter
    assert(Alignment.BottomCenter == layout.defaultComponentAlignment)
  }

}
