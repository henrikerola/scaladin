package vaadin.scala.internal

import vaadin.scala.UI.PollEvent

/**
 *
 * @author Henri Kerola / Vaadin
 */
class PollListener(val action: PollEvent => Unit)
    extends com.vaadin.event.UIEvents.PollListener with Listener {

  override def poll(event: com.vaadin.event.UIEvents.PollEvent): Unit = {
    val ui = wrapperFor(event.getUI).get
    action(new PollEvent(ui))
  }

}
