package vaadin.scala.renderers

import java.text.DateFormat
import java.util.{ Locale, Date }

import com.vaadin.ui.renderers.{ DateRenderer => VaadinDateRenderer }
import vaadin.scala.renderers.mixins.DateRendererMixin

package mixins {
  trait DateRendererMixin extends AbstractRendererMixin
}

object DateRenderer {

  def apply(): DateRenderer = new DateRenderer

  def apply(locale: Locale): DateRenderer = new DateRenderer(new VaadinDateRenderer(locale) with DateRendererMixin)

  def apply(formatString: String): DateRenderer =
    new DateRenderer(new VaadinDateRenderer(formatString) with DateRendererMixin)

  def apply(formatString: String, locale: Locale): DateRenderer =
    new DateRenderer(new VaadinDateRenderer(formatString, locale) with DateRendererMixin)

  def apply(dateFormat: DateFormat): DateRenderer =
    new DateRenderer(new VaadinDateRenderer(dateFormat) with DateRendererMixin)
}

/**
 * @see com.vaadin.ui.renderer.DateRenderer
 * @author Henri Kerola / Vaadin
 */
class DateRenderer(override val p: VaadinDateRenderer with DateRendererMixin = new VaadinDateRenderer with DateRendererMixin)
  extends AbstractRenderer[Date](p)