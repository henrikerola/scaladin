package vaadin.scala.internal

import com.vaadin.event.{ SelectionEvent => VaadinSelectionEvent }
import com.vaadin.event.SelectionEvent.SelectionListener
import vaadin.scala.Component
import vaadin.scala.event.SelectionEvent

/**
 *
 * @author Henri Kerola / Vaadin
 */
class GridSelectionListener(val action: SelectionEvent => Unit)
    extends SelectionListener with Listener {
  override def select(event: VaadinSelectionEvent): Unit = {
    val component = wrapperFor[Component](event.getSource).get
    val added = event.getAdded.toArray
    val removed = event.getRemoved.toArray
    action(SelectionEvent(component, added, removed))
  }
}
