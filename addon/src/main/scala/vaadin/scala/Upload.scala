package vaadin.scala

import vaadin.scala.internal.UploadReceiver
import vaadin.scala.internal.UploadProgressListener
import vaadin.scala.internal.UploadStartedListener
import vaadin.scala.internal.UploadFinishedListener
import vaadin.scala.internal.UploadFailedListener
import vaadin.scala.internal.UploadSucceededListener

object Upload {
  case class ReceiveEvent(filename: String, mimeType: String)
  case class ProgressEvent(readBytes: Long, contentLength: Long)
  case class StartedEvent(upload: Upload, filename: String, mimeType: String, contentLength: Long)
  case class FinishedEvent(upload: Upload, filename: String, mimeType: String, contentLength: Long)
  case class FailedEvent(upload: Upload, filename: String, mimeType: String, contentLength: Long, reason: Exception)
  case class SucceededEvent(upload: Upload, filename: String, mimeType: String, contentLength: Long)
}

/**
 * @see com.vaadin.ui.Upload
 * @author Henri Kerola / Vaadin
 */
class Upload(override val p: com.vaadin.ui.Upload = new com.vaadin.ui.Upload) extends AbstractComponent(p) with Focusable {

  WrapperRegistry.put(this)

  def receiver: Option[Upload.ReceiveEvent => java.io.OutputStream] = p.getReceiver match {
    case null => None
    case receiver: UploadReceiver => Some(receiver.receiver)
  }

  def receiver_=(receiver: Upload.ReceiveEvent => java.io.OutputStream): Unit = {
    p.setReceiver(new UploadReceiver(receiver))
  }

  def receiver_=(receiverOption: Option[Upload.ReceiveEvent => java.io.OutputStream]): Unit = receiverOption match {
    case None => p.setReceiver(null)
    case Some(r) => receiver = r
  }

  def interruptUpload() = p.interruptUpload()

  def uploading = p.isUploading

  def bytesRead: Long = p.getBytesRead

  def uploadSize: Long = p.getUploadSize

  def buttonCaption = Option(p.getButtonCaption)
  def buttonCaption_=(buttonCaption: Option[String]) = p.setButtonCaption(buttonCaption.getOrElse(null))
  def buttonCaption_=(buttonCaption: String) = p.setButtonCaption(buttonCaption)

  def submitUpload() = p.submitUpload()

  lazy val progressListeners = new ListenersTrait[Upload.ProgressEvent => Unit, UploadProgressListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.terminal.StreamVariable.StreamingProgressEvent])
    override def addListener(elem: Upload.ProgressEvent => Unit) = p.addListener(new UploadProgressListener(elem))
    override def removeListener(elem: UploadProgressListener) = p.removeListener(elem)
  }

  lazy val startedListeners = new ListenersTrait[Upload.StartedEvent => Unit, UploadStartedListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Upload.StartedEvent])
    override def addListener(elem: Upload.StartedEvent => Unit) = p.addListener(new UploadStartedListener(elem))
    override def removeListener(elem: UploadStartedListener) = p.removeListener(elem)
  }

  lazy val finishedListeners = new ListenersTrait[Upload.FinishedEvent => Unit, UploadFinishedListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Upload.FinishedEvent])
    override def addListener(elem: Upload.FinishedEvent => Unit) = p.addListener(new UploadFinishedListener(elem))
    override def removeListener(elem: UploadFinishedListener) = p.removeListener(elem)
  }

  lazy val failedListeners = new ListenersTrait[Upload.FailedEvent => Unit, UploadFailedListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Upload.FailedEvent])
    override def addListener(elem: Upload.FailedEvent => Unit) = p.addListener(new UploadFailedListener(elem))
    override def removeListener(elem: UploadFailedListener) = p.removeListener(elem)
  }

  lazy val succeededListeners = new ListenersTrait[Upload.SucceededEvent => Unit, UploadSucceededListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.Upload.SucceededEvent])
    override def addListener(elem: Upload.SucceededEvent => Unit) = p.addListener(new UploadSucceededListener(elem))
    override def removeListener(elem: UploadSucceededListener) = p.removeListener(elem)
  }
}

package internal {
  class UploadReceiver(val receiver: Upload.ReceiveEvent => java.io.OutputStream) extends com.vaadin.ui.Upload.Receiver {
    def receiveUpload(filename: String, mimeType: String): java.io.OutputStream = receiver(Upload.ReceiveEvent(filename, mimeType))
  }

  class UploadProgressListener(val action: Upload.ProgressEvent => Unit) extends com.vaadin.ui.Upload.ProgressListener with Listener {
    def updateProgress(readBytes: Long, contentLength: Long) = action(Upload.ProgressEvent(readBytes, contentLength))
  }

  class UploadStartedListener(val action: Upload.StartedEvent => Unit) extends com.vaadin.ui.Upload.StartedListener with Listener {
    def uploadStarted(e: com.vaadin.ui.Upload.StartedEvent) = action(Upload.StartedEvent(WrapperRegistry.get[Upload](e.getUpload).get, e.getFilename, e.getMIMEType, e.getContentLength))
  }

  class UploadFinishedListener(val action: Upload.FinishedEvent => Unit) extends com.vaadin.ui.Upload.FinishedListener with Listener {
    def uploadFinished(e: com.vaadin.ui.Upload.FinishedEvent) = action(Upload.FinishedEvent(WrapperRegistry.get[Upload](e.getUpload).get, e.getFilename, e.getMIMEType, e.getLength))
  }

  class UploadFailedListener(val action: Upload.FailedEvent => Unit) extends com.vaadin.ui.Upload.FailedListener with Listener {
    def uploadFailed(e: com.vaadin.ui.Upload.FailedEvent) = action(Upload.FailedEvent(WrapperRegistry.get[Upload](e.getUpload).get, e.getFilename, e.getMIMEType, e.getLength, e.getReason))
  }

  class UploadSucceededListener(val action: Upload.SucceededEvent => Unit) extends com.vaadin.ui.Upload.SucceededListener with Listener {
    def uploadSucceeded(e: com.vaadin.ui.Upload.SucceededEvent) = action(Upload.SucceededEvent(WrapperRegistry.get[Upload](e.getUpload).get, e.getFilename, e.getMIMEType, e.getLength))
  }
}

