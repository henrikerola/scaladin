package vaadin.scala

import com.vaadin.ui.themes.BaseTheme
import com.vaadin.ui._

class Form(width: Option[Measure] = None, height: Option[Measure] = 100 percent, item: com.vaadin.data.Item = null, layout: com.vaadin.ui.Layout = null, fieldFactory: FormFieldFactory = DefaultFieldFactory.get())
  extends com.vaadin.ui.Form(layout, fieldFactory) {
  setWidth(if (width.isDefined) width.get.toString else null)
  setHeight(if (height.isDefined) height.get.toString else null)
  if (item != null) setItemDataSource(item)
}