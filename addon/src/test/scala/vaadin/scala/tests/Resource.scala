package vaadin.scala.tests

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import vaadin.scala.server.ExternalResource

@RunWith(classOf[JUnitRunner])
class Resource extends FunSuite {

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