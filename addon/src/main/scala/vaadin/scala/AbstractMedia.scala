package vaadin.scala

import vaadin.scala.mixins.AbstractMediaMixin
import collection.JavaConverters._
import collection.mutable

package mixins {
  trait AbstractMediaMixin extends AbstractComponentMixin { self: com.vaadin.ui.AbstractMedia => }
}

/**
 * @see com.vaadin.ui.AbstractMedia
 * @author Henri Kerola / Vaadin
 */
class AbstractMedia(override val p: com.vaadin.ui.AbstractMedia with AbstractMediaMixin)
    extends AbstractComponent(p) {

  def source: Option[Resource] = p.getSources.asScala.headOption map { wrapperFor(_).get }
  def source_=(source: Resource) { p.setSource(source.p) }

  def sources: Seq[Resource] = p.getSources.asScala map { wrapperFor(_).get }

  def addSource(source: Resource) { p.addSource(source.p) }

  def showControls: Boolean = p.isShowControls
  def showControls_=(showControls: Boolean) { p.setShowControls(showControls) }

  def altText: Option[String] = Option(p.getAltText)
  def altText_=(altText: Option[String]) { p.setAltText(altText.orNull) }
  def altText_=(altText: String) { p.setAltText(altText) }

  def htmlContentAllowed: Boolean = p.isHtmlContentAllowed
  def htmlContentAllowed_=(htmlContentAllowed: Boolean) { p.setHtmlContentAllowed(htmlContentAllowed) }

  def autoplay: Boolean = p.isAutoplay
  def autoplay_=(autoplay: Boolean) { p.setAutoplay(autoplay) }

  def muted: Boolean = p.isMuted
  def muted_=(muted: Boolean) { p.setMuted(muted) }

  def pause(): Unit = p.pause()

  def play(): Unit = p.play()
}
