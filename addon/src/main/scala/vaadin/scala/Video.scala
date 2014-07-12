package vaadin.scala

import vaadin.scala.mixins.VideoMixin
import vaadin.scala.server.Resource

package mixins {
  trait VideoMixin extends AbstractMediaMixin { self: com.vaadin.ui.Video => }
}

/**
 * @see com.vaadin.ui.Video
 * @author Henri Kerola / Vaadin
 */
class Video(override val p: com.vaadin.ui.Video with VideoMixin = new com.vaadin.ui.Video with VideoMixin)
    extends AbstractMedia(p) {

  def poster: Option[Resource] = wrapperFor(p.getPoster)
  def poster_=(poster: Option[Resource]) { p.setPoster(peerFor(poster)) }
  def poster_=(poster: Resource) { p.setPoster(poster.pResource) }
}