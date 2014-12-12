package vaadin.scala

import event.LayoutClickNotifier
import scala.collection.mutable.Map
import com.vaadin.ui.{ Component => VaadinComponent }
import vaadin.scala.mixins.CssLayoutMixin
import internal.WrapperUtil

package mixins {
  trait CssLayoutMixin extends AbstractLayoutMixin { self: com.vaadin.ui.CssLayout =>
    override def getCss(c: com.vaadin.ui.Component): String = {
      wrapper.asInstanceOf[CssLayout].getCss(WrapperUtil.wrapperFor(c).get).orNull
    }
  }
}

/**
 * @see com.vaadin.ui.CssLayout
 * @author Henri Kerola / Vaadin
 *
 */
class CssLayout(
  override val p: com.vaadin.ui.CssLayout with CssLayoutMixin = new com.vaadin.ui.CssLayout with CssLayoutMixin)
    extends AbstractLayout(p) with LayoutClickNotifier {

  // FIXME: should be private or protected
  val cssMap = Map.empty[VaadinComponent, String]

  // TODO: could take a function literal instead of a CSS String
  def add[C <: Component](component: C, css: => String = null): C = {
    add(component)
    if (css != null) {
      cssMap += component.p -> css
    }
    component
  }

  def getCss(component: Component): Option[String] = Option(cssMap.getOrElse(component.p, null))

  def addComponentAsFirst(component: Component) {
    p.addComponentAsFirst(component.p)
  }

  override def removeComponent(component: Component) = {
    super.removeComponent(component)
    cssMap -= component.p
  }
}