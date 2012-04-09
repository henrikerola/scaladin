package vaadin.scala

import scala.collection.mutable.Map
import com.vaadin.ui.{ Alignment => VaadinAlignment, Component => VaadinComponent }
import com.vaadin.event.LayoutEvents.LayoutClickNotifier

trait LayoutClickListener extends LayoutClickNotifier {
  def addLayoutClickListener(action: com.vaadin.event.LayoutEvents.LayoutClickEvent => Unit) {
    addListener(new com.vaadin.event.LayoutEvents.LayoutClickListener {
      def layoutClick(event: com.vaadin.event.LayoutEvents.LayoutClickEvent) = action(event)
    })
  }
}

class CssLayout(width: Option[Measure] = None, height: Option[Measure] = None, margin: Boolean = false, style: String = null, caption: String = null, size: Tuple2[String, String] = null)
  extends AbstractLayout /*with LayoutClickListener*/ {

  override val p = new com.vaadin.ui.CssLayout {
    override def getCss(c: VaadinComponent): String = {
      cssMap.getOrElse(c, null)
    }
  }
  WrapperRegistry.put(this)

  if (size != null) {
    p.setWidth(size._1)
    p.setHeight(size._2)
  } else {
    p.setWidth(if (width.isDefined) width.get.toString else null)
    p.setHeight(if (height.isDefined) height.get.toString else null)
  }
  p.setMargin(margin)
  p.setStyleName(style)
  p.setCaption(caption)

  // TODO remove css from map when component is removed from the layout
  val cssMap = Map[VaadinComponent, String]()

  def add[C <: Component](component: C, css: String = null): C = {
    add(component)
    if (css != null) cssMap(component.p) = css
    component
  }

  // TODO: addComponentAsFirst?
  // TODO: listeners

  // TODO:
  //  def css(c: Component): String = {
  //	  p.getCss(c.p)
  //  }
}