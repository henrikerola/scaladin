package vaadin.scala.event

import com.vaadin.ui.{ AbstractComponent => VaadinAbstractComponent }
import com.vaadin.event.SelectionEvent.{ SelectionNotifier => VaadinSelectionNotifier }
import vaadin.scala.ListenersSet
import vaadin.scala.internal.{ ListenersTrait, GridSelectionListener }

/**
 *
 * @author Henri Kerola / Vaadin
 */
trait SelectionNotifier {
  self: { def p: VaadinAbstractComponent with VaadinSelectionNotifier } =>

  lazy val selectionListeners: ListenersSet[SelectionEvent => Unit] =
    new ListenersTrait[SelectionEvent, GridSelectionListener] {
      override def listeners = p.getListeners(classOf[com.vaadin.event.SelectionEvent])
      override def addListener(elem: SelectionEvent => Unit) = p.addSelectionListener(new GridSelectionListener(elem))
      override def removeListener(elem: GridSelectionListener) = p.removeSelectionListener(elem)
    }
}
