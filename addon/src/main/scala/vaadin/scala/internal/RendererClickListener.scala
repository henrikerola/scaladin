package vaadin.scala.internal

import com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent
import vaadin.scala.Grid
import vaadin.scala.renderers.ClickableRenderer

/**
 *
 * @author Henri Kerola / Vaadin
 */
class RendererClickListener(val action: ClickableRenderer.RendererClickEvent => Unit)
    extends com.vaadin.ui.renderers.ClickableRenderer.RendererClickListener with Listener {

  override def click(event: RendererClickEvent): Unit = {
    val grid = wrapperFor[Grid](event.getSource).get
    val itemId = event.getItemId
    val column = new Grid.Column(event.getColumn)

    action(ClickableRenderer.RendererClickEvent(grid, itemId, column))
  }
}
