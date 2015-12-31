package vaadin.scala.internal

import com.vaadin.ui.Window.{ WindowModeChangeEvent => VaadinWindowModeChangeEvent }
import vaadin.scala.Window

/**
 *
 * @author Henri Kerola / Vaadin
 */
class WindowModeChangeListener(val action: Window.WindowModeChangeEvent => Unit)
    extends com.vaadin.ui.Window.WindowModeChangeListener with Listener {

  override def windowModeChanged(event: VaadinWindowModeChangeEvent): Unit = {
    val window = wrapperFor[Window](event.getWindow).get
    val mode = Window.WindowMode(event.getWindowMode.ordinal)

    action(Window.WindowModeChangeEvent(window, mode))
  }
}
