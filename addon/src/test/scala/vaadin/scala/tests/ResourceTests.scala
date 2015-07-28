package vaadin.scala.tests

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import vaadin.scala.server._

@RunWith(classOf[JUnitRunner])
class ResourceTests extends FunSuite {

  test("ExternalResource, non-null url param") {
    val resource = new ExternalResource("http://example.com")
    assert(resource.url === "http://example.com")
    assert(resource.pResource.getURL === "http://example.com")
    assert(resource.mimeType === "application/x-msdos-program")
    assert(resource.pResource.getMIMEType === "application/x-msdos-program")
  }

  test("ExternalResource, null url throws RuntimeException") {
    intercept[RuntimeException] {
      val resource = new ExternalResource(null)
    }
  }

  test("ExternalResource, mime param specified") {
    val resource = new ExternalResource(sourceUrl = "http://example.com", mimeType = "my mime type")
    assert(resource.mimeType === "my mime type")
    assert(resource.pResource.getMIMEType === "my mime type")
  }

  test("ClassResource") {
    val resource = ClassResource("hello")

    assert("hello" == resource.fileName)
    assert("application/octet-stream" == resource.mimeType)
    assert(86400000 == resource.cacheTime)
    assert(0 == resource.bufferSize)

    resource.cacheTime = 1000
    resource.bufferSize = 10

    assert(1000 == resource.cacheTime)
    assert(10 == resource.bufferSize)
  }
}