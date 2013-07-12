package vaadin.scala.tests

import vaadin.scala._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import org.mockito.Mockito
import com.vaadin.server.VaadinRequest
import java.io.InputStream
import java.util.Locale
import scala.collection.mutable
import vaadin.scala.server.ScaladinRequest

@RunWith(classOf[JUnitRunner])
class ScaladinRequestTests extends FunSuite with BeforeAndAfter with MockitoSugar {

  var scaladinRequest: ScaladinRequest = _
  var vaadinRequest: com.vaadin.server.VaadinRequest = _

  before {
    vaadinRequest = mock[com.vaadin.server.VaadinRequest]
    scaladinRequest = new ScaladinRequest {
      val p = vaadinRequest
    }
  }

  test("getParameter") {
    Mockito.when(vaadinRequest.getParameter("param1")).thenReturn("value")
    Mockito.when(vaadinRequest.getParameter("param2")).thenReturn(null)

    assert(scaladinRequest.getParameter("param1") === Some("value"))
    assert(scaladinRequest.getParameter("param2") === None)
  }

  test("parameterMap") {
    val map: mutable.Map[String, Array[String]] = scaladinRequest.parameterMap
    Mockito.verify(vaadinRequest).getParameterMap
  }

  test("contentLength") {
    Mockito.when(vaadinRequest.getContentLength).thenReturn(12345)
    assert(scaladinRequest.contentLength === 12345)
  }

  test("inputStream") {
    val inputStream = mock[InputStream]
    Mockito.when(vaadinRequest.getInputStream).thenReturn(inputStream)
    assert(scaladinRequest.inputStream === inputStream)
  }

  test("getAttribute") {
    // cannot use thenResult because complains about "ambiguous reference to overloaded definition"
    Mockito.doReturn(12345).when(vaadinRequest).getAttribute("attr1")
    Mockito.doReturn(null).when(vaadinRequest).getAttribute("attr2")
    assert(scaladinRequest.getAttribute("attr1") === Some(12345))
    assert(scaladinRequest.getAttribute("attr2") === None)
  }

  test("setAttribute") {
    scaladinRequest.setAttribute("attr1", 12345)
    Mockito.verify(vaadinRequest).setAttribute("attr1", 12345)

    scaladinRequest.setAttribute("attr2", Some(123))
    Mockito.verify(vaadinRequest).setAttribute("attr2", 123)

    scaladinRequest.setAttribute("attr3", None)
    Mockito.verify(vaadinRequest).setAttribute("attr3", null)
  }

  test("pathInfo") {
    Mockito.when(vaadinRequest.getPathInfo).thenReturn("/test")
    assert(scaladinRequest.pathInfo === Some("/test"))

    Mockito.when(vaadinRequest.getPathInfo).thenReturn(null)
    assert(scaladinRequest.pathInfo === None)
  }

  test("contextPath") {
    Mockito.when(vaadinRequest.getContextPath).thenReturn("/test")
    assert(scaladinRequest.contextPath === Some("/test"))

    Mockito.when(vaadinRequest.getContextPath).thenReturn(null)
    assert(scaladinRequest.contextPath === None)
  }

  // TODO: wrappedSession

  test("contentType") {
    Mockito.when(vaadinRequest.getContentType).thenReturn("contentType")
    assert(scaladinRequest.contentType === Some("contentType"))

    Mockito.when(vaadinRequest.getContentType).thenReturn(null)
    assert(scaladinRequest.contentType === None)
  }

  // TODO: browserDetails

  test("locale") {
    Mockito.when(vaadinRequest.getLocale).thenReturn(Locale.ENGLISH)
    assert(scaladinRequest.locale === Locale.ENGLISH)
  }

  test("remoteAddr") {
    Mockito.when(vaadinRequest.getRemoteAddr).thenReturn("192.168.0.1")
    assert(scaladinRequest.remoteAddr === Some("192.168.0.1"))

    Mockito.when(vaadinRequest.getRemoteAddr).thenReturn(null)
    assert(scaladinRequest.remoteAddr === None)
  }

  test("isSecure") {
    Mockito.when(vaadinRequest.isSecure).thenReturn(true)
    assert(scaladinRequest.isSecure)
  }

  test("getHeader") {
    Mockito.when(vaadinRequest.getHeader("header1")).thenReturn("value")
    Mockito.when(vaadinRequest.getHeader("header2")).thenReturn(null)

    assert(scaladinRequest.getHeader("header1") === Some("value"))
    assert(scaladinRequest.getHeader("header2") === None)
  }

  // TODO: vaadinService

}