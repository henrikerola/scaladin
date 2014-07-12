package vaadin.scala.server

import vaadin.scala.server.mixins.FileDownloaderMixin
import com.vaadin.server.{ FileDownloader => VaadinFileDownloader }
import vaadin.scala.AbstractComponent

package mixins {
  trait FileDownloaderMixin extends AbstractExtensionMixin
}

object FileDownloader {

  def apply(resource: Resource): FileDownloader = {
    new FileDownloader(new VaadinFileDownloader(resource.pResource) with FileDownloaderMixin)
  }
}

/**
 * @see com.vaadin.server.FileDownloader
 * @author Henri Kerola / Vaadin
 */
class FileDownloader(override val p: VaadinFileDownloader with FileDownloaderMixin) extends AbstractExtension(p) {

  def extend(target: AbstractComponent): Unit = p.extend(target.p)

  def resource: Resource = wrapperFor(p.getFileDownloadResource).get

  def resource_=(resource: Resource): Unit = p.setFileDownloadResource(resource.pResource)

  def overrideContentType: Boolean = p.isOverrideContentType

  def overrideContentType_=(overrideContentType: Boolean): Unit = p.setOverrideContentType(overrideContentType)
}
