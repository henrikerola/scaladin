package vaadin.scala

import vaadin.scala.mixins.AudioMixin

package mixins {
  trait AudioMixin extends AbstractMediaMixin { self: com.vaadin.ui.Audio => }
}

/**
 * @see com.vaadin.ui.Audio
 * @author Henri Kerola / Vaadin
 */
class Audio(override val p: com.vaadin.ui.Audio with AudioMixin = new com.vaadin.ui.Audio with AudioMixin)
  extends AbstractMedia(p)
