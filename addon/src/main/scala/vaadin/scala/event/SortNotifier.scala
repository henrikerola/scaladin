package vaadin.scala.event

import vaadin.scala.ListenersSet
import vaadin.scala.internal.{ GridSortListener, ListenersTrait }
import com.vaadin.ui.{ AbstractComponent => VaadinAbstractComponent }
import com.vaadin.event.SortEvent.{ SortNotifier => VaadinSortNotifier }

/**
 *
 * @author Henri Kerola / Vaadin
 */
trait SortNotifier {
  self: { def p: VaadinAbstractComponent with VaadinSortNotifier } =>

  lazy val sortListeners: ListenersSet[SortEvent => Unit] =
    new ListenersTrait[SortEvent, GridSortListener] {
      override def listeners = p.getListeners(classOf[com.vaadin.event.SortEvent])
      override def addListener(elem: SortEvent => Unit) = p.addSortListener(new GridSortListener(elem))
      override def removeListener(elem: GridSortListener) = p.removeSortListener(elem)
    }
}
