package vaadin.scala.tests

import vaadin.scala.converter.ResourceConverter
import vaadin.scala.server.ThemeResource

/**
 *
 * @author Henri Kerola / Vaadin
 */
class ResourceConverterTests extends ScaladinTestSuite {

  val converter = new ResourceConverter

  test("convertToPresentation converts Scaladin Resource to wrapped Vaadin Resource") {
    val resource = new ThemeResource("mypicture.png")
    assert(Some(resource.pResource) === converter.convertToPresentation(Option(resource), null, null))
  }

  test("convertToPresentation converts None to None") {
    assert(None === converter.convertToPresentation(None, null, null))
  }

  test("convertToModel converts None to None") {
    assert(None === converter.convertToModel(None, null, null))
  }

  test("convertToModel converts wrapped Vaadin Resource to Scaladin Resource") {
    val resource = new ThemeResource("mypicture.png")
    assert(Some(resource) === converter.convertToModel(Option(resource.pResource), null, null))
  }

}
