package vaadin.scala.tests

import org.scalatest.FunSuite
import vaadin.scala._

class Resource extends FunSuite {

  implicit val wr = new WrapperRegistry

  test("ExternalResource, non-null url param") {
    val resource = new ExternalResource("http://example.com")
    assert(resource.url === "http://example.com")
    assert(resource.p.getURL === "http://example.com")
    assert(resource.mimeType === "application/x-msdos-program")
    assert(resource.p.getMIMEType === "application/x-msdos-program")
  }

  test("ExternalResource, null url throws RuntimeException") {
    intercept[RuntimeException] {
      val resource = new ExternalResource(null)
    }
  }

  test("ExternalResource, mime param specified") {
    val resource = new ExternalResource(sourceUrl = "http://example.com", mimeType = "my mime type")
    assert(resource.mimeType === "my mime type")
    assert(resource.p.getMIMEType === "my mime type")
  }

}