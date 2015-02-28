package vaadin.scala.renderers

import com.vaadin.ui.renderers.{ ClickableRenderer => VaadinClickableRenderer }
import vaadin.scala.renderers.ClickableRenderer.RendererClickEvent
import vaadin.scala.{ ListenersSet, Grid }
import vaadin.scala.event.ComponentEvent
import vaadin.scala.internal.{ RendererClickListener, ListenersTrait }
import vaadin.scala.renderers.mixins.ClickableRendererMixin

package mixins {
  trait ClickableRendererMixin extends AbstractRendererMixin
}

object ClickableRenderer {
  case class RendererClickEvent(grid: Grid, itemId: Any, column: Grid.Column) extends ComponentEvent(grid) {
    def propertyId: Any = column.propertyId
  }
}

/**
 *
 * @author Henri Kerola / Vaadin
 */
class ClickableRenderer[T](override val p: VaadinClickableRenderer[T] with ClickableRendererMixin)
    extends AbstractRenderer[T](p) {

  lazy val clickListeners: ListenersSet[RendererClickEvent => Unit] =
    new ListenersTrait[RendererClickEvent, RendererClickListener] {
      override def listeners = p.getListeners(classOf[com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent])
      override def addListener(elem: RendererClickEvent => Unit) = p.addClickListener(new RendererClickListener(elem))
      override def removeListener(elem: RendererClickListener) = p.removeClickListener(elem)
    }

}
