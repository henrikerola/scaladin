package vaadin.scala.tests

import vaadin.scala.Upload
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito
import org.mockito.Matchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.io.FileOutputStream
import vaadin.scala.mixins.UploadMixin

@RunWith(classOf[JUnitRunner])
class UploadTests extends FunSuite with BeforeAndAfter with MockitoSugar {

  class VaadinUpload extends com.vaadin.ui.Upload with UploadMixin

  var upload: Upload = _
  var spy: VaadinUpload = _

  before {
    val vaadinUpload = new VaadinUpload
    spy = Mockito.spy(vaadinUpload)
    upload = new Upload(spy)
  }

  test("receiver") {
    assert(upload.receiver === None)

    val receiver = { e: Upload.ReceiveEvent =>
      mock[java.io.OutputStream]
    }

    upload.receiver = receiver
    assert(upload.receiver === Some(receiver))

    upload.receiver = None
    assert(upload.receiver === None)

    upload.receiver = Some(receiver)
    assert(upload.receiver === Some(receiver))
  }

  test("interruptUpload()") {
    upload.interruptUpload()
    Mockito.verify(spy).interruptUpload()
  }

  test("uploading") {
    assert(!upload.uploading)
    Mockito.verify(spy).isUploading
  }

  test("bytesRead") {
    assert(upload.bytesRead === 0)
    Mockito.verify(spy).getBytesRead
  }

  test("uploadSize") {
    assert(upload.uploadSize === -1)
    Mockito.verify(spy).getUploadSize
  }

  test("buttonCaption") {
    assert(upload.buttonCaption === Some("Upload"))

    upload.buttonCaption = None
    assert(upload.buttonCaption === None)

    upload.buttonCaption = "Test"
    assert(upload.buttonCaption === Some("Test"))

    upload.buttonCaption = Some("upload")
    assert(upload.buttonCaption === Some("upload"))
  }

  test("submitUpload()") {
    upload.submitUpload()
    Mockito.verify(spy).submitUpload()
  }

  // TODO: test listeners and events

  test("Usage example") {
    val upload = new Upload {
      buttonCaption = "Lähetä"
      receiver = { e: Upload.ReceiveEvent =>
        new FileOutputStream("/tmp/" + e.filename)
      }
      // FIXME
      //      succeededListeners += { e: Upload.SucceededEvent =>
      //        window.map(showNotification("Upload Succeeded"))
      //      }
      //      failedListeners += { e: Upload.FailedEvent =>
      //        window.showNotification("Upload Failed")
      //      }
    }
  }

}
